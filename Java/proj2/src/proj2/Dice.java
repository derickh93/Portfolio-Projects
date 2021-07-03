package proj2;

/**
 * Title: Dice
 * Description: takes the dice and executes various methods invoked for the 
 * dice class and returns the corresponding values asked for
 * 
 * @author Kuldeep Singh Hare
 *
 */
public class Dice {

	/**
	 * dieOne - This variable contains the string version of the value of dice1
	 */
	String dieOne;
	/**
	 * dieTwo - This variable contains the string version of the value of dice2
	 */
	String dieTwo;
	/**
	 * dieThree - This variable contains the string version of the value of dice3
	 */
	String dieThree;
	private int die1;
	private int die2;
	private int die3;
	
	/**
	 * Dice - This constructor sets the face value of the dice
	 * to zero
	 */
	public Dice() {
		die1 = 0;
		die2 = 0;
		die3 = 0;
	}
	
	/**
	 * toString - This method takes the values of the dies
	 * converts them into strings
	 * and concatenates them with spaces
	 * @return returns the values of the dies with spaces 
	 */
	public String toString() {
		String returnMe;
		dieOne = Integer.toString(die1);
		dieTwo = Integer.toString(die2);
		dieThree = Integer.toString(die3);
		returnMe = (dieOne + " " + dieTwo + " " + dieThree);
		return returnMe;
	}
	
	/**
	 * getDieOne -- this method returns the value of dieOne
	 * @return the value of dieOne
	 */
	public int getDie1() {
		return this.die1;
	}
	/**
	 * getDieTwo -- this method returns the value of dieOne
	 * @return the value of dieTwo
	 */
	public int getDie2() {
		return this.die2;
	}
	
	/**
	 * getDieThree -- this method returns the value of dieOne
	 * @return the value of dieThree
	 */
	public int getDie3() {
		return this.die3;
	}
	
	/**
	 * roll -- this method randomizes the values of the dice
	 * @return a random value from 1 to 6 for all the dice
	 */
	public String roll() {
		String returnMe;
		die1 = (int) (Math.random() * 6 + 1);
		die2 = (int) (Math.random() * 6 + 1);
		die3 = (int) (Math.random() * 6 + 1);
		dieOne = Integer.toString(die1);
		dieTwo = Integer.toString(die2);
		dieThree = Integer.toString(die3);
		returnMe = (dieOne + " " + dieTwo + " " + dieThree);
		return returnMe;
	}
	
	/**
	 * calcTotalRoll - This method will calculate the sum of t6he values
	 * rolled on the three dice
	 * @return the sum of the three dice
	 */
	public int calcTotalRoll() {
		return (die1 + die2 + die3);
	}
	
	/**
	 * threeOfAKing method - this method will determine if all three
	 * of the dice have the same value
	 * @return true if they have the same value, false otherwise
	 */
	public String threeOfAKind() {
		if ((die1 == die2) && (die2 == die3) && (die1 == die3))
			return ("Three of a kind.");
		else 
			return ("Not three of a kind.");
		
	}
	
	/**
	 * findRightestDie - This method will determine the highest value 
	 * on the three dice
	 * @return the highest value
	 */
	public int findHighestDie() {
		if ((die1 >= die2) && (die1 >= die3))
			return die1;
		else if ((die2 >= die1) && (die2 >= die3))
			return die2;
		else 
			return die3;
	}
	
	/**
	 * rollOneDie - This method rolls only one of the three dice and returns 
	 * the value of that dice each time it is rolled
	 * 
	 * @param diePick is the parameter required when the method is invoked
	 */
	public void rollOneDie(int diePick) {
		if (diePick == 1)
			die1 = (int) (Math.random() * 6 + 1);
		else if (diePick == 2)
			die2 = (int) (Math.random() * 6 + 1);
		else if (diePick == 3)
			die3 = (int) (Math.random() * 6 + 1);
		
		
	
	}
	
}
