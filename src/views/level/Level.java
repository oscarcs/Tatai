package views.level;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Collections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import game.Game;
import views.end_game_screen.EndGameScreen;
import views.end_game_screen.EndGameScreenView;
import views.main_container.MainContainer;

/**
 * Class for the main game screen in the application.
 */
public class Level implements Initializable {

	private HashMap<Integer, Rectangle> progressBar;

	@FXML
	Rectangle r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;

	@FXML
	Circle circle;

	@FXML
	Button recordButton, playButton, nextQuestionButton;

	@FXML
	Text questionText, answerStatus, receivedAnswerText;

	@FXML
	Text attemptText, roundText;

	private Game game;

	private HashMap<Integer, Boolean> roundData;

	/**
	 * Method that is called to set the game model to this level.
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
		game.setLevel(this);
		
		questionText.setText(game.questionText());
		attemptText.setText("Attempt " + game.getCurrentAttempt());
		answerStatus.setText("Waiting");
		receivedAnswerText.setText("We received: ");

		roundData = game.getRoundData();
		this.setRoundColour(roundData);
	}

	/**
	 * Initialize this scene.
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nextQuestionButton.setDisable(true);
		playButton.setDisable(true);

		progressBar = new HashMap<Integer, Rectangle>();
		progressBar.put(1, r1);
		progressBar.put(2, r2);
		progressBar.put(3, r3);
		progressBar.put(4, r4);
		progressBar.put(5, r5);
		progressBar.put(6, r6);
		progressBar.put(7, r7);
		progressBar.put(8, r8);
		progressBar.put(9, r9);
		progressBar.put(10, r10);	
	}
	
	/**
	 * When the user presses the record button.
	 */
	@FXML
	public void recordHit() {
		recordButton.setDisable(true);
		nextQuestionButton.setDisable(true);
		circle.setFill(Color.YELLOW);

		game.record();
	}

	/**
	 * When the user presses the record button.
	 */
	@FXML
	public void playHit() {
		game.play();
	}

	/**
	 * Called when 'next question' button is pressed.
	 */
	@FXML
	public void nextQuestionHit() {
		// Create a new level and pass the game into it.
		LevelView levelView = new LevelView();
		MainContainer.instance().changeCenter(levelView);
		Level level = (Level) levelView.controller();
		level.setGame(game);
	}

	/**
	 * When the recording has completed this method is called.
	 */
	public void recordingDone() {

		recordButton.setDisable(false);
		playButton.setDisable(false);

		game.process();
	}

	/**
	 * When processing is completed this method is called.
	 */
	public void processingDone() {
	}

	/**
	 * When the user fails an attempt but the game is not over, this method is
	 * called.
	 */
	public void failedAttempt() {
		recordButton.setDisable(false);
		attemptText.setText("Attempt: " + game.getCurrentAttempt());
		answerWrong();
	}

	/**
	 * When the user answers correct this method is called.
	 */
	public void answerCorrect() {
		answerStatus.setText("Correct");
		answerStatus.setFill(Color.GREEN);
		circle.setFill(Color.GREEN);

		receivedAnswerText.setText("We received: " + game.getReceivedAnswer());
	}

	/**
	 * When the user answers wrong this method is called.
	 */
	public void answerWrong() {
		answerStatus.setText("Incorrect");
		answerStatus.setFill(Color.RED);
		circle.setFill(Color.RED);
		
		receivedAnswerText.setText("We received: " + game.getReceivedAnswer());
	}

	/**
	 * When the user is allowed to go on the next question this method is called.
	 */
	public void nextLevel() {
		nextQuestionButton.setDisable(false);
	}

	/**
	 * When the game is over this method is called.
	 */
	public void endGame() {
		// Create a end game screen and pass the game into it.
		EndGameScreenView endGameScreenView = new EndGameScreenView();
		MainContainer.instance().changeCenter(endGameScreenView);
		EndGameScreen endGameScreen = (EndGameScreen) endGameScreenView.controller();
		endGameScreen.setGame(game);
	}

	public void setRoundColour(HashMap<Integer,Boolean> data) {

	    System.out.println(Collections.singletonList(data));

		for (int i = 1; i <= data.size(); i++) {
			if (data.get(i)) {
				progressBar.get(i).setFill(Color.web("#55c1ff"));
				System.out.println("green");
			} 
			else {
				progressBar.get(i).setFill(Color.web("#ff5b5b"));
				System.out.println("red");
			}
		}

	}

}
