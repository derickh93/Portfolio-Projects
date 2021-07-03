package gm;

import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GoogleApp {

	public static void main(String[] args) {
		
		GoogleFrame gf = new GoogleFrame();
		
		gf.setTitle("Google Map");
		gf.setVisible(true);
		gf.setSize(new Dimension(1024, 768));
		gf.setResizable(false);
		gf.setLocationRelativeTo(null);
		gf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}