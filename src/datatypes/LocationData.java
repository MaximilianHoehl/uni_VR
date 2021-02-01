package datatypes;

public class LocationData {
	
	private String street;
	private String town;
	private int postcode;
	private String country;
	
	public LocationData(String locationstring) {
		
		String[] splitted = locationstring.split("\\s+");
		street = splitted[0];
		postcode = Integer.valueOf(splitted[1]);
		town = splitted[2];
		country = splitted[3];
	}
	public LocationData(String street, String town, int postcode, String country) {
		
		this.street = street;
		this.town = town;
		this.postcode = postcode;
		this.country = country;
	}
	
	public String getLocationstring(){
		return street + " " + String.valueOf(postcode) + " " + town + " " + country;
	}
	public String getStreet() {
		
		return street;
	}
	public int getPostcode() {
		
		return postcode;
	}
	public String getTown() {
		
		return town;
	}
	public String getCountry() {
		
		return country;
	}
}
