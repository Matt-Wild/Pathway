package mainpackage;

import java.util.ArrayList;

public class ObjectList {
	
	private ArrayList<Object> objects = new ArrayList<Object>();
	
	public ObjectList(String[] objectIDs, int xPos, int groundIndex) {
		int yPos = 800 - (50 * groundIndex);
		
		for (int i = 0, fillY = 0; i < 16 - groundIndex; i++, fillY += 50) {
			generateObject("NONE", xPos, fillY);
		}
		
		for (int i = 0; i < objectIDs.length; i++, yPos += 50) {
			generateObject(objectIDs[i], xPos, yPos);
		}
		
		generateObject("BEDROCK", xPos, yPos);
	}
	
	private void generateObject(String ID, int xPos, int yPos) {
		if(ID == "NONE") {
			objects.add(new Object(xPos, yPos));
		} else if(ID == "BEDROCK") {
			objects.add(new ObjectBedrock(xPos, yPos));
		} else if(ID == "ROAD") {
			objects.add(new ObjectRoad(xPos, yPos));
		} else {
			System.out.println("ERROR: Could not define Object with given ID: " + ID);
		}
	}
	
	public void draw(OTManager otm) {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).draw(otm);
		}
	}
	
	public void changeX(int x) {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).changeX(x);
		}
	}
	
	public int getX() {
		return objects.get(0).getX();
	}
	
	public Object getObject(int i) {
		return objects.get(i);
	}
	
}
