package views.customQuiz;

import java.io.File;
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
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import views.main_container.MainContainer;

public class CustomeQuiz implements Initializable {

	@FXML
	Button playBtn, deleteBtn, newBtn;

	@FXML
	ComboBox<String> quizBox;
	private ArrayList<String> quizList;

	@FXML
	public void newBtnHit() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("TestName");

		// Set the button types.
		ButtonType CreateBtnType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(CreateBtnType, ButtonType.CANCEL);

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		// gridPane.setPadding(new Insets(20, 150, 10, 10));

		TextField name = new TextField();
		name.setPromptText("Name of the Quiz");
		TextField number = new TextField();
		number.setPromptText("Number of Questions");

		// force the field to be numeric only
		number.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if (!newValue.matches("\\d*")) {
					dialog.getDialogPane().lookupButton(CreateBtnType).setDisable(true);
				}
			}
		});

		gridPane.add(new Label("Please enter the name of the Quiz"), 0, 0);
		gridPane.add(name, 0, 1);
		gridPane.add(new Label("number of Questions"), 0, 2);
		gridPane.add(number, 1, 2);
		gridPane.add(new Label("the maximum number of question is 20"), 0, 2);

		dialog.getDialogPane().setContent(gridPane);

		// Request focus on the username field by default.
		Platform.runLater(() -> name.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == CreateBtnType) {
				return new Pair<>(name.getText(), number.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(pair -> {

			if (quizList.contains(pair.getKey()) || pair.getKey().equals("")) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Name invalid!");
				alert.setHeaderText("Please enter a different name!");
				alert.setContentText("This name already exists or the box is empty, please enter a different one!");
				alert.showAndWait();
				return;
			}

			System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
		});
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
