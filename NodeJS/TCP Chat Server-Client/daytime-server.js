"use strict";
const net = require("net");

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
	const daytime = new Date().toString();
	socket.write(`${daytime}\r\n`);
}

const connectionStart = function() {
	console.log("server bound");
};

server.on("error",connectionError);
server.on("connection",connectionListener);
server.on("listening",connectionStart);

server.listen(3013);