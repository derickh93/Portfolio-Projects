package proj4fa16;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Title: UnorderedList.java
 * Description: Creates a UnorderedList that will read a file and store the ticker symbol
 * and the company name as CompanyStock objects.
 * @ Derick Hansraj
 */
public class UnorderedList
{
	private ArrayUnorderedList<CompanyInfo> stocks;

	/**
	 * parameterized constructor -- Scans the file and creates a CompanyStock object.
	 *
	 * @param f1 The file containing the ticker symbols and company names
	 * @throws FileNotFoundException It is thrown only if the file is not found
	 */
	public UnorderedList(File f1) throws FileNotFoundException
	{
		stocks = new ArrayUnorderedList<CompanyInfo>();
		@SuppressWarnings("resource")
		Scanner fileScan = new Scanner(f1);
		String[] seperatePart;
		while (fileScan.hasNextLine())
		{
			seperatePart = fileScan.nextLine().split(" ", 2);
			stocks.addToRear((new CompanyInfo(seperatePart[0], seperatePart[1].trim())));
		}
	}

	/**
	 * tickerSearch -- Returns the company name of the desired ticker symbol
	 *
	 * @param s1 the ticker symbol used to search
	 * @return the company name of the chosen ticker symbol
	 */
	public String tickerSearch(String s1)
	{
		for (int i = 0; i < stocks.size(); i++) {
			CompanyInfo tickerTemp = stocks.get(i);
			if (tickerTemp.getTicker().equals(s1))
			{
				return tickerTemp.getCompanyName();
			}
		}
		return "This Ticker symbol was not recognized.";
	}
}