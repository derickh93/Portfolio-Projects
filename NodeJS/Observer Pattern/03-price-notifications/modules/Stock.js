/* Example 06A: Stock.js */

const EventEmitter = require('events');
class Stock extends EventEmitter {
	constructor({ticker, name, price, volatility = 0.1, growth = 0.07, day_emitter}){
		super();
		this.ticker = ticker;
		day_emitter.on("newday", ({mm_dd}) => {
			let previous = price;
			price = Stock.tomorrow_price({price, volatility, growth});
			let change = Stock.round(price - previous);
			this.emit("newday", {mm_dd, ticker, name, price, previous, change});
		});
	}
	static tomorrow_price({price, volatility, growth}){
		let daily_change = Math.random() * 2 * volatility - volatility + (growth / 365);
		let new_price = Stock.round(price * (1 + daily_change));
		return Math.max(new_price, 0);
	}
	static round(num){
		return Math.round((num + Number.EPSILON) * 100) / 100;
	}
}
module.exports = Stock;