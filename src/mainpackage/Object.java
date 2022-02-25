package mainpackage;

import java.util.ArrayList;

public class Object {
	
	protected int x, y, width = 50, height = 50;
	protected String ID = "NONE";
	protected Hitbox tileHitbox = new Hitbox(x, y, width, height);
	protected ArrayList<Hitbox> foregroundHitboxes = new ArrayList<Hitbox>();
	
	// TEMPORARY
	protected boolean checked = false;
	
	public Object(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(OTManager otm) {
		if(checked) {
			//int xAdj = player.getXAdj();
			//int yAdj = player.getYAdj();
			
			//g.setColor(Color.GREEN);
			//g.fillRect(x + xAdj, y + yAdj, width, height);
			
			checked = false;
		}
	}
	
	public boolean onScreen(int sWidth, int sHeight, int xAdj, int yAdj) {
		int drawX = x + xAdj;
		int drawY = y + yAdj;
		
		if(drawX > 0 - width && drawX < sWidth) {
			if(drawY > 0 - height && drawY < sHeight) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean intersectingTile(int x, int y, int width, int height) {
		return tileHitbox.intersectingBox(x, y, width, height);
	}
	
	public CollisionData getHorizontalCollision(int x, int y, int width, int height, int vx, int vy) {
		checked = true;
		boolean detected = false;
		int shortestDistance = 0;
		
		for (int i = 0; i < foregroundHitboxes.size(); i++) {
			
			if(foregroundHitboxes.get(i).horizontalCollisionDetected(x, y, width, height, vx, vy)) {
				int queryDistance = foregroundHitboxes.get(i).horizontalCollisionDistance(x, width, vx);
				
				if(!detected || Math.abs(shortestDistance) > Math.abs(queryDistance)) {
					shortestDistance = queryDistance;
					detected = true;
				}
			}
		}
		
		return new CollisionData(detected, shortestDistance);
	}
	
	public CollisionData getVerticalCollision(int x, int y, int width, int height, int vx, int vy) {
		boolean detected = false;
		int shortestDistance = 0;
		
		for (int i = 0; i < foregroundHitboxes.size(); i++) {
			
			if(foregroundHitboxes.get(i).verticalCollisionDetected(x, y, width, height, vx, vy)) {
				int queryDistance = foregroundHitboxes.get(i).verticalCollisionDistance(y, height, vy);
				
				if(!detected || Math.abs(shortestDistance) > Math.abs(queryDistance)) {
					shortestDistance = queryDistance;
					detected = true;
				}
			}
		}
		
		return new CollisionData(detected, shortestDistance);
	}
	
	public void changeX(int x) {
		this.x += x;
		tileHitbox.changeX(x);
		
		for (int i = 0; i < foregroundHitboxes.size(); i++) {
			foregroundHitboxes.get(i).changeX(x);
		}
	}
	
	public int getX() {
		return x;
	}

}
