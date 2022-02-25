package mainpackage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundObject {
	
	private boolean playing = false;
	private String filename;
	private File file;
	private Clip clip;
	private FloatControl gainControl;
	
	public SoundObject(String filename) {
		this.filename = "materials/audio/" + filename;
		
		try {
			file = new File(this.filename);
			if (file.exists()) {
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
                clip.open(sound);
                playing = true;
			} else {
				throw new RuntimeException("Sound File Not Found: " + this.filename);
			}
		} catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	
	public void setVolume(double gain) {
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
		gainControl.setValue(dB);

	}
	
	public void play() {
		playing = true;
		
		clip.setFramePosition(0);
        clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		playing = false;
		
		clip.stop();
	}
	
	public boolean isPlaying() {
		return playing;
	}
	
	public String getFilename() {
		return filename;
	}
}
