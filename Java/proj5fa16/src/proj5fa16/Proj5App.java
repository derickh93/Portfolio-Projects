package proj5fa16;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Title: Project5App.java
 * Description: The application class creates an SFacebook object. The specified commands in facebookData.txt are processed and
 * displayed.
 *
 * @author Derick Hansraj
 */
public class Proj5App
{
	public static void main(String[] args) throws FileNotFoundException
	{
		SFacebook facebookOne = new SFacebook();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new File("facebookData.txt"));
		String[] split = sc.nextLine().split(" ", 3);
		String action = split[0];
		String f1;
		while (!action.equals("X"))
		{
			f1 = split[1];
			if (action.equals("P"))
			{
				System.out.println("Input: " + action + " " + f1 + " " + split[2]);

				int secLevel = Integer.parseInt(split[2]);
				facebookOne.addToFacebook(f1, secLevel);
				System.out.println(facebookOne.toString());
			}
			else if (action.equals("F"))
			{
				try
				{
					System.out.println("Input: " + action + " " + f1 + " " + split[2]);
					facebookOne.makeFriends(f1, split[2]);
					System.out.println(facebookOne.toString());

				} catch (FriendNotFoundException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			else if (action.equals("U"))
			{
				try
				{
					System.out.println("Input: " + action + " " + f1 + " " + split[2]);
					facebookOne.breakFriendship(f1, split[2]);
					System.out.println(facebookOne.toString());
				} catch (FriendNotFoundException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			else if (action.equals("L"))
			{
				try
				{
					System.out.println("Input: " + action + " " + f1);
					System.out.println(facebookOne.getFriends(f1) + "\n");
				} catch (FriendNotFoundException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			else if (action.equals("Q"))
			{
				try
				{
					System.out.println("Input: " + action + " " + f1 + " " + split[2]);
					if (facebookOne.getfriendStatus(f1, split[2]))
					{
						System.out.println(f1 + " and " + split[2] + " are friends.\n");
					}
					else
					{
						System.out.println(f1 + " and " + split[2] + " are not friends.\n");
					}
				} catch (FriendNotFoundException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			else if (action.equals("V"))
			{
				try
				{
					System.out.println("Input: " + action + " " + f1);
					System.out.println(facebookOne.getFriendsByLevel(f1));
				} catch (FriendNotFoundException ex)
				{
					System.out.println(ex.getMessage());
				}
			}
			split = sc.nextLine().split(" ", 3);
			action = split[0];
		}
	}
}