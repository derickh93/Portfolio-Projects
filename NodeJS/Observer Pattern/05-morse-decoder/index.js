/*
=======================
05 - Morse Decoder - index.js
=======================
Student ID:
Comment (Required):

=======================
*/
const MorseEmitter = require("./modules/MorseEmitterEncrypted");
const morse = require("./data/morse-code.json");

function MorseToChar(data){
	return morse.find(cv => cv.code === data).char;
}

let morse_emitter = new MorseEmitter();

let data ="";

morse_emitter.on(".",function(){
	data+=".";
});

morse_emitter.on("break",function(){
	let letter = MorseToChar(data);
	process.stdout.write(letter);
	data = "";
});

morse_emitter.start();