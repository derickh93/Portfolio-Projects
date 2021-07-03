package gm;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GoogleFrame extends JFrame {

	
	private static final long serialVersionUID = 1L;
	Image mapImage;
	GoogleMap googleMap;
	ImageIcon mapIcon;
	JLabel centerLabel;
	
	

	public GoogleFrame() {

		try {
			URL url = new URL("http://stargate.ncc.edu/~steve/ds-meme.jpg");
			mapImage = ImageIO.read(url);// read the data from Google and store in the variable mapImage
		}
		catch (IOException ie) {
			System.out.println("Error" + ie.getMessage());
		}

		mapIcon= new ImageIcon(mapImage);

		// instantiate your google marker data structure object here
		Marker marker = new Marker();
		
		// instantiate your google map object here and pass that object to the 
		// GoogleMap constructor, this call will need to be modified
		
		googleMap = new GoogleMap();
		
		
		JPanel mainPanel = new JPanel();

		// make a new panel to hold graphic widgets 
		// like labels, dropdown boxes and a button
		

		// make a label
		
		
		// make a drop downbox
		

		// make a button and add an actionListener to it
		

		// add the above widgets you made to the top panel
		

		mainPanel.setLayout(new BorderLayout());
		
		centerLabel = new JLabel(mapIcon);

		// add the top panel to the mainpanel in the NORTH position
		
		mainPanel.add(centerLabel, BorderLayout.CENTER);
		
		add(mainPanel);



	}

	

}