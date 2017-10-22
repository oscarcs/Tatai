package views.speech_help;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import views.help.HelpView;
import views.main_container.MainContainer;


public class SpeechHelp implements Initializable{

	private HashMap<String, File> soundFile;
	
	@FXML
	Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b60, b54;
	
	@FXML
	Button help;
	
	@FXML
	public void b1hit() {
		playSound(soundFile.get("tahi.wav"));
	}
	
	@FXML
	public void b2hit() {
		playSound(soundFile.get("rua.wav"));
	}
	
	@FXML
	public void b3hit() {
		playSound(soundFile.get("toru.wav"));
	}
	
	@FXML
	public void b4hit() {
		playSound(soundFile.get("wha.wav"));
	}
	
	@FXML
	public void b5hit() {
		playSound(soundFile.get("rimaII.wav"));
	}
	
	@FXML
	public void b6hit() {
		playSound(soundFile.get("onoII.wav"));
	}
	
	@FXML
	public void b7hit() {
		playSound(soundFile.get("whituIII.wav"));
	}
	
	@FXML
	public void b8hit() {
		playSound(soundFile.get("waruII.wav"));
	}
	
	@FXML
	public void b9hit() {
		playSound(soundFile.get("iwa.wav"));
	}
	
	@FXML
	public void b10hit() {
		playSound(soundFile.get("tekau.wav"));
	}
	
	@FXML
	public void b60hit() {
		playSound(soundFile.get("60.wav"));
	}
	
	@FXML
	public void b54hit() {
		playSound(soundFile.get("54.wav"));
	}
	
	
	
	
	@FXML
	public void helpHit() {
		MainContainer.instance().changeCenter(new HelpView());
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		soundFile = new HashMap<String, File>();
		
		File voiceDir = new File("NumberRecordings");
		
		for (File f : voiceDir.listFiles()) {
			soundFile.put(f.getName(), f);
		}
		
		
	}
	
	private void playSound(File sound) {
		
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
