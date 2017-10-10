package views.help;

import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import views.main_container.MainContainer;
import views.speechHelp.SpeechHelpView;

public class Help implements Initializable {

	@FXML
	Button pronunciation;

	@FXML
	public void proHit() {
		MainContainer.instance().changeCenter(new SpeechHelpView());
	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
