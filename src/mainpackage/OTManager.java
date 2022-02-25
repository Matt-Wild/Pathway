package mainpackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

// OBJECT TEXTURE MANAGER
public class OTManager {
	
	private ImageLoader imageLoader;
	private ArrayList<OTRequest> drawRequests = new ArrayList<OTRequest>();
	
	private BufferedImage bedrock, road;
	
	public OTManager(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		
		bedrock = this.imageLoader.loadImage("/textures/bedrock.png");
		road = this.imageLoader.loadImage("/textures/road.png");
	}
	
	public void draw(Graphics g, Player player) {
		int xAdj = player.getXAdj();
		int yAdj = player.getYAdj();
		
		for (int i = 0; i < drawRequests.size(); i++) {
			OTRequest currentRequest = drawRequests.get(0);
			
			if(currentRequest.getID() == "BEDROCK") {
				g.drawImage(bedrock, currentRequest.getX() + xAdj, currentRequest.getY() + yAdj, null);
			} else if(currentRequest.getID() == "ROAD") {
				g.drawImage(road, currentRequest.getX() + xAdj, currentRequest.getY() + yAdj, null);
			}
			
			drawRequests.remove(0);
		}
	}
	
	public void request(int x, int y, String ID) {
		drawRequests.add(new OTRequest(x, y, ID));
	}

}
