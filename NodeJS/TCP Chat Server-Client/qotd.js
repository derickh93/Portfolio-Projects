"use strict";
const net = require("net");
const portfolio_data = require("./quotes.json");
let length = portfolio_data.length;			

const server = net.createServer();
const connectionError = function(err) {
	throw err;
};

const connectionListener = function(socket) {
	const connectionEnd = function () {
		console.log("client disconnected");
	};
	socket.on("end",connectionEnd);
	console.log("client connected");
	console.log(portfolio_data[getRandomInt(length)].quote);
	socket.write(`client connected`);
}

const connectionStart = function() {
	console.log("server bound");
};

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

server.on("error",connectionError);
server.on("connection",connectionListener);
server.on("listening",connectionStart);

server.listen(13);