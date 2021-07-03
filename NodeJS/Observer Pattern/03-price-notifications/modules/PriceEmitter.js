/*
=======================
03 - Price Notifications - PriceEmitter.js
=======================
Student ID:
Comment (Required):

=======================
*/
const EventEmitter = require('events');
class PriceEmitter extends EventEmitter {
	constructor({target_price,direction,stock}) {
		super();
		stock.on("newday",({mm_dd,ticker,price}) => {
			if((direction === "above" && price >= target_price) || 
				(direction === "below" && price <= target_price)){
				this.emit("price-alert",{mm_dd,ticker,target_price, direction,price});
			}
		});
	}
}
module.exports = PriceEmitter;
