package mainpackage;

public class Hitbox {
	
	private int x, y, width, height;
	
	public Hitbox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean intersectingBox(int x, int y, int width, int height) {
		if(x <= (this.x + this.width) && (x + width) >= this.x) {
			if(y <= (this.y + this.height) && (y + height) >= this.y) {
				return true;
			}
		}
		return false;
	}
	
	public boolean horizontalCollisionDetected(int x, int y, int width, int height, int vx, int vy) {
		if(inXRange(x, width, vx) && inYRange(y, height, vy) && inYRange(y, height, 0)) {
			return true;
		}
		return false;
	}
	
	public int horizontalCollisionDistance(int x, int width, int vx) {
		if(vx > 0) {
			int result = this.x - (x + width);
			if(result >= 0) {
				return result;
			}
			return 0;
		} else {
			int result = (this.x + this.width) - x;
			if(result <= 0) {
				return result;
			}
			return 0;
		}
	}
	
	public boolean verticalCollisionDetected(int x, int y, int width, int height, int vx, int vy) {
		if(inXRange(x, width, vx) && inYRange(y, height, vy) && !inYRange(y, height, 0)) {
			return true;
		}
		return false;
	}
	
	public int verticalCollisionDistance(int y, int height, int vy) {
		if(vy > 0) {
			int result = this.y - (y + height);
			if(result >= 0) {
				return result;
			}
			return 0;
		} else {
			int result = (this.y + this.height) - y;
			if(result <= 0) {
				return result;
			}
			return 0;
		}
	}
	
	private boolean inXRange(int x, int width, int vx) {
		if((x + vx) < (this.x + this.width) && (x + width + vx) > (this.x)) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean inYRange(int y, int height, int vy) {
		if((y + vy) < (this.y + this.height) && (y + height + vy) > (this.y)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void changeX(int x) {
		this.x += x;
	}
	
}
