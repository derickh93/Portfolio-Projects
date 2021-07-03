package proj2;

/***
 * Title: Project 2: Card Game
 * Description: This creates a new Deck, which it then shuffles.
 * It then displays the card on the GUI. Then it displayed the 
 * selected outputs the user has asked for.
 * 
 * @author Derick Hansraj
 *
 */
public class Project2 
{
	/**creates a public access, non object creating, program that searches  
	 * for the main function with no return type 
	 * 
	 * @param args the argument is passed to the Deck object 
	 */
	public static void main(String[] args)
	{
		
		/** This displays a new GUI
		 * 
		 */
		GUI theWindow = new GUI();
		
		/** This creates a new deck which it then shuffles
		 * 
		 */
		Deck theDeck = new Deck();
		theDeck.shuffleDeck();
		
		/** This creates three cards from theDeck
		 * 
		 */
		Card card1 = theDeck.dealCard();
		Card card2 = theDeck.dealCard();
		Card card3 = theDeck.dealCard();
		
		/**This displays the three cards created on GUI created.
		 * 
		 */
		theWindow.showCard(card1);
		theWindow.showCard(card2);
		theWindow.showCard(card3);
		
		/** This displays the value and suit of each card
		 * 
		 */
		System.out.println("Card 1 is: " + card1.toString());
		System.out.println("Card 2 is: " + card2.toString());
		System.out.println("Card 3 is: " + card3.toString());

		/** This compares all the cards and determines if there is a pair
		 * 
		 */
		if (card1.getValue() == card2.getValue())
			System.out.println("Pair");
		else if (card1.getValue() == card3.getValue())
			System.out.println("Pair");
		else if (card2.getValue() == card3.getValue())
			System.out.println("Pair");
		else
			System.out.println("Not a pair");
		
		/** This assigns each card value to a variable which it then
		 * uses to compare the cards to determine which is the highest
		 * value
		 */
		int cardOne = card1.getValue();
		int cardTwo = card2.getValue();
		int cardThree = card3.getValue();
		
		if ((cardOne >= cardTwo) && (cardOne >= cardThree))
			System.out.println("The highest value is " + cardOne);
		else if ((cardTwo >= cardOne) && (cardTwo >= cardThree))
			System.out.println("The highest value is " + cardTwo);
		else 
			System.out.println("The highest value is " + cardThree);
		
		/** This assigns the point value of each card to a variable which it then 
		 * sums them up together to get a total
		 */
		int cardOnePoint = card1.getPointValue();
		int cardTwoPoint = card2.getPointValue();
		int cardThreePoint = card3.getPointValue();
		int cardSum = (cardOnePoint + cardTwoPoint + cardThreePoint);
		
		System.out.println("The sum is " + (cardSum));
		
		/** This determines if the user or the computer wins as per the value
		 * of the sum
		 */
		if (cardSum >= 25)
			System.out.println("You win!");
		else 
			System.out.println("Computer wins.");

		
	}
}