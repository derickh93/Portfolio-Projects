package proj5fa16;
/**
 * Title: FriendNotFoundException Class
 * Description: Exception class used when a Friend is not found.
 *
 * @author Derick Hansraj
 */
public class FriendNotFoundException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * default constructor -- invokes the parameterized constructor in the parent class, RuntimeException, to store the
     * string "Friend not found!".
     */
    public FriendNotFoundException()
    {
        super("Friend not found!\n");
    }

    /**
     * parameterized constructor -- invokes the parameterized constructor in the parent class, RuntimeException, to
     * store the string message.
     *
     * @param message a reference to a String object that contains information about the type of exception that
     *                occurred
     */
    public FriendNotFoundException(String message)
    {
        super(message);
    }
}