/*
=======================
05 - Morse Decoder - MorseEmitter.js
=======================
Student ID:
Comment (Required):

=======================
*/
const EventEmitter = require('events');
const morse = require("../data/morse-code.json");

function StringToMorse(string) {
	//Slash represents end of character (Break)
	return string.split('').map(letter => morse.find(cv => cv.char === letter).code).join("/")+"/";
}

class MorseEmitter extends EventEmitter {
	constructor(message = "secretmessage"){
		super();
		message = message.toUpperCase();
		let coverted = StringToMorse(message_;
		this.morse_data = coverted;
	}
	start(){
		for(let i = 0; i < this.morse_data.length;i++){
			if(this.morse_data[i] === "."){
				this.emit('.');
			}
			else if(this.morse_data[i] === "-") {
				this.emit('-');
			}
			else{
				this.emit('break');
			}
		}
	}
}

module.exports = MorseEmitter;
