package views.welcome;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import game.GameData;
import game.User;

/**
 * This is the 'welcome screen', essentially the home screen of the app.
 */
public class Welcome implements Initializable {

	@FXML
	Label name, text1, text2;
	
	private User user;
	private LocalDate date;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setUp(User user) {
		
		// Greet the user.
		name.setText("Welcome to Tatai, " + user.getUsername() + "!");

		
		date = LocalDate.now();
		ArrayList<LocalDate> userGameDate = new ArrayList<LocalDate>();
		
		if (user.getGames().size() == 0) {
			text1.setText("How are you doing today?");
			text2.setText("Let's get started, shall we?");
		} 
		else {
			for (GameData data : user.getGames()) {
				userGameDate.add(data.getTime());
			}
			Collections.sort(userGameDate);
			
			if (date.equals(userGameDate.get(userGameDate.size() - 1))) {
				text1.setText("Well done, " + user.getUsername() + "!");
				text2.setText("You have completed your daily practice!");
			}
			else {
				text1.setText("You haven't done any practice today.");
				text2.setText("Shall we do some?");
			}
		
		}
		
	}
}
