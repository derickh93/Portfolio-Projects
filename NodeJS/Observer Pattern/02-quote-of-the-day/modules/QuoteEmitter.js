/*
=======================
02 - Quote of the Day - QuoteEmitter.js
=======================
Student ID:
Comment (Required):

=======================
*/
const EventEmitter = require('events');
class QuoteEmitter extends EventEmitter {
	constructor({quotes,day_emitter} = spec) {
		super();
		day_emitter.on("newday",({mm_dd}) => {
			let quote = quotes[Math.random() * quotes.length | 0];
			this.emit("qotd",quote);
		});
	}
}
module.exports = QuoteEmitter;