package proj5fa16;

/**
 * Title: FriendList Class
 * Description: A FriendList object consists of nodes that can be accessed and changed to represent a
 * friend list.
 *
 * @author Derick Hansraj
 */
public class FriendList {
    private int count;
    private Node<Friend> first, currentPos;

    /** default constructor - Initializes count to 0 and first to a Node that has no item and points to null. 
    */
    public FriendList() {
        count = 0;
        first = new Node<Friend>(null, new Node<Friend>(null));
    }

    /**
     * size - Returns the number of friends in the friend list.
     *
     * @return count - the number of friends in the friend list
     */
    public int size() {
        return count;
    }

    /**
     * isEmpty - Checks if the friend list is empty.
     *
     * @return true if the list does not contain at least one Friend, false otherwise
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * addToFront - Adds a Friend to the front of the friend list.
     *
     * @param p1- the person to be added
     */
    public void addToFront(Friend p1) {
        first.setNext(new Node<Friend>(p1, first.getNext()));
        count++;
    }

    /**
     * remove - Removes a Friend from the friend list.
     *
     * @param f1- the Friend to be removed from the friend list
     * @return a reference to the Friend that was removed
     */
    public Friend remove(Friend f1) {
        resetList();
        while (currentPos.getNext().getFriend() != null)
        {
            if (currentPos.getNext().getFriend().equals(f1))
            {
                currentPos.setNext(currentPos.getNext().getNext());
                count--;
                return f1;
            }
            getNextFriend();
        }
        return null;
    }

    /**
     * resetList - Sets currentPos to the head.
     */
    public void resetList() {
        currentPos = first;
    }

    /**
     * getNextFriend - Returns the next Friend in the list and shifts currentPos over appropriately
     *
     * @return a reference to the next Friend in the list
     */
    public Friend getNextFriend() {
        currentPos = currentPos.getNext();
        return currentPos.getFriend();
    }

    /**
     * search - Checks for the existence of the specified Friend.
     *
     * @param f1 the Friend to be searched for
     * @return true if the Friend is in the list, false otherwise
     */
    public boolean search(Friend f1) {
        resetList();
        while (currentPos.getNext().getFriend() != null)
        {
            if (getNextFriend().equals(f1))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * listOfFriends - Displays the contents of the friend list.
     *
     * @return a String containing a list of friend names
     */
    public String listOfFriends() {
        String s1 = new String();
        resetList();
        while (currentPos.getNext().getFriend() != null)
        {
            s1 += getNextFriend().getName() + " ";
        }
        return s1;
    }
}