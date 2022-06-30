package flappymask;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;

public class FlappyMaskMain extends JFrame implements ActionListener{
	
	GamePanel gamePanel;
	Timer gameTimer;
	
	// GAME/FRAME CONSTANTS
	public static final int WIDTH	= 375;
	public static final int HEIGHT	= 667;
	private static final int DELAY	= 12;
	
	// CONSTRUCTOR
	public FlappyMaskMain() {
		
		super("Flappy Mask");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		
		// GAME TIMER
		gameTimer = new Timer(DELAY, this);
		gameTimer.start();
		
		// ADD GAME PANEL TO THE FRAME
		gamePanel = new GamePanel();
		this.add(gamePanel);
		
		// ADD ICON TO THE FRAME
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("res/img/icon.png"));
		
		this.setResizable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(gamePanel != null && gamePanel.ready) {
			gamePanel.repaint();
		}
		
	}
	
//------------------------- MAIN METHOD --------------------------------------------------//
	
	public static void main(String[] args) {
		FlappyMaskMain gameFlappyMask = new FlappyMaskMain();
	}
}
