package views.makeQuiz;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class MakeQuiz implements Initializable {

	@FXML
	Button previousBtn, nextBtn;

	@FXML
	TextField firstNum, secNum, operation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		firstNum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					firstNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		secNum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					secNum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}

		});

		operation.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[*/+-]") || newValue.length() > 1) {
					operation.setText(newValue.replaceAll("[^*/+-]", ""));
				}
			}

		});
		
		setTextLimit(firstNum,2);
		setTextLimit(secNum,2);
		setTextLimit(operation,1);

		firstNum.setFont(Font.font("System", 40));
		secNum.setFont(Font.font("System", 40));
		operation.setFont(Font.font("System", 40));

	}

	private void setTextLimit(TextField textfield, int length) {
		textfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (textfield.getText().length()>length) {
					textfield.setText(textfield.getText().substring(0, length));
				}
			}

		});
	}

}
