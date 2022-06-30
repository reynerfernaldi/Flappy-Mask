package flappymask;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class GameObject {

	//OBJECT ATTRIBUTES
	private BufferedImage objectImage;
	private int x, y, width, height;
	private Rectangle rectangle;
	
	//CONSTRUCTOR
	public GameObject(BufferedImage objectImage, int x, int y) {
		this.objectImage = objectImage;
		this.x = x;
		this.y = y;
		this.width = objectImage.getWidth();
		this.height = objectImage.getHeight();
		this.rectangle = new Rectangle(x, y, width, height);
	}
	
//------------------------ GETTERS METHOD ------------------------------------------------//
	
	public BufferedImage getObjectImage() {
		return objectImage;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Rectangle getRect() {
		return rectangle;
	}
}
