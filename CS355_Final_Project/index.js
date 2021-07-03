/*
=-=-=-=-=-=-=-=-=-=-=-=-
Final Project
=-=-=-=-=-=-=-=-=-=-=-=-
Student ID:15209569
=-=-=-=-=-=-=-=-=-=-=-=-
*/
const http = require('http');
const https = require('https');
const querystring = require("querystring");
const port = 3000;
const server = http.createServer();
const fs = require('fs');
const url = require('url');
const credentials = require('./auth/credentials.json');
const api_key = credentials.api_key_charity;
const api_key_weather = credentials.api_key_weather;
var charity_arr = [];
var objects = "";
var valid_cache = false;
var exist = true;

server.on("request", connection_handler)																																																																																										;c
server.on("listening", listening_handler);
server.listen(port);

function connection_handler(req, res) {
    console.log(`New Request for ${req.url} from ${req.socket.remoteAddress}`);
    if (req.url === '/') {
        const main = fs.createReadStream('html/main.html');
        res.writeHead(200, {
            'Content-Type': 'text/html'
        });
        main.pipe(res);
    } else if (req.url === '/favicon.ico') {
        const main = fs.createReadStream('images/favicon.ico');
        res.writeHead(200, {
            'Content-Type': 'image/x-icon'
        });
        main.pipe(res);
    } else if (req.url === '/images/banner.jpg') {
        const main = fs.createReadStream('images/banner.jpg');
        res.writeHead(200, {
            'Content-Type': 'image/jpeg'
        });
        main.pipe(res);
    } else if (req.url.startsWith('/search')) {
        const myURL = new URL(`localhost:3000${req.url}`);
        const file_cache = `./cache/${myURL.searchParams.get('city')}-cache.json`;
        if (myURL.searchParams.get('city') === "" || myURL.searchParams.get('state') === "") {
            res.writeHead(404, {
                "Content-Type": "text/plain"
            });
            res.write("Please enter both a city and state", () => res.end());
        } else if (fs.existsSync(file_cache)) {
            valid_cache = true;
            charity_arr = require(file_cache);
            if (new Date(charity_arr.req_date) > Date.now()) {
                weatherFunc(charity_arr, res);
                console.log('cache');
            } else {
                create_search_request(myURL.searchParams.get('city'), myURL.searchParams.get('state'), res);
                console.log('new request');
            }
        } else {
            create_search_request(myURL.searchParams.get('city'), myURL.searchParams.get('state'), res);
            console.log('new request');
        }

    } else {
        res.writeHead(404, {
            "Content-Type": "text/plain"
        });
        res.write("404 Not Found", () => res.end());
    }
}

function create_search_request(city, state, res) {
    let auth_req = https.request(`https://data.orghunter.com/v1/charitysearch?user_key=${api_key}&state=${state}&city=${city}&eligible=1`);


    auth_req.on('error', error_handler);

    function error_handler(err) {
        throw err;
    }
    auth_req.once('response', post_auth_cb);

    function post_auth_cb(incoming_msg_stream) {
        stream_to_message(incoming_msg_stream, message => create_search_request_2(message, res));
    }
    auth_req.end();
}

function create_search_request_2(message, res) {
    printMessage(message, res);
    if (exist === true) {
        let auth_req = http.request(`http://api.weatherstack.com/current?access_key=${api_key_weather}&query=${charity_arr.data[0].city}, ${charity_arr.data[0].state}`);
        auth_req.on('error', error_handler);

        function error_handler(err) {
            throw err;
        }
        auth_req.once('response', post_auth_cb);

        function post_auth_cb(incoming_msg_stream) {
            stream_to_message(incoming_msg_stream, message => printMessageWeather(message, res));
        }
        auth_req.end();
    }
    exist = true;
}

function stream_to_message(stream, callback) {
    let body = "";
    stream.on("data", (chunk) => body += chunk);
    stream.on("end", () => callback(body));
}

function printMessage(message, res) {
    if (valid_cache === false) {
        charity_arr = JSON.parse(message.toString());
        if (charity_arr.data.length === 0) {
            exist = false;
            console.log('search criteria returned no results');
            res.writeHead(404, {
                "Content-Type": "text/plain"
            });
            res.write("Search criteria returned no results", () => res.end());
        }
    }
    valid_cache = false;
}

function printMessageWeather(message, res) {
    objects = JSON.parse(message.toString());
    generate_results(res);
    create_cache(charity_arr);
}

function completeWrite() {
    console.log('file written');
}

function weatherFunc(charity_arr, res) {
    create_search_request_2(charity_arr, res);
}

function create_cache(charity_auth) {
    let cache_time = new Date(); //used for caching later
    cache_time.setHours(cache_time.getHours() + 1);
    charity_auth.req_date = cache_time;
    JSON.stringify(charity_auth);
    fs.writeFile(`./cache/${objects.location.name}-cache.json`, JSON.stringify(charity_auth), 'utf8', completeWrite);
}

function generate_results(res) {
    res.writeHead(200, {
        'Content-Type': 'text/html'
    });
    res.write(`<img src="images/banner.jpg"/>`);
    res.write(`<!DOCTYPE html><html><head><title>Charity Search</title></head></html>`);
    var temp = objects.current.temperature * 1.8 + 32;
    res.write(`<body><h1>${objects.location.name}</h1><p>${temp}<br>${objects.location.region}<br>${objects.location.timezone_id}<br>${objects.location.localtime}</p></body>`);
    res.write(`<img src="${objects.current.weather_icons[0]}"/>`);

    for (let i = 0; i < charity_arr.data.length; i++) {
        res.write(`<body><h1>${charity_arr.data[i].charityName}</h1><p>Charity EIN: ${charity_arr.data[i].ein}<br><a href="${charity_arr.data[i].url}">Charity Website</a><br><a href="${charity_arr.data[i].donationUrl}">Charity Donation Link</a></p></body>`);
    }
    res.end();
}

function listening_handler() {
    console.log(`Now Listening on Port ${port}`);
}