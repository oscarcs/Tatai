package views.endQuiz;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import game.Quiz;
import game.QuizData;
import game.RoundData;
import game.SingleQuiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import numbers.Equation;

public class EndQuiz implements Initializable {

	private Quiz quiz;

	@FXML
	Label name;

	@FXML
	TableView<SingleQuiz> table;

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		name.setText(quiz.getQuizData().getName());

		TableColumn<SingleQuiz, Integer> quizNumberColumn = new TableColumn<>("Quiz Number");
		quizNumberColumn.setMinWidth(100);
		quizNumberColumn.setCellValueFactory(new PropertyValueFactory<>("quizNum"));

		TableColumn<SingleQuiz, String> equationColumn = new TableColumn<>("Equation");
		equationColumn.setMinWidth(100);
		equationColumn.setCellValueFactory(new PropertyValueFactory<>("representation"));

		TableColumn<SingleQuiz, Integer> answerColumn = new TableColumn<>("Answer");
		answerColumn.setMinWidth(100);
		answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));

		table.setItems(quizs());
		table.getColumns().addAll(quizNumberColumn, equationColumn, answerColumn);

		table.getSortOrder().add(quizNumberColumn);
		this.quiz.getQuizData().save();
	}

	private ObservableList<SingleQuiz> quizs() {
		return FXCollections.observableArrayList(quiz.getQuizData().getQuestion());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
