package proj4fa16;

/**
 * Title: The InvalidSaleException Class
 * Description: Exception class used by the MyStocks class.
 *
 * @author Derick Hansraj
 */
	@SuppressWarnings("serial")
	public class IncompleteSaleException extends RuntimeException
	{
		/**
		 * Initializes an IncompleteSaleException storing an appropriate 
		 * message along with the type of the collection (as specified by the user).
		 * @param a error message to be displayed
		 */
	    public IncompleteSaleException(String message)
	    {
	        super(message);
	    }
	}