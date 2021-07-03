/*
=======================
03 - Price Notifications - index.js
=======================
Student ID:
Comment (Required):

=======================
*/
const DayEmitter = require("./modules/DayEmitter");
const Stock = require("./modules/Stock");
const PriceEmitter = require("./modules/PriceEmitter");

const portfolio_data = require("./data/stocks.json");
const stock_alerts = require("./data/stock-alerts.json");

const day_emitter = new DayEmitter(2400);
const portfolio = portfolio_data.map(stock => new Stock({...stock, day_emitter}));

let current_line = portfolio.length + 1;

portfolio.forEach(function(stock, index){
	stock.on("newday", function({ticker, name, price, previous, change}){
		process.stdout.cursorTo(0, index + 1);
		process.stdout.clearLine();
		console.log(`${ticker} ${name} ${price.toFixed(2)} ${change.toFixed(2)}`);
		process.stdout.cursorTo(0, current_line);
	});
});

stock_alerts.map(stock_alert => ({...stock_alert, stock:portfolio.find(stock => stock.ticker === stock_alert.ticker)}))
	.forEach(stock_alert_data => new PriceEmitter(stock_alert_data).on("price-alert",price_alert));

function price_alert({mm_dd,ticker,target_price,direction,price}){
	console.log(`Price Alert: (${ticker}:${price}) went ${direction} ${target_price} on ${mm_dd}`);
	current_line++;
	}





day_emitter.on("newday", function(spec){
	let {mm_dd} = spec;
	process.stdout.cursorTo(0, 0);
	process.stdout.clearLine();
	process.stdout.write(mm_dd);
	process.stdout.cursorTo(0, current_line);
});
day_emitter.start();