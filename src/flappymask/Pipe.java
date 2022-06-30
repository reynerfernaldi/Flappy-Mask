package flappymask;

public class Pipe {
	
	// PIPE ATTRIBUTES
	
	private int x = FlappyMaskMain.WIDTH + 5;
	private int y;
	
	// NAME OF THE PIPE (TOP OR BOTTOM)
	String pipeType;
	
	// PIPE CONSTANTS
	public static final int WIDTH			= 67;
	public static final int HEIGHT			= 416;
	public static final int PIPE_DISTANCE	= 150;			// HORIZONTAL DISTANCE
	public static final int PIPE_GAP		= HEIGHT + 170;	// VERTICAL GAP
	private static final int SPEED			= -2;
	
	// IF THE CHARACTER PASS THE PIPE, GET A POINT
	public boolean isPassThePipe = true;
	
	//CONSTRUCTOR
	public Pipe(String type) {
		this.pipeType = type;
		reset();
	}
	
//------------------------ METHODS (GETTERS, SETTERS, ETC) --------------------------------------------------------//
	
	// GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	// SETTER
	public void setY(int newY) {
		this.y = newY;
	}
	
	// MOVE METHOD
	public void move() {
		x += SPEED;
	}
	
	// COLLISION DETECTOR
	/*
	 	cX, cY -> Character's coordinate x and y
	 	cW, cH -> Character's Weight and Height
	 */
	public boolean collide(int cX, int cY, int cW, int cH) {
		
		// PREVENT CHARACTER FROM JUMPING OVER THE PIPE (COLLIDE)
		if(cX > x && cY < 0 && isPassThePipe) {
			return true;
		}
		else if(cX < x + WIDTH && (cX + cW) > x && cY < (y + HEIGHT) && (cY + cH) > y) {
			return true;
		}
		return false;
	}
	
	// RESET PIPE POSITION
	public void reset() {
		x = FlappyMaskMain.WIDTH + 5;
		
		// SET BOUNDARIES FOR TOP PIPES
		// THIS Y COORDINATE + PIPE SPACING WILL BE THE Y OF BOTTOM PIPE
		if(pipeType.equals("top")) {
			y = - Math.max((int)(Math.random() * 320) + 30, 140);
		}
	}
	
}
