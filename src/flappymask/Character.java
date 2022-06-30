package flappymask;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Character extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	// CHARACTER ATTRIBUTES
	public String name;
	private int x, y;
	private boolean isAlive = true;
	
	// CHARACTER CONSTANTS
	private int FLOAT_MULTIPLIER		= -1;
	public final int CHARA_WIDTH		= 44;
	public final int CHARA_HEIGHT 		= 31;
	private final int BASE_COLLISION	= 521 - CHARA_HEIGHT - 5;
	private final int SHIFT				= 10;
	private final int STARTING_CHARA_X	= 90;
	private final int STARTING_CHARA_Y	= 343;
	
	// PHYSICS VARIABLES (MOVEMENTS)
	private double velocity				= 0;
	private double gravity				= 0.41;
	private double delay				= 0;
	private double rotation				= 0;
	
	// CHARACTER ANIMATION ARRAY
	private BufferedImage[] characterArray;
	
	// CONSTRUCTOR
	public Character(String name, int x, int y, BufferedImage[] characterImages) {
		this.name 			= name;
		this.x 				= x;
		this.y 				= y;
		this.characterArray = characterImages;
	}
	
//--------------------- METHODS ---------------------------------------------------------------//
	
	// GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, 44, 32);
	}
	
	// BOOLEAN
	public boolean isAlive() {
		return isAlive;
	}
	
	// SETTERS
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setIsAlive(boolean status) {
		this.isAlive = status;
	}
	
					// CHARACTER UTILITIES
	
	// KILLS CHARCTER
	public void kill() {
		isAlive = false;
	}
	
	// SET RESPAWN COORDINATE
	public void setGameStartPos() {
		x = STARTING_CHARA_X;
		y = STARTING_CHARA_Y;
		rotation = 0;
	}
	
	// FLOATING CHARACTER EFFECT ON MENU SCREEN
	public void menuFloat() {
		y += FLOAT_MULTIPLIER;
		
		// CHANGE DIRECTION WITHIN FLOATING RANGE
		if(y < 220) {
			FLOAT_MULTIPLIER *= -1;
		}
		else if(y > 280) {
			FLOAT_MULTIPLIER *= -1;
		}
	}
	
	// CHARACTER JUMP
	public void jump() {
		if(delay < 1) {
			velocity = -SHIFT;
			delay = SHIFT;
		}
	}
	
	// CHARACTER MOVEMENT DURING THE GAME
	public void inGame() {
		
		// IF THE CHARACTER DIDN'T HIT THE BASE, LOWER IT BY APPLYING GRAVITY
		if(y < BASE_COLLISION) {
			velocity += gravity;
			
			// LOWER DELAY IF POSSIBLE
			if(delay > 0) {
				delay--;
			}
			
			// ADD ROUNDED VELOCITY TO CHARACTER'S Y COORDINATE
			y += (int) velocity; 
		}
		else {
			// PLAY AUDIO
			//GamePanel.audio.hit
			
			// KILL THE CHARACTER
			kill();
		}
	}
	
	// RENDERS CHARACTER
	public void renderCharacter(Graphics g, double speed) {
		
		// CALCULATE ANGLE TO ROTATE BASED ON Y-VELOCITY
		rotation = ((90 * (velocity + 25) / 25) - 90) * Math.PI / 180;
		
		// DIVIDE FOR CLEAN JUMP
		rotation /= 2;
		
		// HANDLE ROTATION OFFSET
		rotation = rotation > Math.PI / 2 ? Math.PI / 2 : rotation;
		
		// ANIMATION WHEN CHARACTER DIES
		if(!isAlive()) {
			// DROP
			if(y < BASE_COLLISION - 10) {
				velocity += gravity;
				y += (int) velocity;
			}
		}
		
		// APPLY CHARACTER ANIMATION
		Animation.animate(g, characterArray, x, y, speed, rotation);
		
	}
	
	
}
