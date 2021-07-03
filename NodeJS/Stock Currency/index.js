const fs = require("fs");
const http = require("http");
const https = require('https')
const {
    parse
} = require('querystring');
let stock_name = "";
let stock_price = "";
const port = 3000;
const host = "localhost";
const credentials = require('./auth/api_key.json');
const api_key = credentials.credential.api_key;
var currency_arr = ['EUR', 'JPY', 'GBP', 'AUD', 'CAD'];

const server = http.createServer((req, res) => {
    if (req.method === 'POST') {
        collectRequestData(req, result => {
            stock_name = result.q;
            console.log(result.q);
            first_api(stock_name);
            res.writeHead(200, {
                'Content-Type': 'text/html'
            });
            res.write(`Results saved to file results.txt`);
            res.end();
        });
    } else {
        fs.readFile('./html/search-form.html', function(error, data) {
            if (error) {
                res.writeHead(404);
                res.write(error);
                res.end();
            } else {
                res.writeHead(200, {
                    'Content-Type': 'text/html'
                });
                res.write(data);
                res.end();
            }
        });
    }
});

function collectRequestData(request, callback) {
    const FORM_URLENCODED = 'application/x-www-form-urlencoded';
    if (request.headers['content-type'] === FORM_URLENCODED) {
        let body = '';
        request.on('data', chunk => {
            body += chunk.toString();
        });
        request.on('end', () => {
            callback(parse(body));
        });
    } else {
        callback(null);
    }
}
server.listen(port, host);
console.log(`Now Listening On Port ${port}`);

function first_api(input) {
    const options = {
        hostname: 'financialmodelingprep.com',
        port: 443,
        path: '/api/v3/company/profile/' + input,
        method: 'GET'
    }

    const req = https.request(options, (res) => {
        res.on('data', (d) => {
            process.stdout.write(d)
            var obj = JSON.parse(d);

            if (obj.profile === undefined) {
                console.log("\nInvalid Ticker Symbol: " + input);

                fs.appendFile("results.txt", "\n********************************************\nInvalid Ticker Symbol: " + input, (err) => {
                    if (err) console.log(err);
                    console.log("Successfully Written to File.");
                });
            } else {
                var filetxt = "\n********************************************\nTicker Symbol: " + obj.symbol + "\nCompany: " + obj.profile.companyName + "\nExchange: " + obj.profile.exchange + "\nIndustry: " + obj.profile.industry +
                    "\nWebsite: " + obj.profile.website + "\nDescription: " + obj.profile.description + "\nCEO: " + obj.profile.ceo + "\nStock Price: " + obj.profile.price +
                    "\n\nConverted Stock Prices:\n";

                fs.appendFile("results.txt", filetxt, (err) => {
                    if (err) console.log(err);
                    console.log("Successfully Written to File.");
                });




                stock_price = obj.profile.price;
                second_api(stock_price);
            }
        })
    })

    req.on('error', (error) => {
        console.error(error)
    })

    req.end();
}

function second_api(input) {
    //The following code will send a GET request to fixer API and retrieve the currency rates from the top 50 countries

    for (var i = 0; i < currency_arr.length; i++) {
        http.get(`http://data.fixer.io/api/convert?access_key=${api_key}&from=USD&to=${currency_arr[i]}&amount=${input}`, (resp) => {
            let data = '';
            // A chunk of data has been recieved.
            resp.on('data', (chunk) => {
                data += chunk;
                //console.log(data);
                console.log("\n");

            });
            // The whole response has been received. Print out the result.
            resp.on('end', () => {
                console.log(JSON.parse(data).query.to + ": " + JSON.parse(data).result);


                var filetxt = JSON.parse(data).query.to + ": " + JSON.parse(data).result.toFixed(2) + "\n";

                fs.appendFile("results.txt", filetxt, (err) => {
                    if (err) console.log(err);
                    console.log("Successfully Written to File.");
                });
            });
        }).on("error", (err) => {
            console.log("Error: " + err.message);
        });
    }
}