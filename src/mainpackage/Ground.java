package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Ground {
	
	private BufferedImage groundImage;
	private ImageLoader imageLoader;
	
	public Ground(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		
		groundImage = this.imageLoader.loadImage("/textures/ground.png");
	}
	
	public void draw(Graphics g, Player player, int width, int height) {
		int x = player.getX();
		int xAdj = player.getXAdj();
		int yAdj = player.getYAdj();
		
		int realY = 800 + yAdj;
		
		for(int i = x - width; i < x + width; i++) {
			if(i % 50 == 0) {
				g.drawImage(groundImage, i + xAdj, realY, null);
				g.setColor(Color.BLACK);
				g.fillRect(0, realY + 150, width, height - realY - 150);
				
				i += 49;
			}
		}
	}

}
