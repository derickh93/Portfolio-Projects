package proj2fa16;
/**
 * <p>Title: Project2fa16
 * <p>Description: Bear Class - An object of type Bear contains a 
 * String which will be used to create a Bear object. The class defines 
 * methods to get the Bear object and a toString method which allows the
 *  user to see the current state of the Bear object.</p>
 * @author <Derick Hansraj>
 */
public class Bear extends Animal {
	//instance variables
	private String b1;
	/**
	 * default constructor --
	 * Creates a bear object.
	 */
	public Bear() {
		b1 = "Bear";
	}
	/**
	 * toString --
	 * Returns a string representing the current state of the bear object.
	 * @return the bear object.
	 */
	public String toString() {
		return b1;
	}

}
