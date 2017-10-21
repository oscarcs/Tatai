package game;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import numbers.Equation;
import views.makeQuiz.MakeQuiz;

public class Quiz {

	private int totalQuestions;
	private int currentQuestion;
	private QuizData data;
	private int first, second;
	private String operation;
	private MakeQuiz quiz;

	public Quiz(String name) {
		totalQuestions = 10;
		currentQuestion = 1;
		data = new QuizData(name);

	}
	
	public void nextQuizNum() {
		currentQuestion++;
	}

	public QuizData getQuizData() {
		return data;
	}

	public void setQuizfile(QuizData data) {
		this.data = data;
	}

	public void addQuiz(SingleQuiz equation) {
		data.addQues(equation);
	}

	public SingleQuiz getTheQuiz(int num) {
		for (SingleQuiz q : data.getQuestion()) {
			if (q.getQuizNum() == num) {
				return q;
			}
		}
		return null;
	}

	public int getCurrentQuizNum() {
		return currentQuestion;
	}

	public void setQuiz(MakeQuiz quiz) {
		this.quiz = quiz;
	}

	public boolean checkValid(int first, int second, String operation) {

		// System.out.println(firstNum.getText() + operation.getText() +
		// secNum.getText());

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

	public SingleQuiz getEquation(int first, int second, String operation) {

		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		int result = 0;
		try {
			result = (int) engine.eval(first + operation + second);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		SingleQuiz question = new SingleQuiz(currentQuestion, first + operation + second, result);

		return question;

	}

}
