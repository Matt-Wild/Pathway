package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Background {
	
	private BufferedImage backgroundImage, backgroundSky, originalImage, originalImageSky,
	                      backgroundImageForward, originalImageForward, backgroundImageBack,
						  originalImageBack;
	private ImageLoader imageLoader;
	private int img_width = 1920, img_height = 1080;
	
	private Color backgroundColorSky = new Color(20, 26, 56);
	
	public Background(ImageLoader imageLoader) {
		this.imageLoader = imageLoader;
		
		backgroundImage = this.imageLoader.loadImage("/textures/backgroundScape.png");
		originalImage = backgroundImage;
		
		backgroundSky = this.imageLoader.loadImage("/textures/backgroundSky.png");
		originalImageSky = backgroundSky;
		
		backgroundImageForward = this.imageLoader.loadImage("/textures/backgroundForward.png");
		originalImageForward = backgroundImageForward;
		
		backgroundImageBack = this.imageLoader.loadImage("/textures/backgroundBack.png");
		originalImageBack = backgroundImageBack;
	}
	
	public void update(int height) {
		if(img_height != height) {
			backgroundImage = this.resize(height, originalImage, true);
			backgroundSky = this.resize(height, originalImageSky, false);
			backgroundImageForward = this.resize(height, originalImageForward, false);
			backgroundImageBack = this.resize(height, originalImageBack, false);
		}
	}
	
	public void draw(Graphics g, Player player, int width) {
		drawSky(g, player, width);
		drawScapeBack(g, player, width);
		drawScape(g, player, width);
		drawScapeForward(g, player, width);
	}
	
	private void drawScape(Graphics g, Player player, int width) {
		int px = player.getX();
		int py = player.getY();
		
		int start_point = -(px / 16) - (-(px / 16) / img_width) * img_width - img_width;
		int y_adj = (700 - py) / 16;
		
		for(int i = start_point; i < width; i += img_width) {
			if(i > -img_width) {
				g.drawImage(backgroundImage, i, y_adj, null);
			}
		}
	}
	
	private void drawScapeForward(Graphics g, Player player, int width) {
		int px = player.getX();
		int py = player.getY();
		
		int start_point = -(px / 8) - (-(px / 8) / img_width) * img_width - img_width;
		int y_adj = (700 - py) / 8;

		for(int i = start_point; i < width; i += img_width) {
			if(i > -img_width) {
				g.drawImage(backgroundImageForward, i, y_adj, null);
			}
		}
	}
	
	private void drawScapeBack(Graphics g, Player player, int width) {
		int px = player.getX();
		int py = player.getY();
		
		int start_point = -(px / 32) - (-(px / 32) / img_width) * img_width - img_width;
		int y_adj = (700 - py) / 32;

		for(int i = start_point; i < width; i += img_width) {
			if(i > -img_width) {
				g.drawImage(backgroundImageBack, i, y_adj, null);
			}
		}
	}
	
	private void drawSky(Graphics g, Player player, int width) {
		int px = player.getX();
		int py = player.getY();
		
		int start_point = -(px / 64) - (-(px / 64) / img_width) * img_width - img_width;
		int y_adj = (700 - py) / 64;

		for(int i = start_point; i < width; i += img_width) {
			if(i > -img_width) {
				g.drawImage(backgroundSky, i, y_adj, null);
				if(y_adj > 0) {
					g.setColor(backgroundColorSky);
					g.fillRect(i, 0, img_width, y_adj);
				} else if(y_adj < 0) {
					g.setColor(Color.BLACK);
					g.fillRect(i, img_height + y_adj, img_width, -y_adj);
				}
			}
		}
	}
	
	private BufferedImage resize(int newHeight, BufferedImage originalImage, boolean updateDim) {
		int w = originalImage.getWidth();
		int h = originalImage.getHeight();
		
		float dh;
		dh = (float)newHeight / h;
		
		BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(dh, dh);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		after = scaleOp.filter(originalImage, after);
		
		if(updateDim) {
			img_width = (int)(after.getWidth() * dh);
			img_height = newHeight;
		}
		
		return after;
	}

}
