package views.help;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import views.main_container.MainContainer;
import views.speech_help.SpeechHelpView;

public class Help implements Initializable {

	@FXML
	Button overview, play, quiz, dashboard;

	@FXML
	TextArea text;

	@FXML
	Button pronunciation;

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		text.setEditable(false);

		setOverview();
	}

	@FXML
	public void setOverview() {
		text.setText(
			"Tātai! is an app to help young adults learn Māori numbers."
		);
	}

	@FXML
	public void setPlay() {
		text.setText(
			"There are two different game types to choose from: single numbers and simple equations. " +
			"The game has two difficulty levels: numbers 1 - 9 and numbers 10 - 99. \n\n" +
			"To play, press 'Record' and say the name of the number. You have two attempts for each question.\n\n" +
			"You can unlock the harder difficulty by winning on easy with a score of 8 or above." 
		);
	}

	@FXML
	public void setQuiz() {
		text.setText(
			"Tātai! also allows you to create your own custom quizzes using the 'Quiz'" + 
			"option in the menu. \n\nYou can use this feature to practice numbers you find difficult."
		);
	}

	@FXML
	public void setDashboard() {
		text.setText(
			"The Dashboard allows you to track your past games. By double-clicking on an entry, " +
			"you can view more information."
		);
	}

	@FXML
	public void setPronunciation() {
		MainContainer.instance().changeCenter(new SpeechHelpView());
	}
}
