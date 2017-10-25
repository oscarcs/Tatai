package views.welcome;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import game.GameData;
import game.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class Welcome implements Initializable{

	@FXML
	Label name, text1, text2, score;
	
	private User user;
	private LocalDate date;
	
	
	
	public void setUp(User user) {
		name.setText(user.getUsername());
		score.setText("Score: " + user.getScore());

		date = LocalDate.now();
		ArrayList<LocalDate> userGameDate = new ArrayList<LocalDate>();
		
		if(user.getGames().size()==0) {
			text1.setText("How you doing Today?");
			text2.setText("Let's get Started, shall we?");
		} else {
		for(GameData data : user.getGames()) {
			userGameDate.add(data.getTime());
		}
		Collections.sort(userGameDate);
		
		if(date.equals(userGameDate.get(userGameDate.size()-1))) {
			text1.setText("Well Done, " + user.getUsername() + " !");
			text2.setText("You have complete your daily practice !");
		}else {
			text1.setText("You haven't done any practice today");
			text2.setText("Let's do some practice, Shall we ?");
		}
		
		}
	
	}
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}



}
