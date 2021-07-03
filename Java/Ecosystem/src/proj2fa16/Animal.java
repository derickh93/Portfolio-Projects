package proj2fa16;
import java.util.Random;
/**
 * <p>Title: Project2fa16
 * <p>Description: Animal Class - An object of type Animal contains either a Fish
 * or Bear object, as well as a gender and strength for the chosen object.The class defines 
 * methods to get the gender, strength, animal type, collision steps and a toString method
 * which allows the user to see the current state of the Animal object.</p>
 * @author <Derick Hansraj>
 */
public class Animal {
	//instance variables
	private Random rand = new Random();
	private String pets = new String();
	private boolean gender  = Math.random() < 0.5;
	private float minX = 1.0f;
	private float maxX = 10.0f;
	private float strength = rand.nextFloat()*(maxX - minX + 1.0f) + minX;
	private String gender1 = new String();
	/**
	 * default constructor --
	 * Creates a Animal object and assigns it with a animal, gender, and strength.
	 */
	public Animal() {
		if (gender == true)
			gender1 = "Male";
		else 
			gender1 = "Female";
		int  a = rand.nextInt(3) + 1; 
		if(a == 1)
			pets = new Fish().toString()  + " " + gender1 + " " + strength;

		else if (a == 2)
			pets = new Bear().toString()  + " " + gender1 + " " + strength;
		else 
			pets = "null";
	}
	/**
	 * parameterized constructor -- The Animal is given a type based upon the
	 * arguments sent, and then a gender and strength.
	 * @param s1 - The String used to represent the desired animal type.
	 *
	 */
	public Animal(String s1) {
		if(s1.equals("Fish"))
			pets = new Fish().toString()  + " " + gender1 + " " + strength;
		else if(s1.equals("Bear"))
			pets = new Bear().toString()  + " " + gender1 + " " + strength;
		else
			pets = "null";
	}
	/**
	 * getAnimal --
	 * Returns a String representing the type of animal, either Bear or Fish, 
	 * or null if no animal.
	 * @return Return animal type of space in ecosystem.
	 */
	public String getAnimal() {
		if(pets.substring(0, 4).equals("Fish"))
			return "Fish";
		else if (pets.substring(0, 4).equals("Bear"))
			return "Bear";
		else
			return "null";

	}
	/**
	 * moveDecision --
	 * Returns either true or false to determine whether or not we move
	 * a chosen animal into the next space.
	 * @return true if we will move, else false
	 */
	public boolean moveDecison() {
		return Math.random() < 0.5;
	}
	/**
	 * collideAnimal --
	 * Returns a String containing what happens when the two chosen
	 * animals collide.
	 * @param a1 - An animal object to be collided with another.
	 * @return a string with the after math of animal collision
	 */
	public String collideAnimal(Animal a1) {
		String s1 = new String();			
		if(this.getAnimal().equals(a1.getAnimal()) && !this.getGender().equals(a1.getGender()))
			s1 = "Same Animal Different Gender";
		else if (this.getAnimal().equals(a1.getAnimal()) && this.getGender().equals(a1.getGender()))
			s1 = "Same Animal Same Gender";
		else if (!this.getAnimal().equals(a1.getAnimal()))
			s1 = "Bear eats Fish";
		else 
			s1 = "null";				
		return s1;
	}
	/**
	 * getGender --
	 * Returns a String with the animals gender
	 * @return a gender, either male or female
	 */
	public String getGender() {
		return gender1;
	}
	/**
	 * getStrength --
	 * Returns a float with the animals strength
	 * @return a float value representing the animals strength
	 */
	public float getStrength() {
		return strength;
	}
	/**
	 * toString --
	 * Returns a string representing the current state of the animal object.
	 * @return a string with the current state of the ecosystem.
	 */
	public String toString() {
		return pets;
	}
}
