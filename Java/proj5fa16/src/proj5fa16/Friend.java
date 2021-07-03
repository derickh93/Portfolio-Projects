package proj5fa16;
/**
 * Title: Friend Class
 * Description: A Friend object stores a name, securityLevel, and a friend list. The list has the ability to be accessed and
 * changed.
 *
 * @author Derick Hansraj
 */
public class Friend
{
    private String name;
    private int securityLevel;
    private FriendList friends;

    /**
     * parameterized constructor - Initializes name and security to the specified argument values and initializes a
     * FriendList for the Friend.
     *
     * @param name- the name of the Friend to be added
     * @param securityLevel- 0 for close friends only. 1 for friends of friends    
     */
    public Friend(String name, int securityLevel) {
        this.name = name;
        this.securityLevel = securityLevel;
        friends = new FriendList();
    }

    /**
     * getName - Returns the Friends name.
     *
     * @return name of the Friend
     */
    public String getName() {
        return name;
    }

    /**
     * addFriend - Adds a Friend to the friend list.
     *
     * @param newFriend the Friend to be added
     */
    public void addFriend(Friend newFriend) {
        friends.addToFront(newFriend);
    }

    /**
     * unfriend - Removes a Friend from the friend list.
     *
     * @param f1- the Friend to be unfriended
     */
    public void unfriend(Friend f1) {
        friends.remove(f1);
    }

    /**
     * friendsWith - Checks if target is in the friend list.
     *
     * @param f1- the Friend to be checked
     * @return true if friends, false otherwise
     */
    public boolean friendsWith(Friend f1) {
        return friends.search(f1);
    }

    /**
     * getFriends - Returns the friends of a Friend.
     *
     * @return the friends of a Friend
     */
    public String getFriends() {
        return friends.listOfFriends();
    }

    /**
     * getAllFriends - Returns the friends depending on security level.
     *
     * @return the immediate friends only if level 0, otherwise includes friends of friends
     */
    public String getFriendsByLevel() {
        if (securityLevel == 1)
        {
            String friendsTemp = friends.listOfFriends();    
            String[] split = friendsTemp.split(" ");   
            String s1 = new String();
            boolean search;
            Friend current;
            for (int i = 0; i < split.length; i++)
            {
                search = false;
                friends.resetList();
                for (int j = 0; j < friends.size() && !search; j++)
                {
                    current = friends.getNextFriend();
                    if (current.getName().equals(split[i]))
                    {
                        s1 += "and \n" + current.getName() + " is friends with: " + current.getFriends();
                        search = true;
                    }
                }
            }
            return name + " is friends with: " + getFriends() + s1 + "\n";
        }
        else
        {
            return name + " is friends with: " + getFriends() + "\n";
        }
    }

    /**
     * equals - Determines if two Friends have the same name
     *
     * @param f1 a reference to a Friend object
     * @return true if two Friends have the same name, otherwise false
     */
    public boolean equals(Friend f1) {
        return (name.equals(f1.getName()));
    }
}