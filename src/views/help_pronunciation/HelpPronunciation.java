package views.help_pronunciation;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import views.help.HelpView;
import views.main_container.MainContainer;

/**
 * Controller for speechHelp, which allows the user to hear example
 * pronunciations for numbers.
 */
public class HelpPronunciation implements Initializable {

	private HashMap<String, File> soundFile;

	@FXML
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b60, b54;

	@FXML
	Button help;

	@FXML
	TextArea text;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		text.setEditable(false);

		soundFile = new HashMap<String, File>();
		File voiceDir = new File("NumberRecordings");
		for (File f : voiceDir.listFiles()) {
			soundFile.put(f.getName(), f);
		}

		text.setFont(new Font("System", 16.5));
		// Set the explanatory text:
		text.setText("All numbers from 11 onwards follow a basic pattern. For example, to say twenty-two, "
				+ "simply say 'rua tekau', which means 20, and then 'mā rua', which means and two, therefore becomes 'rua tekau mā rua'. "
				+ "So all you have to do is learn the numbers from 1 - 10 and then the words for "
				+ "10, 20, 30, 40 and so on. \n" + "For example: 60  is 'ono tekau' and 54 is 'rima tekau mā whā'");
	}

	/*
	 * the following are the action performed when the number button were clicked
	 */

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

	/**
	 * Play a sound.
	 */
	private void playSound(File sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
