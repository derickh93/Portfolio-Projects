package proj4fa16;

/**
 * Title: CompanyInfo.java
 * Description: A class that will store the ticker symbol and company name of a stock.
 * It also contains methods to get the ticker symbol and company name.
 * @author Derick Hansraj
 */
public class CompanyInfo
{
	private String tickerSym;
	private String companyName;

	/**
	 * parameterized constructor -- Creates a CompanyInfo object using the specified names
	 *
	 * @param tickerSym   the ticker symbol
	 * @param companyName the company name
	 */
	public CompanyInfo(String s1, String s2)
	{
		this.tickerSym = s1;
		this.companyName = s2;
	}

	/**
	 * getTicker -- Returns the ticker symbol
	 *
	 * @return the ticker symbol
	 */
	public String getTicker()
	{
		return tickerSym;
	}

	/**
	 * getCompanyName -- Returns the company name of the stock.
	 *
	 * @return the company name of the stock
	 */
	public String getCompanyName()
	{
		return companyName;
	}
}