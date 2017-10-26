package views.practice;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import question.Question;
import question.PracticeQuestionFactory;
import game.Game;
import views.main_container.MainContainer;
import views.level.Level;
import views.level.LevelView;

/**
 * This screen sets up the practice mode.
 * 
 * @author osim082
 */
public class Practice implements Initializable {

	@FXML
	Button startBtn;

	@FXML
	TextField number;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setTextLimit(number, 2);

		// Numeric listener. Called when the number field changes.
		ChangeListener<String> numericListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// Get the calling textfield:
				StringProperty textProperty = (StringProperty) observable;
				TextField textField = (TextField) textProperty.getBean();

				// Strip non-numeric characters:
				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		};

		// Set the listener for the numeric TextFields:
		number.textProperty().addListener(numericListener);
	}

	@FXML
	public void startHit() {

		if (number.getText().length() == 0) {
			return;
		}

		int num = Integer.parseInt(number.getText());

		if (num > 0 && num < 100) {

			// Create the question
			Question question = new Question(num + "", num);

			// Retrieve the selected question factory and set the range on it.
			PracticeQuestionFactory questionFactory = new PracticeQuestionFactory(question);

			// Create the game model:
			Game game = new Game(questionFactory, 10);

			// Create a new level and set it as the view.
			LevelView levelView = new LevelView();
			MainContainer.instance().changeCenter(levelView);

			Level level = (Level) levelView.controller();
			game.setLevel(level);
			level.setGame(game);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Invalid question provided");
			alert.setContentText("The answer to the question should be an integer < 100.");
			alert.showAndWait();
		}
	}

	/**
	 * set up the limit of the text field
	 * 
	 * @param textfield
	 *            the textfield that need to be set
	 * @param length
	 *            maximum number of character can be entered
	 */
	private void setTextLimit(TextField textfield, int length) {
		textfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (textfield.getText().length() > length) {
					textfield.setText(textfield.getText().substring(0, length));
				}
			}
		});
	}

}
