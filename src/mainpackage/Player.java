package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Player {
	
	private int x = 50,y = 600, width = 50, height = 75, hitboxXOffset = 8;
	private float vx = 0, vy = 0;
	private KeyManager keyManager;
	private Display display;
	private ImageLoader imageLoader;
	private WorldManager worldManager;
	private BufferedImage defaultFrameState, reversedDefaultFS, walk1, walk2, walk3, walk1L, walk2L, walk3L, 
						  jumpStill, jumpStillL, jumpMove, jumpMoveL, sprint1, sprint2, sprint3, 
						  sprint1L, sprint2L, sprint3L;
	private boolean facingRight = true, sprinting = false, onGround = false, jumpKeyReleased = true;
	
	private String state = "STILL";
	private BufferedImage[] walkAnim, sprintAnim;
	private BufferedImage[] walkAnimL, sprintAnimL;
	private int animCount = 0;
	private int animDelay = 12;
	private int animIndex = 0;
	
	public Player(KeyManager keyManager, Display display, ImageLoader imageLoader, WorldManager worldManager) {
		this.keyManager = keyManager;
		this.display = display;
		this.imageLoader = imageLoader;
		this.worldManager = worldManager;
		
		defaultFrameState = this.imageLoader.loadImage("/textures/player/player.png");
		reversedDefaultFS = createFlipped(defaultFrameState);
		
		walk1 = this.imageLoader.loadImage("/textures/player/walk1.png");
		walk2 = this.imageLoader.loadImage("/textures/player/walk2.png");
		walk3 = this.imageLoader.loadImage("/textures/player/walk3.png");
		walk1L = createFlipped(walk1);
		walk2L = createFlipped(walk2);
		walk3L = createFlipped(walk3);
		
		sprint1 = this.imageLoader.loadImage("/textures/player/sprint1.png");
		sprint2 = this.imageLoader.loadImage("/textures/player/sprint2.png");
		sprint3 = this.imageLoader.loadImage("/textures/player/sprint3.png");
		sprint1L = createFlipped(sprint1);
		sprint2L = createFlipped(sprint2);
		sprint3L = createFlipped(sprint3);
		
		jumpStill = this.imageLoader.loadImage("/textures/player/jumpStill.png");
		jumpMove = this.imageLoader.loadImage("/textures/player/jumpMoving.png");
		jumpStillL = createFlipped(jumpStill);
		jumpMoveL = createFlipped(jumpMove);
		
		walkAnim = new BufferedImage[4];
		walkAnim[0] = walk1;
		walkAnim[1] = walk2;
		walkAnim[2] = walk3;
		walkAnim[3] = walk2;
		
		walkAnimL = new BufferedImage[4];
		walkAnimL[0] = walk1L;
		walkAnimL[1] = walk2L;
		walkAnimL[2] = walk3L;
		walkAnimL[3] = walk2L;
		
		sprintAnim = new BufferedImage[4];
		sprintAnim[0] = sprint1;
		sprintAnim[1] = sprint2;
		sprintAnim[2] = sprint3;
		sprintAnim[3] = sprint2;
		
		sprintAnimL = new BufferedImage[4];
		sprintAnimL[0] = sprint1L;
		sprintAnimL[1] = sprint2L;
		sprintAnimL[2] = sprint3L;
		sprintAnimL[3] = sprint2L;
	}
	
	public void update() {
		// MANAGING KEY PRESSES
		if(keyManager.getKey_LeftShift()) {
			sprinting = true;
		} else {
			sprinting = false;
		}
		if(keyManager.getKey_Up()) {
			if(onGround && jumpKeyReleased) {
				vy = -9;
				jumpKeyReleased = false;
			}
		} else {
			jumpKeyReleased = true;
		}
		if(keyManager.getKey_Down()) {
			
		}
		if(keyManager.getKey_Left()) {
			vx -= 2;
		}
		if(keyManager.getKey_Right()) {
			vx += 2;
		}
		
		horizontalFric();
		
		adjustVel();
		
		x += (int)vx;
		y += (int)vy;
	}
	
	public void draw(Graphics g) {
		int fWidth = display.getFrameWidth();
		int fHeight = display.getFrameHeight();
		
		int drawPosX = fWidth / 2 - (this.width / 2);
		int drawPosY = fHeight / 2 - (this.height / 2) + fHeight / 6;
		
		// Drawing collision box
		g.setColor(Color.RED);
		g.drawRect(drawPosX + hitboxXOffset, drawPosY, getHitboxWidth(), height);
		
		// Drawing character
		animate(g, drawPosX, drawPosY);
	}
	
	private CollisionData[] getCollisionData() {
		int[] collisionIndexes = getCollisionIndexRange();
		int topYIndex = collisionIndexes[0];
		int bottomYIndex = collisionIndexes[1];
		int lowXIndex = collisionIndexes[2];
		int highXIndex = collisionIndexes[3];
		
		return worldManager.getCollisionData(topYIndex, bottomYIndex, lowXIndex, highXIndex, getHitboxX(), y, getHitboxWidth(), height, (int)vx, (int)(vy + 0.5));
	}
	
	private void animate(Graphics g, int drawPosX, int drawPosY) {
		updateState();
		
		if(facingRight) {
			animRight(g, drawPosX, drawPosY);
		} else {
			animLeft(g, drawPosX, drawPosY);
		}
	}
	
	private void animRight(Graphics g, int drawPosX, int drawPosY) {
		if(state == "STILL") {
			if(onGround) {
				g.drawImage(defaultFrameState, drawPosX, drawPosY, null);
			} else {
				g.drawImage(jumpStill, drawPosX, drawPosY, null);
			}
		} else if(state == "MOVING") {
			if(onGround) {
				if(sprinting) {
					animArray(g, drawPosX, drawPosY, sprintAnim);
				} else {
					animArray(g, drawPosX, drawPosY, walkAnim);
				}
			} else {
				g.drawImage(jumpMove, drawPosX, drawPosY, null);
			}
		}
	}
	
	private void animLeft(Graphics g, int drawPosX, int drawPosY) {
		if(state == "STILL") {
			if(onGround) {
				g.drawImage(reversedDefaultFS, drawPosX, drawPosY, null);
			} else {
				g.drawImage(jumpStillL, drawPosX, drawPosY, null);
			}
		} else if(state == "MOVING") {
			if(onGround) {
				if(sprinting) {
					animArray(g, drawPosX, drawPosY, sprintAnimL);
				} else {
					animArray(g, drawPosX, drawPosY, walkAnimL);
				}
			} else {
				g.drawImage(jumpMoveL, drawPosX, drawPosY, null);
			}
		}
	}
	
	private void animArray(Graphics g, int drawPosX, int drawPosY, BufferedImage[] array) {
		g.drawImage(array[animIndex], drawPosX, drawPosY, null);
		advanceAnim(array);
	}
	
	private void advanceAnim(BufferedImage[] array) {
		animCount += 1;
		if(animCount > (array.length * animDelay) - 1) {
			animCount = 0;
		}
		animIndex = animCount / animDelay;
	}
	
	private void updateState() {
		if(vx > 0) {
			facingRight = true;
			setAnimWalking();
		} else if(vx < 0) {
			facingRight = false;
			setAnimWalking();
		} else {
			setAnimStill();
		}
	}
	
	private void setAnimWalking() {
		if(state != "MOVING") {
			state = "MOVING";
			animCount = 0;
		}
	}
	
	private void setAnimStill() {
		if(state != "STILL") {
			state = "STILL";
			animCount = 0;
		}
	}
	
	private void adjustVel() {
		if(sprinting) {
			sprintAdjustVel();
		} else {
			walkAdjustVel();
		}
		
		
		// IMPLEMENTING GRAVITY
		vy += 0.5;
		
		if(vy > 8) {
			vy = 8;
		} else if(vy < -10) {
			vy = -10;
		}
		
		// RESETING 'ON GROUND' DETECTION
		onGround = false;
		
		// DETECTING FALL
		boolean falling;
		if(vy > 0) {
			falling = true;
		} else {
			falling = false;
		}
		
		// WORLD COLLISION DETECTION
		CollisionData[] mixedData = getCollisionData();
		CollisionData xData = mixedData[0];
		CollisionData yData = mixedData[1];
		
		if(xData.getDetection()) {
			vx = xData.getDistance();
		}
		if(yData.getDetection()) {
			vy = yData.getDistance();
			// DETECTING IF GROUND SURFACE HIT
			if(falling) {
				onGround = true;
			}
		}
	}
	
	private int getHitboxWidth() {
		return width - (hitboxXOffset * 2);
	}
	
	private int getHitboxX() {
		return x + hitboxXOffset;
	}
	
	private void walkAdjustVel() {
		if(vx > 2) {
			vx = 2;
		} else if(vx < -2) {
			vx = -2;
		}
	}
	
	
	private void sprintAdjustVel() {
		if(vx > 4) {
			vx = 4;
		} else if(vx < -4) {
			vx = -4;
		}
	}
	
	private void horizontalFric() {
		if(onGround) {
			if(vx > 0) {
				vx -= 1;
			} else if(vx < 0) {
				vx += 1;
			}
		}
	}
	
	private static BufferedImage createTransformed(BufferedImage image, AffineTransform at) {
	        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = newImage.createGraphics();
	        g.transform(at);
	        g.drawImage(image, 0, 0, null);
	        g.dispose();
	        return newImage;
	}
	
	private static BufferedImage createFlipped(BufferedImage image) {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(), 0));
        return createTransformed(image, at);
    }
	
	// RETRIEVING THE LEFT SCREEN OBJECT COLUMN INDEX
	public int getLeftSOCIndex() {
		int fWidth = display.getFrameWidth();
		int amountSOC = (fWidth / 50) + 1;
		int playerOC = x / 50;
		
		return playerOC - ((amountSOC / 2) + 1);
	}
	
	// RETRIEVING THE RIGHT SCREEN OBJECT COLUMN INDEX
	public int getRightSOCIndex() {
		int fWidth = display.getFrameWidth();
		int amountSOC = (fWidth / 50) + 1;
		int playerOC = x / 50;
		
		return playerOC + ((amountSOC / 2) + 1);
	}
	
	public int[] getCollisionIndexRange() {
		int topYIndex = y / 50 - 1;
		int bottomYIndex = (y + height) / 50 + 1;
		
		int lowXIndex = x / 50 - 1;
		int highXIndex = (x + width) / 50 + 1;
		
		if(vy < 0) {
			topYIndex -= (vy / 50);
		} else {
			bottomYIndex += (vy / 50);
		}
		
		if(vx < 0) {
			lowXIndex -= (Math.abs(vx) / 50);
		} else {
			highXIndex += (Math.abs(vx) / 50);
		}
		
		int[] result = {topYIndex, bottomYIndex, lowXIndex, highXIndex};
		
		return result;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getXAdj() {
		return (x * -1) + ((display.getFrameWidth() - this.width) / 2);
	}
	
	public int getYAdj() {
		int fHeight = display.getFrameHeight();
		return (y * -1) + ((fHeight - this.height) / 2) + fHeight / 6;
	}

}
