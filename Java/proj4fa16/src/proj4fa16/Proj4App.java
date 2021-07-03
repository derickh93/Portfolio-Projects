package proj4fa16;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Title: Proj4App.java
 * Description: The application class creates a UnorderedList object and a MyStocks object. Transactions
 * are then scanned in from tickerCompanyData.txt and transactionData.txt and processed and displayed.
 * @ Derick Hansraj
 */
public class Proj4App
{
	public static void main(String args[]) throws Exception
	{
		File tickerSymbol = new File("tickerCompanyData.txt");
		File stockData = new File("transactionData.txt");
		UnorderedList tickerData = new UnorderedList(tickerSymbol);

		MyStocks stockPortfolio = new MyStocks(tickerData);
		@SuppressWarnings("resource")
		Scanner scanFile = new Scanner(stockData);
		String[] lineBreak;

		DecimalFormat convert = new DecimalFormat("$###,##0.00");

		while (scanFile.hasNextLine())
		{
			System.out.println("STARTING TRANSACTION\n" + "PRE TRANSACTION STATUS:\n" + stockPortfolio.toString());

			lineBreak = scanFile.nextLine().split(" ", 4);
			char status = lineBreak[0].charAt(0);
			int amount = Integer.parseInt(lineBreak[1]);
			double price = Double.parseDouble(lineBreak[2]);
			String ticker = lineBreak[3];

			if (status == 'b')
			{
				System.out.println("BUYING\n" + tickerData.tickerSearch(ticker) + "\n" + "Share Price: " + convert.format(price)  +
						" Shares Sold: " + amount + "\n");
			}
			else
			{
				System.out.println("SELLING\n" + tickerData.tickerSearch(ticker) + "\n" + "Share Price: " + convert.format(price) +
						" Shares Sold: " + amount + "\n");
			}

			try
			{
				stockPortfolio.process(status, amount, price, ticker);
			}
			catch (IncompleteSaleException ex)
			{
				System.out.println(ex.getMessage());
			}

			System.out.println("POST TRANSACTION STATUS:\n" + stockPortfolio.toString() + "\nEND OF TRANSACTION\n");
		}
	}
}