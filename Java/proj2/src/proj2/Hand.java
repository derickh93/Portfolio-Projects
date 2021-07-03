package proj2;

/**
 * <p>Title: The Hand class</p>
 *
 * <p>Description: This class represents a Hand of playing cards. takes the cards
 * dealt from the deck and enters them consecutively into the array cards 
 * the default is a hand of size 10 but only 7 cards are dealt. also keeps a 
 * count of the number of cards in the hand. makes a copy of the hand which 
 * can be altered. has methods to replace a card in the hand, make a copy of 
 * the hand, search if the hand contains a specific card, find the number
 * of face cards in the hand, find the lowest card in the hand, replace
 * the lowest card in the hand, and to make a string containing a list
 * of all the cards currently in the hand. </p>
 * 
 * @author Kuldeep Singh Hare
 */
public class Hand {
	
	// instance variables
	private Card[] cards;
	private int numCards;
	
	/**
	 * default Hand constructor --
	 * allocates an array that is capable of storing at most 10 Card references
	 * sets the number of cards to 0
	 */
	public Hand() {
		cards = new Card[10];
		numCards = 0;
	}
	
	/** 
	 * insertCard method -- 
	 * accepts a reference to the Card object to be stored in the next 
	 * position in the cards array - checks to make sure the array isn't full
	 * @param theCard a reference to the Card object to be stored in the array
	 */
	public void insertCard(Card theCard) {
		if (numCards < cards.length)
		{
			cards[numCards] = theCard;
			numCards++;
		}
	}
	
	/**
	 * getCards method --
	 * makes a copy of the cards array and returns the address of the copy; 
	 * the size of the array that is returned is based upon the number of 
	 * cards currently in the hand
	 * @return a reference to the copy of the cards array 
	 */
	public Card[] getCards() {
		Card[] copy = new Card[numCards];
		for (int i = 0; i < numCards; ++i) {
			copy[i] = cards[i];
		}
		return copy;
	}

	/**
	 * replaceCard method -- 
	 * accepts a reference to a new Card object and the position in which 
	 * the reference should be stored; the position is expected to be in the
	 * range 0 to (number of cards -1); position is validated to ensure that 
	 * it is in this range
	 * @param pos the index where the card should be stored
	 * @param theCard the reference to the Card object to be stored in the array
	 */
	public void replaceCard(int pos, Card theCard) {
		if ((pos >= 0) && (pos < numCards))
			cards[pos] = theCard;
	}
	
	/**
	 * searchCard method --
	 * accepts a reference to a Card to search for and determines whether 
	 * or not that card exists in the hand 
	 * @param theCard a reference to the Card to search for
	 * @return true if the card is found, false otherwise
	 */
	public boolean searchCard(Card theCard) {
		Boolean yup = false;
		for (int i = 0; i < numCards; ++i) {
			if (cards[i].equalCard(theCard)) { 
				yup = true;
				break; 
			}
			else 
				yup = false;
		}	
		return yup;
	}
	
	/** 
	 * findNumFaceCards method --
	 * counts the number of face cards (i.e. value of 11, 12 or 13) in the hand and
	 * returns the count
	 * @return the number of face cards in the hand
	 */
	public int findNumFaceCards() {
		int faceCardCount = 0;
		for (int i = 0; i < 7; ++i) {
			if (cards[i].getValue() >= 11)
				++faceCardCount;
		}
		return faceCardCount;
	}
	
	/**
	 * findLowCard method --
	 * finds and returns the position of the lowest card
	 * @return the index of the lowest card
	 */	
	public int findLowCard() {
		int lowestCard = 13;
		int position = 0;
		for (int i = 0; i < numCards; ++i) {
			if (cards[i].getValue() <= lowestCard) 
				lowestCard = cards[i].getValue();
		}
		for (int i = 0; i < numCards; ++i) {
			if (lowestCard == cards[i].getValue()) 
				position = i;
		}
		return position;			
	}
	
	/**
	 * replaceLowCard method --
	 * accepts a reference to a Card object and replaces the card 
	 * having the lowest value with the new card
	 * @param theCard a reference to the Card object that will replace the 
	 * lowest card
	 */
	public void replaceLowCard(Card theCard) {
		cards[this.findLowCard()] = theCard;
	}
	
	/**
	 * toString method --
	 * creates and returns a reference to a String with each of the card values
	 * on a separate line
	 * @return a reference to a String containing the state of the hand
	 */	
	public String toString() {
		
		String printHand = new String();
		for (int i = 0; i < 7; ++i) {
			String nextInHand = ("Card " + (i + 1) + ": " + cards[i].toString() + "\n");
			printHand = printHand + nextInHand;
		}
		return printHand;
	}

}