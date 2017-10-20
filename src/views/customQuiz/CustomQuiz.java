package views.customQuiz;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import game.Quiz;
import game.QuizData;
import game.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import views.main_container.MainContainer;
import views.makeQuiz.MakeQuiz;
import views.makeQuiz.MakeQuizView;

public class CustomQuiz implements Initializable {

	private QuizData quizData;
	private Quiz quiz;
	
	@FXML
	Button playBtn, deleteBtn, newBtn;

	@FXML
	ComboBox<String> quizBox;
	private ArrayList<String> quizList;

	@FXML
	public void newBtnHit() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Quiz");
		dialog.setHeaderText("Creating new Quiz");
		dialog.setContentText("Please enter the name of the Quiz:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (quizList.contains(result.get()) || result.get().equals("")) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Quiz name invalid!");
				alert.setHeaderText("Please enter a different Quiz name!");
				alert.setContentText("This name already exists or the box is empty, please enter a different one!");
				alert.showAndWait();
				return;
			}
			// If there has been a string passed in (user didn't cancel) then pass the user to the mainContainer.
			quizData = new QuizData(result.get());
			quiz = new Quiz(result.get());
			//quiz.getQuizData().save();
			MakeQuizView quizView = new MakeQuizView();
			MainContainer.instance().changeCenter(quizView);
			MakeQuiz makeQuiz = (MakeQuiz)quizView.controller();
			quiz.setQuiz(makeQuiz);
			quiz.setQuizfile(quizData);
			makeQuiz.setQuiz(quiz);
		}

		

		
	}

	@FXML
	public void deleteHit() {
		// If no user was selected.
		if (quizBox.getSelectionModel().getSelectedItem() == null
				|| quizBox.getSelectionModel().getSelectedItem().equals("")) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No Quiz selected!");
			alert.setHeaderText("Please select a Quiz!");
			alert.setContentText("You not selected a Quiz, please either select a Quiz or create one.");
			alert.showAndWait();
			return;
		}

		// If user was selected.
		String username = quizBox.getSelectionModel().getSelectedItem();

		// Create a confirmation dialog.
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("You are removing the Quiz!");
		alert.setContentText("Are you sure you want to delete the Quiz " + username + " ?");
		ButtonType typeYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType typeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(typeYes, typeNo);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == typeYes) {
			File delete = new File(MainContainer.QUIZ_DIRECTORY + username);
			// Delete file if user presses yes.
			quizBox.getItems().remove(delete.getName());
			delete.delete();
		} else {

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		File quizDir = new File("quiz");
		if (!quizDir.exists()) {
			quizDir.mkdir();
		}

		quizList = new ArrayList<String>();
		File[] quizFiles = quizDir.listFiles();
		for (File quizFile : quizFiles) {
			quizBox.getItems().add(quizFile.getName());
			quizList.add(quizFile.getName());
		}

	}

}
