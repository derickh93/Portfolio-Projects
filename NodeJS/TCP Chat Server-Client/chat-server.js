"use strict";
const net = require("net");
var arr = [];
const server = net.createServer();
const connectionError = function(err) {
	throw err;
};

const connectionListener = function(socket) {
	socket.setEncoding('utf8');
	const connectionEnd = function () {
    var index = arr.indexOf(socket.remotePort);
    if (index > -1) {
       arr.splice(index, 1);
    }
		console.log("client disconnected");
	};
	
	socket.on("error", connectionError);
	socket.on("end",connectionEnd);
	arr.push(socket);
	console.log(`client connected: ${socket.remotePort} ${arr.length}`);
	socket.on("data",function(data) {
		for(let i = 0; i < arr.length;i++) {
			if(arr[i].remotePort != socket.remotePort){
				console.log(`${arr[i].remotePort} ${socket.remotePort}`);
				arr[i].write(data.toString());
				arr[i].pipe(arr[i]);
			}
		}
	});
}

const connectionStart = function() {
	console.log("server bound");
};

server.on("error",connectionError);
server.on("connection",connectionListener);
server.on("listening",connectionStart);

server.listen(3015);