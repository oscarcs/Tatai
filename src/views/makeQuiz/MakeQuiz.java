package views.makeQuiz;

import java.net.URL;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import game.Quiz;
import game.SingleQuiz;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import numbers.Equation;
import views.endQuiz.EndQuiz;
import views.endQuiz.EndQuizView;
import views.main_container.MainContainer;

public class MakeQuiz implements Initializable {

	private Quiz quiz;

	@FXML
	Button previousBtn, nextBtn;

	@FXML
	TextField firstNum, secNum, operation;

	@FXML
	Label questionNum, valid;

	@FXML
	public void nextHit() {
		int first = Integer.parseInt(firstNum.getText());
		int second = Integer.parseInt(secNum.getText());
		String operate = operation.getText();
		if (quiz.checkValid(first, second, operate)) {
			quiz.addQuiz(quiz.getEquation(first, second, operate));
			valid.setText("Valid");
			if (quiz.getCurrentQuizNum() < quiz.getQuizData().getTotalQues()) {
				quiz.nextQuizNum();
				MakeQuizView quizView = new MakeQuizView();
				MainContainer.instance().changeCenter(quizView);
				MakeQuiz makeQuiz = (MakeQuiz) quizView.controller();
				makeQuiz.setQuiz(quiz);
			} else {
				EndQuizView endView = new EndQuizView();
				MainContainer.instance().changeCenter(endView);
				EndQuiz endQuiz = (EndQuiz) endView.controller();
				endQuiz.setQuiz(quiz);
			}
		} else {
			valid.setText("Invalid");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		firstNum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					firstNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		secNum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					secNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		operation.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[*/+-]") || newValue.length() > 1) {
					operation.setText(newValue.replaceAll("[^*/+-]", ""));
				}
			}

		});

		setTextLimit(firstNum, 2);
		setTextLimit(secNum, 2);
		setTextLimit(operation, 1);

		firstNum.setFont(Font.font("System", 40));
		secNum.setFont(Font.font("System", 40));
		operation.setFont(Font.font("System", 40));

		valid.setText("");

	}

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

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		questionNum.setText("Question" + quiz.getCurrentQuizNum());
	}

}
