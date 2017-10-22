package views.help;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import views.main_container.MainContainer;
import views.speech_help.SpeechHelpView;

public class Help implements Initializable {

	@FXML
	Button overview, play, dashBoard, quiz, gameLevel, gameType;

	@FXML
	Text text;

	@FXML
	Button pronunciation;
	
	@FXML
	public void oveviewHit() {
		text.setText("This software helps you learn Maori\n"
				+ " numbers. It is a software designed for\n"
				+ " young adult to learn Maori language.");
	}
	
	@FXML
	public void playHit() {
		text.setText("Press \"Play\" button on the left hand side\n"
				+ " to start the game\n"
				+ "Each game would have 10 questions\n"
				+ "There are 2 different game levels you can choose\n"
				+ "And there are 2 different game types\n"
				+ "\n"
				+ "Press the \"Record\" button to start the recording,\n"
				+ " and you have 2 attempt for each number.");
	}
	
	@FXML
	public void gameLevelHit() {
		text.setText("The game has 2 levels: \n"
				+ "\n"
				+ "In level 1, you need to pronounce maori\n"
				+ "number from 1 to 9\n"
				+ "\n"
				+ "In level 2, you need to pronounce maori\n"
				+ "number from 10 to 99\n"
				+ "\n"
				+ "You can unlock level 2 if you pass level 1 \n"
				+ "with score of 8 or above\n"
				+ "You will be prompted in the in game screen to \n"
				+ "tell you that you can now play the second level.\n");
	}

	@FXML
	public void gameTypeHit() {
		text.setText("You can choose between two modes.\n"
				+ "\n"
				+ "\"Single Number\": the question would be\n"
				+ "just a number\n"
				+ "\n"
				+ "\"Simple Equation\": the question would\n"
				+ "a equation\n"
				+ "Equation would be simple operations:\n"
				+ "addition, subtraction, multiplication\n"
				+ "and division");
	}
	
	@FXML
	public void dashBoardHit() {
		text.setText("Press \"DashBoard\" on the left hand side\n"
				+ "to view the games you have done, and the score\n"
				+ "of each game."
				+ "The detail of the game can be viewed by "
				+ "double click on the specific game.");
	}
	
	@FXML
	public void proHit() {
		MainContainer.instance().changeCenter(new SpeechHelpView());
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		text.setFont(new Font("System", 17));
		text.setText("This software helps you learn Maori\n"
				+ " numbers. It is a software designed for\n"
				+ " young adult to learn Maori language.");
	}
}
