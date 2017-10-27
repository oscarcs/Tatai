package views.quiz_create;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import game.Quiz;
import question.Question;
import question.Operator;
import views.main_container.MainContainer;

/**
 * Controller class for 'New Quiz' screen.
 * @author szhu842, osim082
 */
public class QuizCreate implements Initializable {

	private static final int QUIZ_LENGTH = 10;

	// The Quiz object that we're creating.
	private Quiz quiz;

	@FXML
	Button previousBtn, nextBtn;
	@FXML
	TextField firstNum, secondNum, operation;
	@FXML
	Label questionNum;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Numeric listener. Called when one of the numeric fields is changed.
		ChangeListener<String> numericListener = new ChangeListener<String>() {
			@Override
			public void changed(
				ObservableValue<? extends String> observable, 
				String oldValue, 
				String newValue
			) {
				// Get the calling textfield
				StringProperty textProperty = (StringProperty) observable;
				TextField textField = (TextField) textProperty.getBean();

				if (!newValue.matches("\\d*")) {
					textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		};

		// Set the listener for the numeric TextFields:
		firstNum.textProperty().addListener(numericListener);
		secondNum.textProperty().addListener(numericListener);

		// Set the listener for the centre TextField, the one for the operation.
		operation.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(
				ObservableValue<? extends String> observable, 
				String oldValue, 
				String newValue
			) {
				if (!newValue.matches("[*/+-]") || newValue.length() > 1) {
					operation.setText(newValue.replaceAll("[^*/+-]", ""));
				}
			}
		});

		setTextLimit(firstNum, 2);
		setTextLimit(secondNum, 2);
		setTextLimit(operation, 1);
	}

	@FXML
	public void nextHit() {

		if (firstNum.getText().length() == 0 || 
			secondNum.getText().length() == 0 || 
			operation.getText().length() == 0
		) {
			return;
		}

		int first = Integer.parseInt(firstNum.getText());
		int second = Integer.parseInt(secondNum.getText());

		Operator operator = null;
		switch (operation.getText()) {
			case "+":
				operator = Operator.ADD;
				break;
			case "-":
				operator = Operator.SUBTRACT;
				break;
			case "*":
				operator = Operator.MULTIPLY;
				break;
			case "/":
				operator = Operator.DIVIDE;
				break;
		}
		
		if (quiz.checkQuestion(first, second, operator)) {

			quiz.addQuestion(first, second, operator);

			if (quiz.getTotalQuestions() < QUIZ_LENGTH) {
				QuizCreateView quizView = new QuizCreateView();
				MainContainer.instance().changeCenter(quizView);
				QuizCreate quizCreate = (QuizCreate) quizView.controller();
				quizCreate.setQuiz(quiz);
			} 
			else {
				QuizCreateEndView endView = new QuizCreateEndView();
				MainContainer.instance().changeCenter(endView);
				QuizCreateEnd endQuiz = (QuizCreateEnd) endView.controller();
				endQuiz.setQuiz(quiz);
			}
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Invalid question provided");
			alert.setContentText("The answer to the question should be an integer < 100.");
			alert.showAndWait();
		}
	}

	private void setTextLimit(TextField textfield, int length) {
		textfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(
				ObservableValue<? extends String> observable, 
				String oldValue, 
				String newValue
			) {
				if (textfield.getText().length() > length) {
					textfield.setText(textfield.getText().substring(0, length));
				}
			}
		});
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		questionNum.setText("Question " + (quiz.getTotalQuestions() + 1));
	}
}
