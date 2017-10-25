package views.end_quiz;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import game.Quiz;
import game.QuizData;
import game.RoundData;
import game.SingleQuiz;
import numbers.Question;
import views.custom_quiz.CustomQuizView;
import views.main_container.MainContainer;

/**
 * Controller class for the final 
 */
public class EndQuiz implements Initializable {

	private Quiz quiz;

	@FXML
	Label name;
	
	@FXML
	Button save;
	
	@FXML
	TableView<SingleQuiz> table;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		name.setText("Questions for: " + quiz.getQuizData().getName());

		// Add the question number column.
		TableColumn<SingleQuiz, Integer> quizNumberColumn = 
			new TableColumn<SingleQuiz, Integer>("Quiz Number");
		quizNumberColumn.setMinWidth(100);
		quizNumberColumn.setCellValueFactory(
			new PropertyValueFactory<SingleQuiz, Integer>("quizNum")
		);

		// Add the question column.
		TableColumn<SingleQuiz, String> questionColumn = 
			new TableColumn<SingleQuiz, String>("Question");
		questionColumn.setMinWidth(100);
		questionColumn.setCellValueFactory(
			new PropertyValueFactory<SingleQuiz, String>("representation")
		);

		// Add the answer column.
		TableColumn<SingleQuiz, Integer> answerColumn = 
			new TableColumn<SingleQuiz, Integer>("Answer");
		answerColumn.setMinWidth(100);
		answerColumn.setCellValueFactory(
			new PropertyValueFactory<SingleQuiz, Integer>("answer")
		);

		table.setItems(getQuizzes());
		table.getColumns().addAll(quizNumberColumn, questionColumn, answerColumn);
		table.getSortOrder().add(quizNumberColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private ObservableList<SingleQuiz> getQuizzes() {
		return FXCollections.observableArrayList(quiz.getQuizData().getQuestion());
	}

	@FXML
	public void saveHit() {
		this.quiz.getQuizData().save();
		MainContainer.instance().changeCenter(new CustomQuizView());
	}
}
