package mainpackage;

import java.awt.Graphics;
import java.util.ArrayList;

public class WorldManager {
	
	private StructureGenerator sg;
	private ArrayList<ObjectList> worldArray = new ArrayList<ObjectList>();
	private String genStage = "R", reverseGenStage = "S2";
	private int worldIndexAdjustment = 0;
	private OTManager otm;
	
	public WorldManager(ImageLoader imageLoader) {
		this.sg = new StructureGenerator();
		
		otm = new OTManager(imageLoader);
		
		appendRandomStructure();
	}
	
	public void update(Player player) {
		int playerRightOCIndex = player.getRightSOCIndex();
		int playerLeftOCIndex = player.getLeftSOCIndex();
		if(playerRightOCIndex > getRightOCIndex()) {
			appendRandomStructure();
		}
		if(playerLeftOCIndex < getLeftOCIndex()) {
			prependRandomStructure();
		}
	}
	
	public void draw(Graphics g, Player player) {
		for (int i = 0; i < worldArray.size(); i++) {
			worldArray.get(i).draw(otm);
		}
		
		otm.draw(g, player);
	}
	
	public CollisionData[] getCollisionData(int topYIndex, int bottomYIndex, int lowXIndex, int highXIndex, int x, int y, int width, int height, int vx, int vy) {
		boolean xCollisionDetected = false;
		boolean yCollisionDetected = false;
		int xCollisionDistance = 0;
		int yCollisionDistance = 0;
		
		lowXIndex -= worldIndexAdjustment;
		highXIndex -= worldIndexAdjustment;
		
		for(int i = lowXIndex; i <= highXIndex; i++) {
			try {
				ObjectList queryArray = worldArray.get(i);
				
				for(int in = topYIndex; in <= bottomYIndex; in++) {
					Object queryObject = queryArray.getObject(in);
					
					CollisionData queryX = queryObject.getHorizontalCollision(x, y, width, height, vx, vy);
					CollisionData queryY = queryObject.getVerticalCollision(x, y, width, height, vx, vy);
					
					if(queryX.getDetection() && (!xCollisionDetected || queryX.getDistance() < xCollisionDistance)) {
						xCollisionDetected = true;
						xCollisionDistance = queryX.getDistance();
					}
					if(queryY.getDetection() && (!yCollisionDetected || queryY.getDistance() < yCollisionDistance)) {
						yCollisionDetected = true;
						yCollisionDistance = queryY.getDistance();
					}
				}
			} catch(ArrayIndexOutOfBoundsException e){
				
			} catch(IndexOutOfBoundsException ex) {
				
			}
			
		}
		
		CollisionData[] result = {new CollisionData(xCollisionDetected, xCollisionDistance), new CollisionData(yCollisionDetected, yCollisionDistance)};
		return result;
	}
	
	private void appendRandomStructure() {
		if(worldArray.size() == 0) {
			ObjectList[] newStructure = sg.getRandomRoad(0);
			
			appendStructure(newStructure);
		} else {
			// GETTING APPROPRIATE X LOCATION
			ObjectList lastList = worldArray.get(worldArray.size() - 1);
			int nextX = lastList.getX() + 50;
			
			// DECIDING WHAT TO GENERATE
			ObjectList[] newStructure;
			if(genStage == "R") {
				newStructure = sg.getRandomRoad(nextX);
			} else {
				newStructure = sg.getRandomStructure(nextX);
			}
			
			// IMPLEMENTING NEW RANDOM STRUCTURE
			appendStructure(newStructure);
		}
		
		rotateGenStage();
	}
	
	private void prependRandomStructure() {
		// GETTING APPROPRIATE X LOCATION
		ObjectList firstList = worldArray.get(0);
		int nextX = firstList.getX() - 50;
					
		// DECIDING WHAT TO GENERATE
		ObjectList[] newStructure;
		if(reverseGenStage == "R") {
			newStructure = sg.getRandomRoad(nextX);
		} else {
			newStructure = sg.getRandomStructure(nextX);
		}
					
		// IMPLEMENTING NEW RANDOM STRUCTURE
		prependStructure(newStructure);
				
		rotateReverseGenStage();
	}
	
	private void appendStructure(ObjectList[] structure) {
		for (int i = 0; i < structure.length; i++) {
			worldArray.add(structure[i]);
		}
	}
	
	private void prependStructure(ObjectList[] structure) {
		worldIndexAdjustment -= structure.length;
		int adjustment = (structure.length - 1) * -50;
		for (int i = structure.length - 1; i >= 0; i--) {
			structure[i].changeX(adjustment);
			worldArray.add(0, structure[i]);
		}
	}
	
	private void rotateGenStage() {
		if(genStage == "S1") {
			genStage = "S2";
		} else if(genStage == "S2") {
			genStage = "R";
		} else {
			genStage = "S1";
		}
	}
	
	private void rotateReverseGenStage() {
		if(reverseGenStage == "S1") {
			reverseGenStage = "R";
		} else if(reverseGenStage == "S2") {
			reverseGenStage = "S1";
		} else {
			reverseGenStage = "S2";
		}
	}
	
	private int getRightOCIndex() {
		return worldArray.size() - 1 + worldIndexAdjustment;
	}
	
	private int getLeftOCIndex() {
		return worldIndexAdjustment;
	}
	
}
