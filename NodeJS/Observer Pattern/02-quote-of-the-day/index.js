/*
=======================
02 - Quote of the Day - index.js
=======================
Student ID:
Comment (Required):

=======================
*/
const quotes = require("./data/quotes.json");
const DayEmitter = require("./modules/DayEmitter");
const QuoteEmitter = require("./modules/QuoteEmitter");

const day_emitter = new DayEmitter(2400);
const quote_emitter = new QuoteEmitter({quotes,day_emitter});

let current_line = 1;

quote_emitter.on("qotd",function(quote){
	console.clear();
	process.stdout.cursorTo(0,1);
	console.log(quote);
});

day_emitter.on("newday", function({mm_dd}){
	process.stdout.cursorTo(0, 0);
	process.stdout.clearLine();
	console.log(mm_dd);
});
day_emitter.start();