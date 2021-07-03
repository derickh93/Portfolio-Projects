const server_address = "localhost";
const server_port = 3015;
var nickname;
const readline = require('readline');
let userInput;

const io = readline.createInterface({
	input: process.stdin,
	output: process.stdout
});

const net = require('net');

const connectionData = function(data) {
	readline.clearLine(process.stdout, 0);
	readline.cursorTo(process.stdout, 0, null);
	console.log(data.toString());
	process.stdout.write(`[${nickname}]: `);
}

const connectionEnd = function() {
	console.log('Client Disconnected');
}

const connectionStart = function() {
	console.log('Connected to server!');
	io.question('Choose a username: ', (answer) => {
		nickname = answer;
		chat();
	});
}

function chat() {
	io.question(`[${nickname}]: `, (answer) => {
	userInput = answer;
		if(userInput === "/exit") {
			client.end(function(){process.exit();});
	}
	client.write(`[${nickname}]: ${userInput}`);
	chat();
	});
}

const client = net.createConnection(server_port, server_address, connectionStart);
client.on('data',connectionData);
client.on('end',connectionEnd);