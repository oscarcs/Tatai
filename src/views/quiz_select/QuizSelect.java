package views.quiz_select;

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

import game.Game;
import game.Quiz;
import game.User;
import question.QuizQuestionFactory;
import views.main_container.MainContainer;
import views.quiz_create.QuizCreate;
import views.quiz_create.QuizCreateView;
import views.level.Level;
import views.level.LevelView;

public class QuizSelect implements Initializable {

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
			quiz = new Quiz(result.get());

			QuizCreateView quizView = new QuizCreateView();
			MainContainer.instance().changeCenter(quizView);
			QuizCreate quizCreate = (QuizCreate) quizView.controller();
			
			quizCreate.setQuiz(quiz);
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
		Quiz quiz = null;

		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
				new FileInputStream(MainContainer.QUIZ_DIRECTORY + quizName)
			);
			quiz = (Quiz) objectInputStream.readObject();
			objectInputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
        // Retrieve the selected question factory and set the range on it.
        QuizQuestionFactory questionFactory = new QuizQuestionFactory(quiz);
        
        // Create the game model:
        Game game = new Game(questionFactory, 10);

        // Create a new level and set it as the view.
        LevelView levelView = new LevelView();
        MainContainer.instance().changeCenter(levelView);

        Level level = (Level) levelView.controller();
        game.setLevel(level);
        level.setGame(game);

		/*
		QuizGame quizGame = new QuizGame(quiz);
		PlayQuizView quizView = new PlayQuizView();
		MainContainer.instance().changeCenter(quizView);
		PlayQuiz playQuiz = (PlayQuiz) quizView.controller();
		playQuiz.setQuizGame(quizGame);
		quizGame.setPlayQuiz(playQuiz);
		*/
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
