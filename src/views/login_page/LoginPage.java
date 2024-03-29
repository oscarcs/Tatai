package views.login_page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

import game.Game;
import game.User;
import views.main_container.MainContainer;
import views.welcome.Welcome;
import views.welcome.WelcomeView;

/**
 * Controller for login page.
 * @author szhu842, osim082
 */
public class LoginPage implements Initializable {

	@FXML
	ComboBox<String> usersBox;
	private MainContainer mainContainer;
	private ArrayList<String> users;

	/**
	 * Sets a main menu controller to set user information to.
	 * 
	 * @param mainContainer
	 */
	public void setMenu(MainContainer mainContainer) {
		this.mainContainer = mainContainer;
	}

	/**
	 * If the client decides to create a new user and hits the corresponding button.
	 */
	@FXML
	public void newUserHit() {
		// Create a dialog that accepts only unique usernames and a non-empty string.
		TextInputDialog dialog = new TextInputDialog("Username");
		dialog.setTitle("Create a new User");
		dialog.setHeaderText("Create a new User:");
		dialog.setContentText("Please enter your username:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			if (users.contains(result.get()) || result.get().equals("")) {

				// Create an alert to tell the use that the username is invalid.
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Username invalid!");
				alert.setHeaderText("Please enter a different username!");
				alert.setContentText(
						"This username already exists or the box is empty, " + "please enter a different one!");
				alert.showAndWait();
				return;
			}

			// If there has been a string passed in i.e. the user didn't cancel,
			// then pass the user to the mainContainer.
			User user = new User(result.get());
			mainContainer.setUser(user);

			WelcomeView welcomeView = new WelcomeView();
			MainContainer.instance().changeCenter(welcomeView);
			Welcome welcome = (Welcome) welcomeView.controller();
			welcome.setUp(user);
		}
	}

	/**
	 * Called when login button is pressed.
	 */
	@FXML
	public void loginHit() {
		// If client hasn't selected a user.
		if (usersBox.getSelectionModel().getSelectedItem() == null
				|| usersBox.getSelectionModel().getSelectedItem().equals("")) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No user selected!");
			alert.setHeaderText("Please select a user!");
			alert.setContentText("You not selected a user, please either select a user or create one.");
			alert.showAndWait();
			return;
		}

		// If the user has selected an account:
		String username = usersBox.getSelectionModel().getSelectedItem();
		User user = null;

		// Create the user object by parsing the output serialization file.
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new FileInputStream(MainContainer.USERS_DIRECTORY + username));
			user = (User) objectInputStream.readObject();
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// Set the user in the main program GUI state
		mainContainer.setUser(user);

		// Move to the welcome screen
		WelcomeView welcomeView = new WelcomeView();
		MainContainer.instance().changeCenter(welcomeView);
		Welcome welcome = (Welcome) welcomeView.controller();
		welcome.setUp(user);
	}

	/**
	 * Called when the delete button is pressed.
	 */
	@FXML
	public void deleteHit() {
		// If no user was selected.
		if (usersBox.getSelectionModel().getSelectedItem() == null
				|| usersBox.getSelectionModel().getSelectedItem().equals("")) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("No user selected!");
			alert.setHeaderText("Please select a user!");
			alert.setContentText("You not selected a user, please either select a user or create one.");
			alert.showAndWait();
			return;
		}

		// Get the selected account:
		String username = usersBox.getSelectionModel().getSelectedItem();

		// Create a dialog to confirm whether the account should be deleted:
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Warning");
		alert.setHeaderText("Deleting User");
		alert.setContentText("Are you sure you want to delete the user " + username + " ?");

		// Set the buttons:
		ButtonType typeYes = new ButtonType("Yes", ButtonData.YES);
		ButtonType typeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(typeYes, typeNo);

		// Open the alert:
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == typeYes) {

			// Delete file if user presses yes.
			File fileToDelete = new File(MainContainer.USERS_DIRECTORY + username);
			usersBox.getItems().remove(fileToDelete.getName());
			fileToDelete.delete();
		}
	}

	/**
	 * Check if the required directories exist and load the users into the ComboBox.
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Users directory:
		File usersDir = new File("users");
		if (!usersDir.exists()) {
			usersDir.mkdir();
		}

		// Sound directory:
		File soundDir = new File(Game.SOUND_DIR);
		if (!soundDir.exists()) {
			soundDir.mkdir();
		}
		// Quiz directory:
		File quizDir = new File("quiz");
		if (!quizDir.exists()) {
			quizDir.mkdir();
		}

		// Load in the users:
		users = new ArrayList<>();
		File[] usersFiles = usersDir.listFiles();
		for (File userFile : usersFiles) {
			usersBox.getItems().add(userFile.getName());
			users.add(userFile.getName());
		}
	}
}
