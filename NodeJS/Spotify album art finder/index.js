/*
=-=-=-=-=-=-=-=-=-=-=-=-
Album Art Search
=-=-=-=-=-=-=-=-=-=-=-=-
Student ID:
Comment (Required):

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
const client_id = credentials.client_id;
const client_secret = credentials.client_secret;
let base64data = Buffer.from(`${client_id}:${client_secret}`).toString('base64');
let base64 = (`Basic ${base64data}`);
let user_input = "";
let res = "";
const authentication_cache = './auth/authentication-res.json';
let cache_valid = false;
var post_data = {grant_type:"client_credentials"};
var postLength = post_data.length;
var header = {"Content-Type":"application/x-www-form-urlencoded", Authorization:base64};
var options = {method:"POST", headers:header};
post_data = querystring.stringify(post_data);

if(fs.existsSync(authentication_cache)){
	cached_auth = require(authentication_cache);
	if(new Date(cached_auth.expiration) > Date.now()){
		cache_valid = true;
	}
}

if(cache_valid){
	console.log('got here');
	create_search_request(cached_auth.access_token, user_input, res);
}
else{

	const token_endpoint = 'https://accounts.spotify.com/api/token';
	let auth_sent_time = new Date(); //used for caching later
	let auth_req = https.request(token_endpoint, options);
	auth_req.on('error', error_handler);
	function error_handler(err){
		throw err;
	}
	auth_req.once('response', post_auth_cb);
	function post_auth_cb(incoming_msg_stream){
		stream_to_message(incoming_msg_stream, message => received_auth(message,user_input,auth_sent_time,res));
	}
	auth_req.end(post_data);
}

function stream_to_message(stream,callback){
	let body = "";
	stream.on("data", (chunk) => body+= chunk);
	stream.on("end", () => callback(body));
}

function received_auth(auth_message, user_input, auth_sent_time, res){
	let spotify_auth = JSON.parse(auth_message);
	console.log(spotify_auth);
	
	let exp_time = auth_sent_time;
	exp_time.setHours(exp_time.getHours()+1);
	spotify_auth.expiration = exp_time;

	create_access_token_cache(spotify_auth);
	create_search_request(spotify_auth.access_token, user_input, res);
}

function create_access_token_cache(spotify_auth) {
	JSON.stringify(spotify_auth);
	fs.writeFile('./auth/authentication-res.json', JSON.stringify(spotify_auth), 'utf8', completeWrite);
}

function completeWrite() {
	console.log('file written');
}

function create_search_request(spotify_auth, user_input, res) {
	const token_endpoint = 'https://api.spotify.com/v1/search';
	let auth_req = https.request(`${token_endpoint}?type=album&q=Taylor%20Swift&access_token=${spotify_auth}`);
	
	
	auth_req.on('error', error_handler);
	function error_handler(err){
		throw err;
	}
	auth_req.once('response', post_auth_cb);
	function post_auth_cb(incoming_msg_stream){
		stream_to_message(incoming_msg_stream, message => console.log(message));
	}
	auth_req.end(post_data);
}

function searchReq() {
	console.log('search req complete');
	}
	
let image_req = https.get(,function(image_res)) {
	let new_img = fs.createWriteStream(img_path, {'encoding':null});
	image_res.pipe(new_img);
	new_img.on("finish", function() {
		downloaded_images++;
		if(downloaded_images === albums.length) {
			generate_webpage(,res);
		}
	});
});
image_req.on('error', function(err){console.log(err);});

server.on("request", connection_handler);
function connection_handler(req, res){
	console.log(`New Request for ${req.url} from ${req.socket.remoteAddress}`);	
	if(req.url === '/'){
		const main = fs.createReadStream('html/main.html');
		res.writeHead(200,{'Content-Type' : 'text/html'});
		main.pipe(res);
	}
	else if(req.url === '/favicon.ico'){
		const main = fs.createReadStream('images/favicon.ico');
		res.writeHead(200,{'Content-Type' : 'image/x-icon'});
		main.pipe(res);
	}
	else if(req.url === '/images/banner.jpg'){
		const main = fs.createReadStream('images/banner.jpg');
		res.writeHead(200,{'Content-Type' : 'image/jpeg'});
		main.pipe(res);
	}
	else if(req.url.startsWith('/album-art/')){
		let image_stream = fs.createReadStream(`.${req.url}`);
		image_stream.on('error',image_error_handler);
		function image_error_handler(err) {
			res.writeHead(404,{"Content-Type" : "text/plain"});
			res.write("404 Not Found", () => res.end());
		}
		image_stream.on('ready', deliver_image);
		function deliver_image() {
			res.writeHead(200,{"Content-Type" : "image/jpeg"});
			image_stream.pipe(res);
		}
	}
	else if(req.url.startsWith('/search')){
		res.write('REPLACE WITH SEARCH');
		const myURL = new URL(`localhost:3000${req.url}`);
		console.log(myURL.searchParams.get('artist'));
		res.end();
	}
	else{
		res.write('REPLACE WITH CATCHALL');
		res.end();
	}
}

server.on("listening", listening_handler);
server.listen(port);
function listening_handler(){
	console.log(`Now Listening on Port ${port}`);
}