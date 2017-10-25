package views.custom_quiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
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

import game.Quiz;
import game.QuizData;
import game.QuizGame;
import game.User;
import views.main_container.MainContainer;
import views.make_quiz.MakeQuiz;
import views.make_quiz.MakeQuizView;
import views.play_quiz.PlayQuiz;
import views.play_quiz.PlayQuizView;

public class CustomQuiz implements Initializable {

	private QuizData quizData;
	private Quiz quiz;

	@FXML
	Button playBtn, deleteBtn, newBtn;

	@FXML
	ComboBox<String> quizBox;
	private ArrayList<String> quizList;

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

	@FXML
	public void newBtnHit() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Quiz");
		dialog.setHeaderText("Creating new Quiz");
		dialog.setContentText("Please enter the name of the Quiz:");

		// Get the name for the new quiz using a dialog.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (quizList.contains(result.get()) || result.get().equals("")) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Create Quiz");
				alert.setHeaderText("Invalid Quiz Name");
				alert.setContentText("This name already exists or is invalid.");
				alert.showAndWait();
				return;
			}

			// If there has been a string passed in (user didn't cancel) then pass the user
			// to the mainContainer.
			quizData = new QuizData(result.get());
			quiz = new Quiz(result.get());

			MakeQuizView quizView = new MakeQuizView();
			MainContainer.instance().changeCenter(quizView);
			MakeQuiz makeQuiz = (MakeQuiz) quizView.controller();
			
			quiz.setQuiz(makeQuiz);
			quiz.setQuizfile(quizData);
			makeQuiz.setQuiz(quiz);
		}

	}

	@FXML
	public void playHit() {

		if (quizBox.getSelectionModel().getSelectedItem() == null || 
			quizBox.getSelectionModel().getSelectedItem().equals("")
		) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Play Quiz");
			alert.setHeaderText("Please Select a Quiz");
			alert.setContentText("Select a quiz or create a new one.");
			alert.showAndWait();
			return;	
		}
		
		String quizName = quizBox.getSelectionModel().getSelectedItem();
		QuizData quizData = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
				new FileInputStream(MainContainer.QUIZ_DIRECTORY + quizName)
			);
			quizData = (QuizData) objectInputStream.readObject();
			objectInputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		QuizGame quizGame = new QuizGame(quizData);
		PlayQuizView quizView = new PlayQuizView();
		MainContainer.instance().changeCenter(quizView);
		PlayQuiz playQuiz = (PlayQuiz) quizView.controller();
		playQuiz.setQuizGame(quizGame);
		quizGame.setPlayQuiz(playQuiz);
	}

	@FXML
	public void deleteHit() {
		// If no quiz was selected:
		if (quizBox.getSelectionModel().getSelectedItem() == null || 
			quizBox.getSelectionModel().getSelectedItem().equals("")
		) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Delete Quiz");
			alert.setHeaderText("Please Select a Quiz");
			alert.setContentText("Please select a quiz to delete.");
			alert.showAndWait();
			return;
		}

		// Otherwise, get the name of the quiz.
		String quizName = quizBox.getSelectionModel().getSelectedItem();

		// Create a confirmation dialog:
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("You are removing the Quiz!");
		alert.setContentText("Are you sure you want to delete the Quiz " + quizName + " ?");
		ButtonType typeYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType typeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(typeYes, typeNo);
		Optional<ButtonType> result = alert.showAndWait();
		
		// Delete file if user presses yes.
		if (result.get() == typeYes) {
			File delete = new File(MainContainer.QUIZ_DIRECTORY + quizName);
			quizBox.getItems().remove(delete.getName());
			delete.delete();
		}
	}
}
