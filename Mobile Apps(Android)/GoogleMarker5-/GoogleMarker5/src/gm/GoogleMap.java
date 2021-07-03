package gm;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
public class GoogleMap {

	Image mapImage;
	double latitudeCenter;
	double longitudeCenter;
	int imageScale;
	int zoomLevel;
	String strURL;
	
	// this constructor's parameter needs to be changed to the google data
	// structure you made
	GoogleMap(DoubleLinkedList<Marker> dll) {

		// this center is NCC, can be changed later
		latitudeCenter = 40.730543;
		longitudeCenter = -73.591663;
		imageScale = 1;
		zoomLevel = 17;
		
		strURL = "http://maps.googleapis.com/maps/api/staticmap?center=" + latitudeCenter + "," + longitudeCenter + "&zoom="
				+ zoomLevel + "&size=1024x1024";
	
	}

	
	public Image getMap(Marker... markers) {
		
		// call the method in your data structure that will take in the search term (category)
		// and return only those markers' string
		
		// concatenate that string to the strURL

		
		strURL += "&sensor=false&scale=" + imageScale;

		System.out.println(strURL);

		try {
			URL url = new URL(strURL);
			mapImage = ImageIO.read(url);
		}
		catch (IOException ie) {
		}
		
		return mapImage;
	}
}
