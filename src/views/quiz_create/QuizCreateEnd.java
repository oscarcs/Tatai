package views.quiz_create;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import game.Quiz;
import game.RoundData;
import question.Question;
import views.quiz_select.QuizSelectView;
import views.main_container.MainContainer;

/**
 * Controller class for the final 
 */
public class QuizCreateEnd implements Initializable {

	private Quiz quiz;

	@FXML
	Label name;
	
	@FXML
	Button save;
	
	@FXML
	TableView<Question> table;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
		name.setText("Questions for: " + quiz.getName());

		// Add the question number column.
		TableColumn<Question, Integer> indexColumn = new TableColumn<Question, Integer>("Quiz Number");
		indexColumn.setCellFactory(col -> {
			TableCell<Question, Integer> cell = new TableCell<>();
			cell.textProperty().bind(Bindings.when(cell.emptyProperty())
				.then("")
				.otherwise(cell.indexProperty().asString()));
			return cell;
		});

		// Add the question column.
		TableColumn<Question, String> questionColumn = new TableColumn<Question, String>("Question");
		questionColumn.setCellValueFactory(
			new PropertyValueFactory<Question, String>("representation")
		);

		// Add the answer column.
		TableColumn<Question, Integer> answerColumn = new TableColumn<Question, Integer>("Answer");
		answerColumn.setCellValueFactory(
			new PropertyValueFactory<Question, Integer>("answer")
		);
		
		indexColumn.setMinWidth(100);
		questionColumn.setMinWidth(100);
		answerColumn.setMinWidth(100);

		table.setItems(getQuestions());
		table.getColumns().addAll(indexColumn, questionColumn, answerColumn);
		table.getSortOrder().add(indexColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private ObservableList<Question> getQuestions() {
		return FXCollections.observableArrayList(quiz.getQuestions());
	}

	@FXML
	public void saveHit() {
		this.quiz.save();
		MainContainer.instance().changeCenter(new QuizSelectView());
	}
}
