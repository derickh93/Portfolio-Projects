/*
=======================
04 - Bad Word Detection - index.js
=======================
Student ID:
Comment (Required):

=======================
*/
const readline = require("readline");
const BadWordEmitter = require("./modules/BadWordEmitter");
const badwords = require("./data/badwords.json");
const rl = readline.createInterface({input: process.stdin, output:process.stdout});
let bad_word_count = 0;
let current_line = 1;

const snitch = new BadWordEmitter({badwords,r1});
snitch.on("badword",()=>{
	bad_word_count++;
	process.stdout.cursorTo(0,0);
	process.stdout.clearLine();
	console.log(`Welcome to the chat | Bad Word Count: ${bad_word_count}`);
	process.stdout.cursorTo(0,current_line);
});



const shell_prompt = function(){
	current_line++;
	process.stdout.write("> ")
}
rl.on("line", shell_prompt);

console.clear();
console.log("Welcome to the chat")
shell_prompt();