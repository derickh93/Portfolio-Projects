
public class Program1App {
	public static void main (String [] args) {
	Sudoku s1 = new Sudoku();
	s1.fillGrid();
	s1.show();
	System.out.println(s1.evaluate());
	}
}
