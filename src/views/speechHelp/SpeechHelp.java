package views.speechHelp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import views.help.HelpView;
import views.main_container.MainContainer;


public class SpeechHelp implements Initializable{

	@FXML
	Button help;
	
	@FXML
	public void helpHit() {
		MainContainer.instance().changeCenter(new HelpView());
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
