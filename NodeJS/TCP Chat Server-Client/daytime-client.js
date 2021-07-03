const server_address = "localhost";
const server_port = 3013;

const net = require('net');

const connectionData = function(data) {
	console.log(data.toString());
}

const connectionEnd = function() {
	console.log('Client Disconnected');
}

const connectionStart = function() {
	console.log('Connected to server!');
}

const client = net.createConnection(server_port, server_address, connectionStart);
client.on('data',connectionData);
client.on('end',connectionEnd);