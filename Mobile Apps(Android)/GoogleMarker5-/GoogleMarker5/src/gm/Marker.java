package gm;

public class Marker {
	
	// will need instance variables to store latitude, longitude, color, label, category
	private String latitude,longitude, color, label, category;

	// will need appropriate parameterized constructor, accessors and mutators
	public Marker(String latitude, String longitude, String color, String label, String category) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.color = color;
		this.label = label;
		this.category = category;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	// will need toString that outputs in format of ex.
	
	// &markers=color:red%7Clabel:A%7C40.729961,-73.590879
	
	// A is the value of the label
	// there is "%7C" separating the value of the color and the word label
	// there is "%7C" separating the value of the label and the latitude
	
	public String toString() { 
		StringBuilder str = new StringBuilder();
		str.append("color:" + color + "%7Clabel:" + label + category + latitude + longitude);
		return str.toString();
	}
}
