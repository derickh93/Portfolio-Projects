/*
=======================
04 - Bad Word Detection - BadWordEmitter.js
=======================
Student ID:
Comment (Required):

=======================
*/
const EventEmitter = require('events');
class BadWordEmitter extends EventEmitter{
	constructor({badwords,r1}){
		super();
		badwords = badwords.map(badword => badword.toLowerCase());
		r1.on("line",(message)=>{
			message = message.toLowerCase();
			let you_said = [];
			badwords.forEach((badword) => {
				const count = message.split(badword).length -1;
				you_said = you_said.concat(Array(count).fill(badword));
			});
			you_said.forEach((badword)=>{
				this.emit("badword");
			});
		});
	}
}
module.exports = BadWordEmitter;