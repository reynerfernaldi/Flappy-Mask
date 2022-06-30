package flappymask;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Components {

	// RESIZE FACTOR TO MATCH THE FRAME SIZE	
	private static final double RESIZE_FACTOR = 2.605;
	
	private static BufferedImage componentImages = null;
	private static BufferedImage titleImage = null;
	private static BufferedImage playButtonImage = null;
	private static BufferedImage charaButtonImage = null;
	private static BufferedImage characterPaneImage = null;
	private static BufferedImage exitButtonImage = null;
	private static BufferedImage checkIconImage = null;
	private static BufferedImage menuButtonImage = null;
	private static ArrayList<BufferedImage> character1 = new ArrayList<BufferedImage>();
	private static ArrayList<BufferedImage> character2 = new ArrayList<BufferedImage>();
	private static ArrayList<BufferedImage> character3 = new ArrayList<BufferedImage>();
	
	// HASH MAP AS A CONTAINER OF THE OBJECTS
	private static HashMap<String, GameObject> components = new HashMap<String, GameObject>();
	
	// CONSTRUCTOR
	public Components() {
		
		loadImage();
		// BACKGROUNDS
		components.put("background1", new GameObject(resize(componentImages.getSubimage(0, 5, 144, 256)),   0, 0));
		components.put("background2", new GameObject(resize(componentImages.getSubimage(146, 5, 144, 256)), 0, 0));
		
		// PIPES
		components.put("pipe-top",    new GameObject(resize(componentImages.getSubimage(56, 323, 26, 160)), 0, 0));
		components.put("pipe-bottom", new GameObject(resize(componentImages.getSubimage(84, 323, 26, 160)), 0, 0));
		
		// CHARACTERS
		components.put("c1move1", new GameObject(character1.get(0), 172, 250));
		components.put("c1move2", new GameObject(character1.get(1), 172, 250));
		components.put("c1move3", new GameObject(character1.get(2),  172, 250));
		
		components.put("c2move1", new GameObject(character2.get(0), 172, 250));
		components.put("c2move2", new GameObject(character2.get(1), 172, 250));
		components.put("c2move3", new GameObject(character2.get(2),  172, 250));
		
		components.put("c3move1", new GameObject(character3.get(0), 172, 250));
		components.put("c3move2", new GameObject(character3.get(1), 172, 250));
		components.put("c3move3", new GameObject(character3.get(2),  172, 250));
		
		// BUTTONS / PANE
		components.put("playButton",   new GameObject(playButtonImage, FlappyMaskMain.WIDTH/2 - playButtonImage.getWidth()/2, 390));
		components.put("characterButton",  new GameObject(charaButtonImage, FlappyMaskMain.WIDTH/2 - charaButtonImage.getWidth()/2, 448));
		components.put("characterPane",   new GameObject(characterPaneImage, FlappyMaskMain.WIDTH/2 - characterPaneImage.getWidth()/2, 359));
		components.put("exitButton",   new GameObject(exitButtonImage, FlappyMaskMain.WIDTH/2 - 136, 350));
		components.put("checkIcon", new GameObject(checkIconImage, 0, 0));
		components.put("menuButton", new GameObject(menuButtonImage, FlappyMaskMain.WIDTH/2 - menuButtonImage.getWidth()/2, 460));
		
		// HELP / TEXTS
		components.put("newHighScore", new GameObject(resize(componentImages.getSubimage(112, 501, 16, 7)),  210, 305));
		components.put("titleText",    new GameObject(titleImage, FlappyMaskMain.WIDTH/2 - titleImage.getWidth()/2, 100));
		components.put("getReadyText", new GameObject(resize(componentImages.getSubimage(295, 59, 92, 30)),  68, 180));
		components.put("gameOverText", new GameObject(resize(componentImages.getSubimage(395, 59, 96, 26)),  62, 100));
		components.put("instruction", new GameObject(resize(componentImages.getSubimage(292, 91, 57, 49)),  113, 300));
		
		// SCORE IMAGES
		// LARGE NUMBERS
		components.put("score-0", new GameObject(resize(componentImages.getSubimage(496, 60, 12, 18)), 0, 0));
		components.put("score-1", new GameObject(resize(componentImages.getSubimage(136, 455, 8, 18)), 0, 0));
		
		int score = 2;
		for (int i = 292; i < 335; i += 14) {
			components.put("score-" + score,       new GameObject(resize(componentImages.getSubimage(i, 160, 12, 20)), 0, 0));
			components.put("score-" + (score + 4), new GameObject(resize(componentImages.getSubimage(i, 184, 12, 20)), 0, 0));
			score++;
		}
		
		// SMALL NUMBERS
		score = 0;
		for (int i = 323; score < 10; i += 9) {
			components.put("mini-score-" + score, new GameObject(resize(componentImages.getSubimage(138, i, 10, 10)), 0, 0));
			score ++;
			if (score % 2 == 0) { i += 8; }
		}
		
		// MEDALS
		components.put("bronze",   new GameObject(resize(componentImages.getSubimage(112, 477, 22, 22)),  73, 285));
		components.put("silver",   new GameObject(resize(componentImages.getSubimage(112, 453, 22, 22)),  73, 285));
		components.put("gold",     new GameObject(resize(componentImages.getSubimage(121, 282, 22, 22)),  73, 285));
		components.put("platinum", new GameObject(resize(componentImages.getSubimage(121, 258, 22, 22)),  73, 285));
		
		//OTHER ASSETS
		components.put("base",      new GameObject(resize(componentImages.getSubimage(292, 0, 168, 56)),  0, 521));
		components.put("scoreCard", new GameObject(resize(componentImages.getSubimage(3, 259, 113, 62)),  40, 230));
	}
	
//--------------------- GETTER(s) and SETTER(s) METHOD --------------------------------------------------------//
	
	private void loadImage() {
		try {
			componentImages = ImageIO.read(new File("res/img/components.png"));
			titleImage = ImageIO.read(new File("res/img/title2.png"));
			playButtonImage = ImageIO.read(new File("res/img/playButton.png"));
			charaButtonImage = ImageIO.read(new File("res/img/characterButton.png"));
			characterPaneImage = ImageIO.read(new File("res/img/characterPane.png"));
			exitButtonImage = ImageIO.read(new File("res/img/exitButton.png"));
			checkIconImage = ImageIO.read(new File("res/img/checkIcon.png"));
			menuButtonImage = ImageIO.read(new File("res/img/menu.png"));
			for(int i=1; i<=3; i++)
				character1.add(ImageIO.read(new File("res/img/char1/"+ i +".png")));
			for(int i=1; i<=3; i++)
				character2.add(ImageIO.read(new File("res/img/char2/"+ i +".png")));
			for(int i=1; i<=3; i++)
				character3.add(ImageIO.read(new File("res/img/char3/"+ i +".png")));
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image couldn't be found");
			System.exit(-1);
			return;
		}
	}
	
	private static BufferedImage resize (BufferedImage image) {

		// NEW WIDTH AND HEIGHT
		int newWidth = (int) (image.getWidth() * RESIZE_FACTOR);
		int newHeight = (int) (image.getHeight() * RESIZE_FACTOR);

		// NEW BUFFERED IMAGE WITH NEW WEIGHT AND HEIGHT
	    BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(image, 0, 0, newWidth, newHeight, null);
	    g.dispose();

	    return resizedImage;
	}
	
	public HashMap<String, GameObject> getComponents(){
		return components;
	}
	
}
