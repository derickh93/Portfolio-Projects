/*
=-=-=-=-=-=-=-=-=-=-=-=-
Charity Weather Project
=-=-=-=-=-=-=-=-=-=-=-=-
Description: This project utilizes 4 main components: Orghunter API, User, Server, and Weatherstack API. The user enters a City and State
to search for charitys in the area. The user input is valided and caching is performed. If data is not pulled from cache a new request is made.
The http request is sent to Orghunter API to get a list of charitys. If the http request is valid a 200 response is sent back along with 
json data. The data is parsed and a a request is sent to the WeatherStack API. If the call is valid a 200 response is sent back along with JSON 
data. The data is parsed and the webpage is passed to the user. Upon receiving a 200 response the user is presented with weather and charity data.
=-=-=-=-=-=-=-=-=-=-=-=-
*/

//import required modules
const http = require("http");
const querystring = require("querystring");
const port = 3000;
const server = http.createServer();
const fs = require("fs");
const url = require("url");
require("dotenv").config();

//Obtaining the API keys from .env file
const api_key = process.env.NODE_CHARITY_API_KEY;
const api_key_weather = process.env.NODE_WEATHER_API_KEY;

//variables
var charity_arr = [];
var objects = "";
var valid_cache = false;
var exist = true;

//server handlers
server.on("request", connection_handler);
server.on("listening", listening_handler);
server.listen(port);

//function that reads request and sends back the correct response
function connection_handler(req, res) {
  console.log(`New Request for ${req.url} from ${req.socket.remoteAddress}`);
  //sends the user to the main/home page
  if (req.url === "/") {
    const main = fs.createReadStream("html/main.html");
    res.writeHead(200, {
      "Content-Type": "text/html",
    });
    main.pipe(res);
    //sends the user to a page that displays the favicon image
  } else if (req.url === "/favicon.ico") {
    const main = fs.createReadStream("images/favicon.ico");
    res.writeHead(200, {
      "Content-Type": "image/x-icon",
    });
    main.pipe(res);
    //sends the user to a page displaying the banner image
  } else if (req.url === "/images/banner.jpg") {
    const main = fs.createReadStream("images/banner.jpg");
    res.writeHead(200, {
      "Content-Type": "image/jpeg",
    });
    main.pipe(res);
    //validates the user input and caching requirements. It then sends the first request out.
  } else if (req.url.startsWith("/search")) {
    const myURL = new URL(`localhost:3000${req.url}`);
    const file_cache = `./cache/${myURL.searchParams.get("city")}-cache.json`;
    if (
      myURL.searchParams.get("city") === "" ||
      myURL.searchParams.get("state") === ""
    ) {
      res.writeHead(404, {
        "Content-Type": "text/plain",
      });
      res.write("Please enter both a city and state", () => res.end());
    } else if (fs.existsSync(file_cache)) {
      valid_cache = true;
      charity_arr = require(file_cache);
      if (new Date(charity_arr.req_date) > Date.now()) {
        weatherFunc(charity_arr, res);
        console.log("cache");
      } else {
        create_search_request(
          myURL.searchParams.get("city"),
          myURL.searchParams.get("state"),
          res
        );
        console.log("new request");
      }
    } else {
      create_search_request(
        myURL.searchParams.get("city"),
        myURL.searchParams.get("state"),
        res
      );
      console.log("new request");
    }
  } else {
    //sends the user to a 404 error page in all other instances
    res.writeHead(404, {
      "Content-Type": "text/plain",
    });
    res.write("404 Not Found", () => res.end());
  }
}

//function that creates a request for charitys with the user provided information.
function create_search_request(city, state, res) {
  let auth_req = https.request(
    `https://data.orghunter.com/v1/charitysearch?user_key=${api_key}&state=${state}&city=${city}&eligible=1`
  );

  auth_req.on("error", error_handler);

  function error_handler(err) {
    throw err;
  }
  auth_req.once("response", post_auth_cb);

  function post_auth_cb(incoming_msg_stream) {
    stream_to_message(incoming_msg_stream, (message) =>
      create_search_request_2(message, res)
    );
  }
  auth_req.end();
}

//function that creates a request to weatherstack api. The city and state is passed for each charity location obtained from the first api call.
function create_search_request_2(message, res) {
  printMessage(message, res);
  if (exist === true) {
    let auth_req = http.request(
      `http://api.weatherstack.com/current?access_key=${api_key_weather}&query=${charity_arr.data[0].city}, ${charity_arr.data[0].state}`
    );
    auth_req.on("error", error_handler);

    function error_handler(err) {
      throw err;
    }
    auth_req.once("response", post_auth_cb);

    function post_auth_cb(incoming_msg_stream) {
      stream_to_message(incoming_msg_stream, (message) =>
        printMessageWeather(message, res)
      );
    }
    auth_req.end();
  }
  exist = true;
}

//a function to retrieve all chunks of data and sends the entire data body upon completion.
function stream_to_message(stream, callback) {
  let body = "";
  stream.on("data", (chunk) => (body += chunk));
  stream.on("end", () => callback(body));
}

//functon that prints a message after checking cache
function printMessage(message, res) {
  if (valid_cache === false) {
    charity_arr = JSON.parse(message.toString());
    if (charity_arr.data.length === 0) {
      exist = false;
      console.log("search criteria returned no results");
      res.writeHead(404, {
        "Content-Type": "text/plain",
      });
      res.write("Search criteria returned no results", () => res.end());
    }
  }
  valid_cache = false;
}

//function that sends the results of the api call to be generated and creates/updates the cache
function printMessageWeather(message, res) {
  objects = JSON.parse(message.toString());
  generate_results(res);
  create_cache(charity_arr);
}

//function that is used to help with caching
function completeWrite() {
  console.log("file written");
}

//function to call the 2nd api request
function weatherFunc(charity_arr, res) {
  create_search_request_2(charity_arr, res);
}

//function that creates cache
function create_cache(charity_auth) {
  let cache_time = new Date(); //used for caching later
  cache_time.setHours(cache_time.getHours() + 1);
  charity_auth.req_date = cache_time;
  JSON.stringify(charity_auth);
  fs.writeFile(
    `./cache/${objects.location.name}-cache.json`,
    JSON.stringify(charity_auth),
    "utf8",
    completeWrite
  );
}

//function that generates a webpage based on the api calls
function generate_results(res) {
  res.writeHead(200, {
    "Content-Type": "text/html",
  });
  res.write(`<img src="images/banner.jpg"/>`);
  res.write(
    `<!DOCTYPE html><html><head><title>Charity Search</title></head></html>`
  );
  var temp = objects.current.temperature * 1.8 + 32;
  res.write(
    `<body><h1>${objects.location.name}</h1><p>${temp}<br>${objects.location.region}<br>${objects.location.timezone_id}<br>${objects.location.localtime}</p></body>`
  );
  res.write(`<img src="${objects.current.weather_icons[0]}"/>`);

  for (let i = 0; i < charity_arr.data.length; i++) {
    res.write(
      `<body><h1>${charity_arr.data[i].charityName}</h1><p>Charity EIN: ${charity_arr.data[i].ein}<br><a href="${charity_arr.data[i].url}">Charity Website</a><br><a href="${charity_arr.data[i].donationUrl}">Charity Donation Link</a></p></body>`
    );
  }
  res.end();
}

//function that displays the server and port is listening
function listening_handler() {
  console.log(`Now Listening on Port ${port}`);
}
