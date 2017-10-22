package views.playQuiz;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import game.Game;
import game.QuizGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import views.end_game_screen.EndGameScreen;
import views.end_game_screen.EndGameScreenView;
import views.level.Level;
import views.level.LevelView;
import views.main_container.MainContainer;

public class PlayQuiz implements Initializable{

	
	
	private HashMap<Integer, Rectangle> progressBar;

	@FXML
	Rectangle r1, r2, r3, r4, r5, r6, r7, r8, r9, r10;

	@FXML
	Button continueButton, recordButton;

	@FXML
	Text equationText, answerStatus, recordStatus, receivedAnswerText;

	@FXML
	Text attemptText, roundText;

	private QuizGame quizGame;

	private HashMap<Integer, Boolean> roundData;

	/**
	 * Method that is called to set the game model to this level.
	 * 
	 * @param game
	 */
	public void setQuizGame(QuizGame quizGame) {
		this.quizGame = quizGame;
		quizGame.setPlayQuiz(this);
		equationText.setText(quizGame.equationText());
		recordStatus.setText("Press the button to record...");
		attemptText.setText("Attempt " + quizGame.getCurrentAttempt());
		answerStatus.setText("");
		receivedAnswerText.setText("We received: ");

		progressBar.get(3).setFill(Color.RED);
		r1.setFill(Color.RED);
		quizGame.setColour();

		roundData = quizGame.getRoundData();
		this.setRoundColour(roundData);

		// setRoundColour(game.roundCorrect);
		System.out.println(quizGame.getCurrentRound());
		System.out.println(quizGame.roundCorrect.size() + "elements");

	}

	/**
	 * Things that are done when the page loads, currently this disables the
	 * continueButton.
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		continueButton.setDisable(true);

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

		// recordButton.setDisable(true);
		// recordStatus.setText("recording....");
		// game.record();

		quizGame.winRound();

	}

	/**
	 * When the recording has completed this method is called.
	 */
	public void recordingDone() {
		recordStatus.setText("processing...");
		quizGame.process();
	}

	/**
	 * When processing is completed this method is called.
	 */
	public void processingDone() {
		recordStatus.setText("Processing done.");
	}

	/**
	 * When the user fails an attempt but the game is not over, this method is
	 * called.
	 */
	public void failedAttempt() {
		recordButton.setDisable(false);
		attemptText.setText("Attempt: " + quizGame.getCurrentAttempt());
		recordStatus.setText("Press the button to record...");
		answerWrong();
	}

	/**
	 * When the user answers correct this method is called.
	 */
	public void answerCorrect() {
		answerStatus.setText("Correct");
		answerStatus.setFill(Color.GREEN);
		receivedAnswerText.setText("We received: " + quizGame.getReceivedAnswer());
	}

	/**
	 * When the user answers wrong this method is called.
	 */
	public void answerWrong() {
		answerStatus.setText("Incorrect");
		answerStatus.setFill(Color.RED);
		receivedAnswerText.setText("We received: " + quizGame.getReceivedAnswer());
	}

	/**
	 * When the user hits continue this method is called.
	 */
	@FXML
	public void continueHit() {
		// Create a new level and pass the game into it.
		PlayQuizView quizView = new PlayQuizView();
		MainContainer.instance().changeCenter(quizView);
		PlayQuiz playQuiz = (PlayQuiz) quizView.controller();
		playQuiz.setQuizGame(quizGame);

	}

	/**
	 * When the user is allowed to go on the next user this method is called.
	 */
	public void nextQuiz() {
		continueButton.setDisable(false);
	}

	/**
	 * When the game is over this method is called.
	 */
	public void endGame() {
		// Create a end game screen and pass the game into it.
//		EndGameScreenView endGameScreenView = new EndGameScreenView();
//		MainContainer.instance().changeCenter(endGameScreenView);
//		EndGameScreen endGameScreen = (EndGameScreen) endGameScreenView.controller();
//		endGameScreen.setGame(quizGame);
	}

	public void setRoundColour(HashMap<Integer, Boolean> data) {
		for (int i = 1; i <= data.size(); i++) {

			if (data.get(i)) {
				progressBar.get(i).setFill(Color.web("#55c1ff"));
				System.out.println("green");
			} else {
				progressBar.get(i).setFill(Color.web("#ff5b5b"));
				System.out.println("red");
			}
		}

	}
}
