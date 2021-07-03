package proj1fa16;
/**
 * <p>Title: Project1fa16
 * <p>Description: BingoBall Class - An object of type BingoBall contains a 
 * letter and number which will be used to create a bingo ball. The class defines 
 * methods to get the number and letter of the bingo ball and a toString method
 * which allows the user to see the current state of the bingo ball.</p>
 * @author <Derick Hansraj>
 */

public class BingoBall 
{
	//instance variables
    private String letter;
    private int number;

    /**
	 * parameterized constructor -- The ball is given a number and 
	 * a letter based upon the arguments sent. 
	 * @param number - The number used to find the bingo ball.
	 * @return - a letter to go along with the number of a ball.
	 * **/
    public BingoBall() {
    	letter="null";
    	number=-1;
    }
    
    public BingoBall(int number)
    {       
    	this.number = number;

        if(number < 1 || number >75)
        {
            System.out.println("Error");
        }
        else if(number <16)
        {
            letter= "B";
            return;
        }
        else if(number <31)
        {
            letter = "I";
            return;
        }
        else if(number < 46)
        {
            letter = "N";
            return;
        }
        else if(number < 61)
        {
            letter  = "G";
            return;
        }
        letter= "O";
    }
    
    /**
	 * getNumber --
	 * Returns the number of the bingo ball.
	 * @return the ball number stored in the instance variable number
	 */
    public int getNumber() {
    	return number;
    }
    /**
	 * getLetter --
	 * Returns the letter of the bingo ball.
	 * @return the letter stored in the instance variable letter
	 */
    public String getLetter() {
    	return letter;
    }
    /**
	 * toString --
	 * Returns a string representing the current state of the bingo ball.
	 * @return the letter and number of a bingo ball.
	 */
    public String toString()
    {
    	return letter + number;
    }
}