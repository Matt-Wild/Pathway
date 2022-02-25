package mainpackage;

public class OTRequest {
	
	private int x, y;
	private String ID;
	
	public OTRequest(int x, int y, String ID) {
		this.x = x;
		this.y = y;
		this.ID = ID;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String getID() {
		return ID;
	}

}
