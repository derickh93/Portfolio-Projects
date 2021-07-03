package proj1fa16;
/**
 * <p>Title: proj1fa16</p>
 * <p>Description: Proj1Testing Class - The Proj1Testing class is responsible for
 * testing our bingo game classes.The user can invoke methods to get a cage through 
 * a default constructor,see a current letter, see a current number,get a random ball,
 *  get a count of the amount of balls left in the cage and a to string method which allows 
 * the user to see the current state of the seat or just a single bingo ball.</p>
 *
 * @author <Derick Hansraj>
 */


public class Proj1Testing {
    /**
     * <p> Name: main method </p>
     * 
     * @param args values to be sent to the method
     */
	public static void main(String [] args) {
		
		//BingoBall Class Testing 
		//I tested the bingo ball class by creating a BingoBall object and for loop.
		//I then used the for loop to test all of the methods in the class on all 
		//possible BingoBall objects.
		for (int i = 1; i < 76; i++) {
			BingoBall balli = new BingoBall(i);
			System.out.println("Testing Ball Number " + i);
			System.out.println(balli.getLetter());
			System.out.println(balli.getNumber());
			System.out.println(balli.toString() + "\n");
		}
		
		//BallCage Class Testing
		//I created a BallCage object. Then i displayed the contents of the BallCage
		//along with the amount of balls inside.
		BallCage cage1 = new BallCage();
		System.out.println("Bingo Balls in the Cage: " + cage1.toString());
		System.out.println("Amount of Balls in the Cage: " + cage1.numBalls() + "\n");
		//I created a for loop to display 75 random balls. Along with each random ball
		//also displayed is the number of balls left in the cage, as well as the current
		//state of the cage.
		for(int i = 0; i < 75; i++){
			System.out.println("Random Ball " + (i + 1) + ": " + cage1.getRandom());
			System.out.println("Remaining Bingo Balls After Ball " + (i+1) + ": " + 
					cage1.toString());
			System.out.println("Number of Balls Remaining After Ball " + (i+1) + ": " 
					+ cage1.numBalls() + "\n");
			
		BingoCard c1 = new BingoCard();
		System.out.println(c1);
			
		}

	}
}
