package proj2fa16;
/**
 * <p>Title: Project2fa16
 * <p>Description: Fish Class - An object of type Fish contains a 
 * String which will be used to create a fish object. The class defines 
 * methods to get the Fish object and a toString method which allows the
 *  user to see the current state of the Fish object.</p>
 * @author <Derick Hansraj>
 */
public class Fish extends Animal {
	//instance variables
	private String f1;
	/**
	 * default constructor --
	 * Creates a fish object.
	 */
	public Fish() {
		f1 = "Fish";
	}
	/**
	 * toString --
	 * Returns a string representing the current state of the fish object.
	 * @return the letter and number of a fish object.
	 */
	public String toString() {
		return f1;
	}

}
