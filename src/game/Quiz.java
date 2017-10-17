package game;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import numbers.Equation;

public class Quiz {
	
	private int totalQuestions;
	private int currentQuestion;
	private HashMap<Integer, Equation> questions;
	private int first, second;
	private String operation;
	

	public Quiz(int questions) {
		totalQuestions = questions;
		currentQuestion = 1;
		
		
		
	}
	
	private boolean checkValid() {
	

//		System.out.println(firstNum.getText() + operation.getText() + secNum.getText());

		if (operation.equals("/")) {
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
				result = (int) engine.eval(first + operation + second);
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
	
	
	
}
