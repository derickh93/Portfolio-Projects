package proj5fa16;

/**
 * Title: SFacebook Class
 * Description: This class contains the methods to create an SFacebook object.It uses the theMembers array, and
 * displays its contents.
 *
 * @author Derick Hansraj
 */
public class SFacebook {
    private Friend[] theMembers;
    private int count;

    /** default constructor - Initializes theMembers with a length of 10 and count to 0 */
    public SFacebook() {
        theMembers = new Friend[10];
        count = 0;
    }

    /**
     * addToFacebook - Adds a Friend to theMembers.
     *
     * @param name- the name of the Friend to be added
     * @param Level- 0 for close friends only. 1 for friends of friends
     */
    public void addToFacebook(String name, int level) {
        // expands the capacity if the array becomes full
        if (count == theMembers.length)
        {
            Friend[] temp = new Friend[count * 2];
            for (int i = 0; i < count; i++)
            {
                temp[i] = theMembers[i];
            }
            theMembers = temp;
        }
        theMembers[count] = new Friend(name, level);
        count++;
    }

    /**
     * makeFriends - makes two Friend objects friends
     *
     * @param first- String name of first Friend
     * @param second- String name of second Friend
     */
    public void makeFriends(String first, String second) {
        if (!first.equals(second))
        {
            Friend firstFriend = findFriend(first);
            Friend secondFriend = findFriend(second);
            firstFriend.addFriend(secondFriend);
            secondFriend.addFriend(firstFriend);
        }
    }

    /**
     * breakFriendship - unfriends two Friend objects
     *
     * @param first- String name of first Friend
     * @param second- String name of second Friend
     */
    public void breakFriendship(String first, String second) {
        if (!first.equals(second)) 
        {
            Friend firstFriend = findFriend(first);
            Friend secondFriend = findFriend(second);
            firstFriend.unfriend(secondFriend);
            secondFriend.unfriend(firstFriend);
        }
    }

    /**
     * getFriends - Returns a list of a specific Friend object
     *
     * @param first- String name of person
     * @return a display of the desired Friend objects friend list
     */
    public String getFriends(String first) {
        return first + " is friends with: " + findFriend(first).getFriends();
    }

    /**
     * getFriendsByLevel - Returns the friends and friends of friends of a person
     *
     * @param first String name of Friend
     * @return a display of a specific Friend objects by level
     */
    public String getFriendsByLevel(String first)
    {
        return findFriend(first).getFriendsByLevel();
    }

    /**
     * getfriendStatus - Check to see if two Friend objects are friends
     *
     * @param first String name of first Friend
     * @param second String name of second Friend
     * @return true if first is friends with second, else it is false
     */
    public boolean getfriendStatus(String first, String second) {
        return findFriend(first).friendsWith(findFriend(second));
    }

    /**
     * toString - Returns the friends of everyone in theMembers
     *
     * @return a output of every Friends friend list
     */
    public String toString() {
        String s1 = new String();
        for (int i = 0; i < count; i++)
        {
            s1 += theMembers[i].getName() + " is friends with: " + theMembers[i].getFriends() + "\n";
        }
        return s1;
    }

    /**
     * findFriend - Searches for a Friend by name, then returns a reference to the Friend object
     *
     * @param s1 the String name of the Friend to be searched for
     * @return a reference to the Friend object if it is found
     */
    public Friend findFriend(String s1)
    {
        for (int i = 0; i < count && s1 != null; i++)
        {
            if (theMembers[i].getName().equals(s1))
            {
                return theMembers[i];
            }
        }
        throw new FriendNotFoundException();
    }
}