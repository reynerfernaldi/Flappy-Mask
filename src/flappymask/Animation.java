package flappymask;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Animation {
	
	private static double currentFrame = 0;
	
	// WE CREATE ANIMATION USING ARRAY OF PARTICUAL OBJECT IN COMPONENTS
	// ANIMATE METHOD
	
	public static void animate(Graphics g, BufferedImage[] objectArray, int x, int y, double speed, double angle) {
		
		Graphics2D g2d 				= (Graphics2D) g; //TYPE CAST
		AffineTransform transform 	= g2d.getTransform();
		AffineTransform at			= new AffineTransform();
		
		// NUMBER OF FRAMES IN AN OBJECT
		int count = objectArray.length;
		
		// ROTATE IMAGE OBJECT (TILT EFFECT)
		at.rotate(angle, x + 25, y + 25);
		g2d.transform(at);
		
		// DRAW THE CURRENT ROTATED IMAGE OBJECT
		g2d.drawImage(objectArray[(int) (Math.round(currentFrame))], x, y, null);
		
		g2d.setTransform(transform);
		
		// SWICTH ANIMATION FRAMES
		if(currentFrame >= count - 1) {
			currentFrame = 0;
		}
		else {
			currentFrame += speed;
		}
		
	}
	
}
