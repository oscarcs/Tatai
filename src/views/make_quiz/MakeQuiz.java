package views.make_quiz;

import java.net.URL;
import java.util.ResourceBundle;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
import game.SingleQuiz;
import numbers.Question;
import views.end_quiz.EndQuiz;
import views.end_quiz.EndQuizView;
import views.main_container.MainContainer;

/**
 * Controller class for 'New Quiz' screen.
 */
public class MakeQuiz implements Initializable {

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
		String operate = operation.getText();
		
		if (quiz.checkValid(first, second, operate)) {

			quiz.addQuiz(quiz.getQuestion(first, second, operate));

			if (quiz.getCurrentQuizNum() < quiz.getQuizData().getTotalQues()) {
				quiz.nextQuizNum();
				MakeQuizView quizView = new MakeQuizView();
				MainContainer.instance().changeCenter(quizView);
				MakeQuiz makeQuiz = (MakeQuiz) quizView.controller();
				makeQuiz.setQuiz(quiz);
			} 
			else {
				EndQuizView endView = new EndQuizView();
				MainContainer.instance().changeCenter(endView);
				EndQuiz endQuiz = (EndQuiz) endView.controller();
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
		questionNum.setText("Question " + quiz.getCurrentQuizNum());
	}
}
