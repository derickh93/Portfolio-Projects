package proj2fa16;
import java.util.Random;
import javax.swing.JOptionPane;
/**
 * <p>Title: proj2fa16</p>
 * <p>Description: Proj2App Class - The Proj2App class is responsible for testing our Bear, Fish,
 * and Animal classes. The user enters a size for the ecosystem, which is between 10-50(inclusive).
 * Then a random ecosystem of the chosen size is created. Then if it is decided that an animal will 
 * move forward, one is chosen at random. The required movements are done and this will occur 7 times.
 * After each occurence the ecosystem is displayed to the console.</p>
 * @author <Derick Hansraj>
 */
public class Proj2App {
	public static void main (String [] args) {
		/**
		 * <p> Name: main method </p>
		 * 
		 * @param args values to be sent to the method
		 */
		String input = JOptionPane.showInputDialog("Please enter a river size between 10 and 50 (inclusive)");
		int result = Integer.parseInt(input);
		Animal[] ecosystem = new Animal[result];
		Random rand = new Random();
		String s2 = new String();
		for(int i = 0; i < ecosystem.length; i++) {

			ecosystem[i] = new Animal();
		}
		System.out.print("The ecosystem consist of the following: \n");
		for(int i = 0; i < ecosystem.length; i++) {
			String s1 = new String();
			if(ecosystem[i] == null)
				s1 = s1 + "null,";
			else
				s1 = s1 + ecosystem[i].toString() + ",";
			System.out.print(s1);
		}

		for(int a = 0; a < 10; a++) {
			int randSpot = rand.nextInt(result-1) + 0; 
			if (ecosystem[randSpot].moveDecison() == true) {
				System.out.println("\n\nWe will move the animal in spot " + randSpot);
				if(ecosystem[randSpot].collideAnimal(ecosystem[randSpot+1]).equals("null")) {
					s2 = "There is no animal here";
				}
				else if (ecosystem[randSpot].collideAnimal(ecosystem[randSpot+1]).equals("Same Animal Different Gender")) {
					for(int j = 0; j < ecosystem.length; j++){
						if(ecosystem[j].getAnimal().equals("null"))
							ecosystem[j] = new Animal(ecosystem[randSpot].getAnimal());
						else
							break;
					}
				}
				else if (ecosystem[randSpot].collideAnimal(ecosystem[randSpot+1]).equals("Same Animal Same Gender")) {
					if(ecosystem[randSpot].getStrength() > ecosystem[randSpot+1].getStrength()) {
						ecosystem[randSpot+1] = ecosystem[randSpot];
						ecosystem[randSpot] = null;
					}
				}
				else if (ecosystem[randSpot].collideAnimal(ecosystem[randSpot+1]).equals("Bear eats Fish")) {
					if(ecosystem[randSpot].getAnimal().equals("Fish")) {
						ecosystem[randSpot] = null;
					}
				}
				else if (ecosystem[randSpot].getAnimal().equals("Bear")) {
					ecosystem[randSpot+1] = ecosystem[randSpot];
					ecosystem[randSpot] = null;
				}
				else
					s2 = "There is no animal here";
			}
			else 
				System.out.println("\n We will not move an animal" + "\n" + s2);
			System.out.println("");

			System.out.println("The ecosystem now consist of the following: ");
			for(int i = 0; i < ecosystem.length; i++) {
				String s1 = new String();
				if(ecosystem[i] == null)
					s1 = s1 + "null,";
				else
					s1 = s1 + ecosystem[i].toString() + ",";
				System.out.print(s1);
			}
		}
	}
}
