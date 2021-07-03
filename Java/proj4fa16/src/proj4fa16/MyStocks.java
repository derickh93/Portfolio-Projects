package proj4fa16;

import java.text.DecimalFormat;

/**
 * Title: MyStocks.java
 * Description: Contains the methods for creating a MyStocks object. It then processes the transaction and 
 * displays the contents of the MyStock objects. It also contains methods to display the MyStocks object as a string.
 * @author Derick Hansraj
 */
public class MyStocks
{   
	private UnorderedList tickers;
	private ArrayUnorderedList<CompanyStock> stockList;
	private double status;

	/**
	 * parameterized constructor --
	 * Creates a MyStocks object with specified CompanyStock
	 *
	 * @param adds CompanyStocks objects to the MyStocks object
	 */
	public MyStocks(UnorderedList s1)
	{
		stockList = new ArrayUnorderedList<CompanyStock>();
		this.tickers = s1;
	}

	/**
	 * process -- updates the contents and order of the stockList array by updating or removing array contents
	 *
	 * @param c1   	the type of transaction
	 * @param n1	amount of shares bought or sold
	 * @param p1    the price per share
	 * @param s1    the ticker symbol of the stock
	 */
	public void process(char c1, int n1, double p1, String s1)
	{
		if (c1 == 'b')
		{
			stockList.addToRear(new CompanyStock(n1, p1, s1));
		}
		else
		{
			if (stockList.size() == 0)
			{
				throw new IncompleteSaleException("MyStocks does not contain anything");
			}
			int total = 0;
			for (int i = 0; i < stockList.size(); i++)
			{
				CompanyStock tempStock = stockList.get(i);
				if (tempStock.getTicker().equals(s1))
				{
					total += tempStock.getShares();
				}
			}
			if (n1 > total)
			{
				throw new IncompleteSaleException("Unable to sell " + n1 + " shares of " + tickers.tickerSearch
						(s1) + ":\nNot enough shares\n");
			}

			int tempNum = n1;
			for (int i = 0; i < stockList.size() && tempNum != 0; i++)            
			{                
				CompanyStock tempStock = stockList.get(i);
				if (tempStock.getTicker().equals(s1))
				{
					if (tempNum >= tempStock.getShares())
					{
						tempNum -= tempStock.getShares();
						status += tempStock.getShares() * (p1 - tempStock.getPrice());
						stockList.remove(i);
						i--; 
					}
					else
					{
						status += tempNum * (p1 - tempStock.getPrice());
						tempStock.setNumShares(tempStock.getShares() - tempNum);
						tempNum = 0;
					}       
				}       
			}
		}
	}

	/**
	 * toString -- Display the contents of stockList
	 *
	 * @return a string containing the contents of stockList
	 */
	public String toString()
	{
		DecimalFormat convert = new DecimalFormat("$###,##0.00");
		String s1 = "\n";
		String[] s2;
		for (int i = 0; i < stockList.size(); i++)
		{
			s2 = stockList.get(i).toString().split(" ", 2);
			s1 += tickers.tickerSearch(stockList.get(i).getTicker()) + "\n" +
					s2[1] + "\n\n";
		}
		return s1 + "\n" + "Current Profit: " + convert.format(status) + "\n";
	}
}