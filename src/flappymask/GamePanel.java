package flappymask;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener, MouseListener{

	private Calendar calendar;
	
							// GAME VARIABLES //
	
	// FONTS 
	private Font baseFont, realFont, scoreFont, miniFont = null;
	
	// COMPONENTS (TEXUTURES)
	public static HashMap<String, GameObject> components = new Components().getComponents();
	
	// MOVING BASE EFFECT
	private static int baseSpeed	= 2;
	private static int[] baseCoords	= {0, 435};
	
	// GAME STATES
	final static int MENU	= 0;
	final static int GAME	= 1;
	private int gameState = MENU;
	
	private int score;					// PLAYER SCORE
	private int pipeDistTracker;		// DISTANCE BETWEEN PIPE
	
	public boolean ready			 = false;				// TRUE -> IF THE GAME HAS BEEN LOADED
	private boolean inStartGameState = false;				// TRUE -> DISPLAY INSTRUCTION SCREEN
	private Point clickedPoint		 = new Point(-1, -1);	// STORE POINT WHEN PLAYER CLICKS
	private boolean scoreWasGreater;						// TRUE -> IF WE HAVE A NEW HIGHEST SCORE
	private boolean darkTheme;								// CHANGE BACKGROUND THEME
	private boolean charSelect		 = false;				// TRUE -> CHARACTER BUTTON IS CLICKED
	private String medal;									// MEDAL TO BE AWARDED AFTER EACH GAME
	public ArrayList<Pipe> pipes;							// ARRAYLIST OF PIPE OBJECTS
	
	// CHARACTERS
	private Character gameCharacter1;
	private Character gameCharacter2;
	private Character gameCharacter3;
	private Character currentCharacter;						// CURRENT CHARACTER
	
	// CHARACTERS IN CHARACTER PANE
	private Character paneCharacter1;
	private Character paneCharacter2;
	private Character paneCharacter3;
	private int checkBox = 1;
	
	
	private HighScore highScore = new HighScore();
	public static Audio audio = new Audio();
	
	
							// CONSTRUCTOR //
	
	public GamePanel() {
		
		// LOAD FONT FILE (TTF)
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream("res/fonts/flappy-font.ttf"));
			baseFont 				= Font.createFont(Font.TRUETYPE_FONT, inputStream);
			
			// OTHER FONTS DERIVED FROM BASE FONT
			scoreFont	= baseFont.deriveFont(Font.PLAIN, 50);
			realFont 	= baseFont.deriveFont(Font.PLAIN, 20);
			miniFont	= baseFont.deriveFont(Font.PLAIN, 15);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not load the Font");
			System.exit(-1);
		}
		
		restart(); // RESET GAME VARIABLES
		
		// INPUT LISTENERS
		this.addKeyListener(this);
		this.addMouseListener(this);
		
	}
	
//------------------------------------ METHODS / HOW THE GAME WORKS ------------------------------------------------------------------//


	//---------------------- GENRERAL FUNCTIONALITIES --------------------
	
	// TO START THE GAME AFTER EVERYTHING HAS BEEN LOADED
	public void addNotify() {
		super.addNotify();
		this.requestFocus();
		this.ready = true;
	}
	
	// RESTART GAME BY RESETTING GAME VARIABLES
	public void restart() {
		
		// GAME STATS
		score			= 0;
		pipeDistTracker	= 0;
		scoreWasGreater	= false;
		
		// GET CURRENT HOUR WITH CALENDAR
		// IF IT'S PAST NOON, USE DARK THEME
		calendar = Calendar.getInstance();
		int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
		
		
		// SET RANDOM SCENE ASSESTS
		darkTheme = currentHour > 12; 		// TRUE -> DARK THEME WILL BE USED
		
		
		// LOAD GAME CHARACTER
		loadCharacters();
		currentCharacter = gameCharacter1;
		
		// REMOVE OLD PIPES
		pipes = new ArrayList<Pipe>();
		
	}
	
	// CHECK IF POINT IS IN THE RECTANGLE
		// IF POINT COLLIDES WITH RECTANGLE, RETURN TRUE
	private boolean isTouching(Rectangle r) {
		return r.contains(clickedPoint);
	}
	
	// SET CHARACTER (WHEN A CHARACTER IS SELECTED IN CHARACTER PANE)
	private void selectCharacter(Character newCharacter) {
		
		int currX = currentCharacter.getX();
		int currY = currentCharacter.getY();
		currentCharacter = newCharacter;
		currentCharacter.setX(currX);
		currentCharacter.setY(currY);
		repaint();
		
	}
	
	// LOAD ALL CHARACTERS
	private void loadCharacters() {
		
		// INSTANTIATE GAME CHARACTERS
		gameCharacter1 = new Character("c1", 172, 250, new BufferedImage[] {
				components.get("c1" + "move1").getObjectImage(),
				components.get("c1" + "move2").getObjectImage(),
				components.get("c1" + "move3").getObjectImage()
			});
		paneCharacter1 = new Character("c1", 172, 250, new BufferedImage[] {
				components.get("c1" + "move1").getObjectImage(),
				components.get("c1" + "move2").getObjectImage(),
				components.get("c1" + "move3").getObjectImage()
			});
		
		gameCharacter2 = new Character("c2", 172, 250, new BufferedImage[] {
				components.get("c2" + "move1").getObjectImage(),
				components.get("c2" + "move2").getObjectImage(),
				components.get("c2" + "move3").getObjectImage()
			});
		paneCharacter2 = new Character("c2", 172, 250, new BufferedImage[] {
				components.get("c2" + "move1").getObjectImage(),
				components.get("c2" + "move2").getObjectImage(),
				components.get("c2" + "move3").getObjectImage()
			});
		
		gameCharacter3 = new Character("c3", 172, 250, new BufferedImage[] {
				components.get("c3" + "move1").getObjectImage(),
				components.get("c3" + "move2").getObjectImage(),
				components.get("c3" + "move3").getObjectImage()
			});
		paneCharacter3 = new Character("c3", 172, 250, new BufferedImage[] {
				components.get("c3" + "move1").getObjectImage(),
				components.get("c3" + "move2").getObjectImage(),
				components.get("c3" + "move3").getObjectImage()
			});	
		
		// SET PANE CHARACTERS
		paneCharacter1.setX(172);
		paneCharacter1.setY(440);
		
		paneCharacter2.setX(102);
		paneCharacter2.setY(440);
		
		paneCharacter3.setX(242);
		paneCharacter3.setY(440);
		
	}
	
	//---------------------------------------------------------------------------------
	//------------------------- PAINT COMPONENT METHOD -------------------------------
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// SET FONT AND COLOR
		g.setFont(realFont);
		g.setColor(Color.black);
		
		// MOVE THE SCREEN WHEN CHARACTER IS ALIVE
		if(currentCharacter.isAlive()) {
			// MOVE THE BASE
			baseCoords[0] = baseCoords[0] - baseSpeed < -435 ? 435 : baseCoords[0] - baseSpeed;
			baseCoords[1] = baseCoords[1] - baseSpeed < -435 ? 435 : baseCoords[1] - baseSpeed;
		}
		
		// BACKGROUND
		g.drawImage(darkTheme ? components.get("background2").getObjectImage()
				: components.get("background1").getObjectImage(), 0, 0, null);
		
		// DRAW CHARACTER
		currentCharacter.renderCharacter(g, 0.09);
		
		// SWITCH GAME CONDITION BETWEEN MENU CONDITION AND GAME CONDITION
		switch (gameState) {
		case MENU: 
			
			drawBase(g);						// SEE 'ALL DRAWING METHODS' FOR EXPLANATION
			drawMenu(g);						// SEE 'ALL DRAWING METHODS' FOR EXPLANATION
			currentCharacter.menuFloat();
			
			// CHARACTER PANE SELECTED
			if(charSelect) {
				
				GameObject checkIcon = components.get("checkIcon");
				drawCharacterPane(g);
				if(checkBox == 1)
					g.drawImage(checkIcon.getObjectImage(), paneCharacter1.getX() + 27, paneCharacter1.getY() + 20, null);
				else if(checkBox == 2)
					g.drawImage(checkIcon.getObjectImage(), paneCharacter2.getX() + 27, paneCharacter2.getY() + 20, null);
				else if(checkBox == 3)
					g.drawImage(checkIcon.getObjectImage(), paneCharacter3.getX() + 27, paneCharacter3.getY() + 20, null);
				
			}
			break;
			
		case GAME:
			
			if(currentCharacter.isAlive()) {
				
				// START AT INSTRUCTIONS STATE
				if(inStartGameState) {
					startGameScreen(g);			// SEE 'ALL DRAWING METHODS' FOR EXPLANATION
				}
				else {
					// START GAME
					pipeHandler(g);				// SEE 'ALL DRAWING METHODS' FOR EXPLANATION
					currentCharacter.inGame();
				}
				
				drawBase(g);						// DRAW BASE OVER THE PIPE
				drawScore(g, score, false, 0, 0);	// DRAW THE SCORE
				
			}
			else {
				pipeHandler(g);
				drawBase(g);
				
				// DRAW GAME OVER OBJECTS
				gameOver(g);					// SEE 'ALL DRAWING METHODS' FOR EXPLANATION
			}
			
			break;
		}
		
	}
	
	//---------------------------------------------------------------------------------------
	//---------------------------------------- ALL DRAWING METHODS --------------------------
	
	// DRAW CENTERED
		/*
		 Method to draw string in the CENTERED X ALLIGNMENT
		 - txt	-> the text
		 - w	-> width 
		 - h	-> height
		 - y	-> fixed y position
		 */
	public void drawCentered(String txt, int w, int h, int y, Graphics g) {
		FontMetrics fm = g.getFontMetrics();

		// Calculate x-coordinate based on string length and width
		int x = (w - fm.stringWidth(txt)) / 2;
		g.drawString(txt, x, y);
	}
	
	// DRAW BASE
	public void drawBase(Graphics g) {
		
		// MOVING BASE EFFECT
		g.drawImage(components.get("base").getObjectImage(), baseCoords[0], components.get("base").getY(), null);
		g.drawImage(components.get("base").getObjectImage(), baseCoords[1], components.get("base").getY(), null);
		
	}
	
	// DRAW MENU
	private void drawMenu(Graphics g) {
		
		// TITLE
		g.drawImage(components.get("titleText").getObjectImage(), 
					components.get("titleText").getX(),
					components.get("titleText").getY(), null);
		
		// BUTTONS
		g.drawImage(components.get("playButton").getObjectImage(),
					components.get("playButton").getX(),
					components.get("playButton").getY(), null);
		g.drawImage(components.get("characterButton").getObjectImage(),
					components.get("characterButton").getX(),
					components.get("characterButton").getY(), null);
		
		// CREATOR
		drawCentered("Created by RAY GROUP", FlappyMaskMain.WIDTH, FlappyMaskMain.WIDTH, 600, g);
		g.setFont(miniFont);
		drawCentered("5025201235 & 5025201094", FlappyMaskMain.WIDTH, FlappyMaskMain.WIDTH, 630, g);
		
	}
	
	// DRAW CHARACTER PANE
	private void drawCharacterPane(Graphics g) {
		
		//PANE
		g.drawImage(components.get("characterPane").getObjectImage(),
					components.get("characterPane").getX(),
					components.get("characterPane").getY(), null);
		
		//EXIT BUTTON
		g.drawImage(components.get("exitButton").getObjectImage(),
					components.get("exitButton").getX(),
					components.get("exitButton").getY(), null);
		
		// CHARACTER 1, 2, 3
		paneCharacter1.renderCharacter(g, 0);
		paneCharacter2.renderCharacter(g, 0);
		paneCharacter3.renderCharacter(g, 0);
		
	}
	
	// START GAME SCREEN
	public void startGameScreen (Graphics g) {
		
		// SET CHARACTER'S NEW POSITION
		currentCharacter.setGameStartPos();
		
		// GET READY TEXT (BEFORE THE FIRST JUMP)
		g.drawImage(components.get("getReadyText").getObjectImage(), 
				components.get("getReadyText").getX(),
				components.get("getReadyText").getY(), null);
		
		// INSTRUCTIONS IMAGE
		g.drawImage(components.get("instruction").getObjectImage(), 
				components.get("instruction").getX(),
				components.get("instruction").getY(), null);
		
	}
	
	// DRAW SCORE
		/*
		 	mini	-> to draw large or small font
		 	x		-> X-coordinate to draw the numbers
		 */
	public void drawScore(Graphics g, int drawNum, boolean mini, int x, int y) {
		
		// CHAR ARRAY OF DIGITS
		char[] digits = ("" + drawNum).toCharArray();
		
		int digitCount = digits.length;
		
		// CALCULATE WIDTH FOR NUMERIC TEXTURE
		int takeUp = 0;
		for(char digit : digits) {
			// SIZE TO ADD VARIES BASED ON COMPONENTS/TEXTURES
			if(mini) {
				takeUp += 18;
			}
			else {
				takeUp += digit == '1' ? 25 : 35;
			}
		}
		
		//CALCULATE THE X-COORDINATE
		int drawScoreX = mini ? (x - takeUp) : (FlappyMaskMain.WIDTH / 2 - takeUp / 2);
		
		// DRAW EVERY DIGIT
		for(int i = 0; i < digitCount; i++) {
			g.drawImage(components.get((mini ? "mini-score-" : "score-") + digits[i]).getObjectImage(),
						drawScoreX, (mini ? y : 60), null);
			
			// SIZE TO ADD VARIES BASED ON COMPONENTS/TEXTURES
			if(mini) {
				drawScoreX += 18;
			}
			else {
				drawScoreX += digits[i] == '1' ? 25 : 35;
			}
		}
		
	}
	
	// PIPE HANDLER
		// MOVES AND REPOSITIONS PIPES
	public void pipeHandler(Graphics g) {
		
		// DECREASE DISTANCE BETWEEN PIPES
		if(currentCharacter.isAlive()) {
			pipeDistTracker--;
		}
		
		// INITIALIZE PIPES AS NULL
		Pipe topPipe 	= null;
		Pipe bottomPipe = null;
		
		// IF THERE IS NO DISTANCE, A NEW PIPE IS NEEDED
		if(pipeDistTracker < 0) {
			// RESET DISTANCE
			pipeDistTracker = Pipe.PIPE_DISTANCE;
			
			for(Pipe pipe : pipes) {
				
				// IF PIPE IS OUT OF SCREEN
				if(pipe.getX() < 0) {
					if(topPipe == null) {
						topPipe = pipe;
						topPipe.isPassThePipe = true;
					}
					else if(bottomPipe == null) {
						bottomPipe = pipe;
						topPipe.isPassThePipe = true;
					}
				}
			}
			
			Pipe currentPipe;	// NEW PIPE OBJECT FOR TOP AND BOTTOM PIPES
			
			// MOVE AND HANDLE INITIAL CREATION OF TOP AND BOTTOM PIPES
			
			if(topPipe == null) {
				currentPipe = new Pipe("top");
				topPipe		= currentPipe;
				pipes.add(topPipe);
			}
			else {
				topPipe.reset();
			}
			
			if(bottomPipe == null) {
				currentPipe = new Pipe("bottom");
				bottomPipe  = currentPipe;
				pipes.add(bottomPipe);
				
				// AVOID DOUBLING POINTS WHEN PASSING INITIAL PIPES
				bottomPipe.isPassThePipe = false;
			}
			else {
				bottomPipe.reset();
			}
			
			// SET Y-COORDINATE OF BOTTOM PIPE BASED ON Y-COORDINATE OF TOP PIPE
			bottomPipe.setY(topPipe.getY() + Pipe.PIPE_GAP);
			
		}
		
		// MOVE AND DRAW EACH PIPE
		for(Pipe pipe : pipes) {
			
			// MOVE THE PIPE
			if(currentCharacter.isAlive()) {
				pipe.move();
			}
			
			// DRAW THE TOP AND BOTTOM PIPES
			if(pipe.getY() <= 0) {
				g.drawImage(components.get("pipe-bottom").getObjectImage(),
							pipe.getX(), pipe.getY(), null);
			}
			else {
				g.drawImage(components.get("pipe-top").getObjectImage(),
						pipe.getX(), pipe.getY(), null);
			}
			
			// CHECK IF CHARACTER HITS PIPES
			if(currentCharacter.isAlive()) {
				if(pipe.collide(currentCharacter.getX(), currentCharacter.getY(), currentCharacter.CHARA_WIDTH, currentCharacter.CHARA_HEIGHT)) {
					
					// KILL CHARACTER AND PLAY SOUND
					currentCharacter.kill();
					audio.hit();
				}
				else {
					
					// CHECKS IF BIRD PASSES A PIPE
					if(currentCharacter.getX() >= pipe.getX() + pipe.WIDTH / 2) {
						
						// INCREASE SCORE AND PLAY SOUND
						if(pipe.isPassThePipe) {
							audio.point();
							score++;
							pipe.isPassThePipe = false;
						}
					}
				}
			}
		}
	}
	
	// GAME OVER
	public void gameOver(Graphics g) {
		
		// GAME OVER TEXT
		g.drawImage(components.get("gameOverText").getObjectImage(),
					components.get("gameOverText").getX(),
					components.get("gameOverText").getY(), null);
		
		// SCORECARD
		g.drawImage(components.get("scoreCard").getObjectImage(),
					components.get("scoreCard").getX(),
					components.get("scoreCard").getY(), null);
		
		// NEW HIGHSCORE IMAGE
		if(scoreWasGreater) {
			g.drawImage(components.get("newHighScore").getObjectImage(),
						components.get("newHighScore").getX(),
						components.get("newHighScore").getY(), null);
		}
		
		// DRAW MINI FONT FOR CURRENT AND BEST SCORE
		drawScore(g, score, true, 303, 276);
		drawScore(g, highScore.getHighestScore(), true, 303, 330);
		
		// HANDLE HIGHSCORE
		if(score > highScore.getHighestScore()) {
			// NEW BEST SCORE
			scoreWasGreater = true;
			highScore.setNewBest(score); // SET IN DATA FILE
		}
		
		// MEDAL
		if (score >= 10) { medal = "bronze";   }
		if (score >= 20) { medal = "silver";   }
		if (score >= 30) { medal = "gold"; 	   }
		if (score >= 40) { medal = "platinum"; }
		
		// ONLY AWARD A MEDAL IF THEY PASS A CERTAIN SCORE
		if(score > 9) {
			g.drawImage(components.get(medal).getObjectImage(),
						components.get(medal).getX(),
						components.get(medal).getY(), null);
		}
		
		// BUTTONS
		g.drawImage(components.get("playButton").getObjectImage(),
					components.get("playButton").getX(),
					components.get("playButton").getY(), null);
		
		g.drawImage(components.get("menuButton").getObjectImage(),
					components.get("menuButton").getX(),
					components.get("menuButton").getY(), null);
	}
	
	//---------------------------------------------------------------------------------------
	//---------------------------------------- KEYBOARD AND MOUSE CONTROL --------------------------
	
	// MOUSE CONTROL
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		
		// SAVE CLICKED POINT
		clickedPoint = e.getPoint();

		if(gameState == MENU) {
			
			if(!charSelect && isTouching(components.get("playButton").getRect())) {
				gameState = GAME;
				inStartGameState = true;
			}
			else if(isTouching(components.get("characterButton").getRect())) {
				
				charSelect = true;			
			}
			if(isTouching(paneCharacter1.getRect()) && charSelect) {
				selectCharacter(gameCharacter1);
				checkBox = 1;
			}
			else if(isTouching(paneCharacter2.getRect()) && charSelect) {
				selectCharacter(gameCharacter2);
				checkBox = 2;
			}
			else if(isTouching(paneCharacter3.getRect()) && charSelect) {
				selectCharacter(gameCharacter3);
				checkBox = 3;
			}
			else if(isTouching(components.get("exitButton").getRect())) {
				
				charSelect = false;
				
			}

		}
		else if(gameState == GAME) {
			
			if(currentCharacter.isAlive()) {
				// ALLOW JUMP WITH CLICKS
				if(inStartGameState) {
					inStartGameState = false;
				}
				
				// JUMP AND PLAY SOUND
				currentCharacter.jump();
				audio.jump();
			}
			else {
				
				// ON GAME OVER SCREEN, ALLOW RESTART AND characterButton BUTTONS
				if(isTouching(components.get("playButton").getRect())) {
					inStartGameState = true;
					gameState = GAME;
					restart();
					if(checkBox == 2) {currentCharacter = gameCharacter2;}
					else if(checkBox == 3) {currentCharacter = gameCharacter3;}
					currentCharacter.setGameStartPos();
				}
				if(isTouching(components.get("menuButton").getRect())) {
					
					inStartGameState = true;
					gameState = MENU;
					restart();
					if(checkBox == 2) {currentCharacter = gameCharacter2;}
					else if(checkBox == 3) {currentCharacter = gameCharacter3;}
				}
			}
		}
	}

	// KEYBOARD CONTROL
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(gameState == MENU) {
			// START GAME ON 'ENTER' KEY
			if(keyCode == KeyEvent.VK_ENTER) {
				gameState = GAME;
				inStartGameState = true;
			}
		}
		else if(gameState == GAME && currentCharacter.isAlive()) {
			
			if(keyCode == KeyEvent.VK_SPACE) {
				
				// EXIT INSTRUCTIONS STATE
				if(inStartGameState) {
					inStartGameState = false;
				}
				
				// JUMP AND PLAY THE AUDIO EFFECT
				currentCharacter.jump();
				audio.jump();
			}
		}
	}


	
	
	
}
