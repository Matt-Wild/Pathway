package mainpackage;

public class CollisionData {
	
	private boolean collisionDetected;
	private int collisionDistance;
	
	public CollisionData(boolean detected, int distance) {
		collisionDetected = detected;
		collisionDistance = distance;
	}
	
	public boolean getDetection() {
		return collisionDetected;
	}
	
	public int getDistance() {
		return collisionDistance;
	}

}
