package mainpackage;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	private static BufferedImage defaultImage;
	
	public BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(ImageLoader.class.getResource(path));
		} catch(NullPointerException e) {
			try {
				return ImageIO.read(ImageLoader.class.getResource("/materials" + path));
			}catch (IOException ex) {
				System.out.println("========================================================================"); 
				System.out.println("Unexpected IOException Error ¬_¬... Sowwy D:");  
				e.printStackTrace();
				System.out.println("========================================================================"); 
				return defaultImage;
			} catch (IllegalArgumentException ex) {
				System.out.println("========================================================================"); 
				System.out.println("Could not find the image file: " + path + "... Sowwy D:"); 
				e.printStackTrace();
				System.out.println("========================================================================"); 
				return defaultImage;
			}
		} catch (IOException e) {
			System.out.println("========================================================================"); 
			System.out.println("Unexpected IOException Error ¬_¬... Sowwy D:");  
			e.printStackTrace();
			System.out.println("========================================================================"); 
			return defaultImage;
		} catch (IllegalArgumentException e) {
			try {
				return ImageIO.read(ImageLoader.class.getResource("/materials" + path));
			}catch (IOException ex) {
				System.out.println("========================================================================"); 
				System.out.println("Unexpected IOException Error ¬_¬... Sowwy D:");  
				e.printStackTrace();
				System.out.println("========================================================================"); 
				return defaultImage;
			} catch (IllegalArgumentException ex) {
				System.out.println("========================================================================"); 
				System.out.println("Could not find the image file: " + path + "... Sowwy D:"); 
				e.printStackTrace();
				System.out.println("========================================================================"); 
				return defaultImage;
			}
		}
	}
	
	public void init() {
		defaultImage = loadImage("/textures/default.png");
	}

}
