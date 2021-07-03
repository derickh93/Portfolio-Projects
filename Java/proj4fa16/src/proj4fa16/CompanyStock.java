package proj4fa16;

import java.text.DecimalFormat;

/**
 * Title: CompanyStock.java
 * Description: A CompanyStock object will store a quantity, price, and name. There are also
 * Accessor and mutator methods defined along with a toString method.
 * @author Derick Hansraj
 */
public class CompanyStock
{
	private int numShares;
	private double price;
	private String tickerSym;

	/**
	 * parameterized constructor -- Creates a CompanyStock object with the desired values
	 *
	 * @param numShares   Amount of stock owned
	 * @param price	price the stock was bought for
	 * @param tickerSym     The ticker symbol of the stock
	 */
	public CompanyStock(int numShares, double price, String tickerSym)
	{
		this.numShares = numShares;
		this.price = price;
		this.tickerSym = tickerSym;
	}

	/**
	 * getShares -- Returns the number of shares owned.
	 *
	 * @return The amount of shares Owned
	 */
	public int getShares()
	{
		return numShares;
	}

	/**
	 * getPrice -- Returns the price that the stock was bought.
	 *
	 * @return The price the stock was purchased
	 */
	public double getPrice()
	{
		return price;
	}

	/**
	 * getTicker -- Returns the tickerSymbol of the chosen stock
	 *
	 * @return The ticker symbol of the stock
	 */
	public String getTicker()
	{
		return tickerSym;
	}

	/** setNumShares -- Changes the number of shares owned. 
	 * @param quantity- The amount of shares that will be owned 
	 */
	public void setNumShares(int quantity)
	{
		numShares = quantity;
	}

	/**
	 * toString -- Returns the shares owned, and purchase price as a string
	 *
	 * @return a string containing tickerSym, numShares, and price
	 */
	public String toString()
	{
		DecimalFormat convert = new DecimalFormat("$###,##0.00");
		return tickerSym + ": " + numShares + " shares at " + convert.format(price);
	}
}
