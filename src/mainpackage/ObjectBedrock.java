package mainpackage;

public class ObjectBedrock extends Object {

	public ObjectBedrock(int x, int y) {
		super(x, y);
		foregroundHitboxes.add(new Hitbox(x, y, 50, 50));
	}
	
	public void draw(OTManager otm) {
		otm.request(x, y, "BEDROCK");
		super.draw(otm);
	}

}
