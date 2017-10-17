package views.makeQuiz;

import java.net.URL;
import java.util.ResourceBundle;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import numbers.Equation;

public class MakeQuiz implements Initializable {

	@FXML
	Button previousBtn, nextBtn;

	@FXML
	TextField firstNum, secNum, operation;

	@FXML
	Label questionNum, valid;

	@FXML
	public void nextHit() {
		if (checkValid()) {
			valid.setText("Valid!");
			Equation equation = getEquation();

		} else {
			valid.setText("Invalid!");
		}
	}

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

		setTextLimit(firstNum, 2);
		setTextLimit(secNum, 2);
		setTextLimit(operation, 1);

		firstNum.setFont(Font.font("System", 40));
		secNum.setFont(Font.font("System", 40));
		operation.setFont(Font.font("System", 40));

		valid.setText("");

	}

	private void setTextLimit(TextField textfield, int length) {
		textfield.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (textfield.getText().length() > length) {
					textfield.setText(textfield.getText().substring(0, length));
				}
			}

		});
	}

	private boolean checkValid() {
		int first = Integer.parseInt(firstNum.getText());
		int second = Integer.parseInt(secNum.getText());

		System.out.println(firstNum.getText() + operation.getText() + secNum.getText());

		if (operation.getText().equals("/")) {
			if (first % second != 0) {
				return false;
			} else {
				return true;
			}
		} else {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			int result = 0;
			try {
				result = (int) engine.eval(first + operation.getText() + second);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
			if (result > 99 || result <= 0) {
				return false;
			} else {
				return true;
			}
		}

	}
	
	private Equation getEquation() {
		int first = Integer.parseInt(firstNum.getText());
		int second = Integer.parseInt(secNum.getText());
		
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		int result = 0;
		try {
			result = (int) engine.eval(first + operation.getText() + second);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		Equation equation = new Equation(first + operation.getText() + second, result);

		return equation;
		
	}

}
