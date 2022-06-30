package flappymask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {

	private AudioInputStream audioInputStream;
	private Clip clip;

	private void playSound (String sound) {

		// Path to sound file
		String soundURL = sound + ".wav";

		// Try to load and play sound
		try {
		    audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(soundURL));
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("Count not load %s.wav!\n", sound);
		}
	}

	// METHOD TO PLAY JUMP SOUND
	public void jump () {
		playSound("jump");
	}
	
	// METHOD TO PLAY GET POINT SOUND
	public void point () {
		playSound("point");
	}

	// METHOD TO PLAY PIPE HIT SOUND
	public void hit () {
		playSound("hit");
	}

}

