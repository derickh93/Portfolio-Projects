package proj5fa16;

/**
 * Title: Node Class
 * Description: A node class capable of storing a reference to an object and a reference to the next node in a
 * linked list. Accessors and mutators are defined for both.
 *
 * @author Derick Hansraj
 */
@SuppressWarnings("hiding")
public class Node<Friend> {
    private Friend data;
    private Node<Friend> next;

    /**
     * parameterized constructor - Initializes data to the specified Friend reference. next is set to null.
     *
     * @param newPerson the Friend reference to be stored in the node
     */
    public Node(Friend newFriend) {
        data = newFriend;
        next = null;
    }

    /**
     * parameterized constructor - Initializes data and next to specified values.
     *
     * @param newPerson the Friend reference to be stored in the node
     * @param nextNode  the reference to the next node in the list
     */
    public Node(Friend newFriend, Node<Friend> nextNode) {
        data = newFriend;
        next = nextNode;
    }

    /**
     * setPerson - Stores a new Friend reference in data.
     *
     * @param newFriend the Friend reference to be stored in the node
     */
    public void setFriend(Friend newFriend) {
        data = newFriend;
    }

    /**
     * setNext - Stores a new Node reference in next.
     *
     * @param nextNode the Node reference to be stored in next
     */
    public void setNext(Node<Friend> nextNode) {
        next = nextNode;
    }

    /**
     * getPerson - Returns the Friend reference stored in data.
     *
     * @return a reference to the data stored in the node
     */
    public Friend getFriend() {
        return data;
    }

    /**
     * getNext - Returns the Node reference stored in next.
     *
     * @return the Node reference stored in next
     */
    public Node<Friend> getNext() {
        return next;
    }
}