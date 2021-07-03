package proj1fa16;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
/**
 * <p>Title: Proj1fa16</p>
 *
 * <p>Description: BallCage Class - An object of type BallCage contains 75
 * Bingo Balls. There are methods to get a cage through a default constructor,
 * get a random ball, get a count of the amount of balls left in the cage and
 *  a to string method which allows the user to see the current state of the 
 *  cage.</p>
 *
 * @author <Derick Hansraj>
 */

public class BallCage extends BingoBall {
	//instance variables
	BingoBall [] b1 = new BingoBall[75];
	List<BingoBall> list2 = new ArrayList<BingoBall>();
	int ballCount = b1.length;

	/**
	 * default constructor --
	 * Creates a ball cage and assigns it with 75 bingo balls.
	 */
	public BallCage() {
		for(int i = 0; i < b1.length; i++) {
			b1[i] = new BingoBall(i + 1);
		}
	}
	/**
	 * getRandom --
	 * Returns a random bingo ball, while also keeping count of the amount
	 * of balls in the cage and the balls that need to be removed.
	 * @return a random ball from the cage
	 */
	public BingoBall getRandom() {
		int rnd = new Random().nextInt(b1.length);
		do {
			rnd = new Random().nextInt(b1.length);
		}
		while (list2.contains(b1[rnd]));
		list2.add(0, b1[rnd]);
		ballCount --;
		return b1[rnd];
		}
	/**
	 * getRemaining --
	 * Returns the number of remaining balls in the cage.
	 * @return the numerical state of the ball cage
	 */
	public int numBalls() {
		return ballCount;
		
	}
    /**
	 * toString --
	 * Returns a string representing the current state of the ball cage.
	 * @return a string with the current state of the ball cage.
	 */
	public String toString() {
		List<BingoBall> list = new ArrayList<BingoBall>(Arrays.asList(b1));
		list.removeAll(list2);
		return list.toString();
		
	}

}
