package flappymask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class HighScore {
	
	// READ/WRITE HIGH SCORE FILE
	private static final String DIRECTORY	= "res/data/highscore.dat";
	private static File dataFile			= new File(DIRECTORY);
	private static Scanner dataScanner		= null;
	private static PrintWriter dataWriter	= null;
	
	// HIGH SCORE
	private int highestScore;
	
	//CONSTRUCTOR
	public HighScore() {
		
		// LOAD SCANNER WITH DATA FILE
		try {
			dataScanner = new Scanner(dataFile);
		} catch (IOException e) {
			System.out.println("Cannot load highscore!");
		}
		
		// STORE HIGH SCORE
		highestScore = Integer.parseInt(dataScanner.nextLine());
	}
	
//--------------------------- GETTERS/SETTERS ---------------------------------------------//
	
	public int getHighestScore() {
		return highestScore;
	}
	
	public void setNewBest(int newBestScore) {
		highestScore = newBestScore;
		
		try {
			// WRITE NEW HIGH SCORE TO DATA FILE
			dataWriter = new PrintWriter(DIRECTORY, "UTF-8");
			dataWriter.println(Integer.toString(highestScore));
			dataWriter.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println("Could not set new highscore!");
		}
	}
	
}
