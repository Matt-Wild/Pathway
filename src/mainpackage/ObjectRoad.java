package mainpackage;

public class ObjectRoad extends Object {

	public ObjectRoad(int x, int y) {
		super(x, y);
		foregroundHitboxes.add(new Hitbox(x, y, 50, 50));
	}
	
	public void draw(OTManager otm) {
		otm.request(x, y, "ROAD");
		super.draw(otm);
	}

}
