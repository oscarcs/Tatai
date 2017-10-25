package game;

import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import numbers.Question;
import views.make_quiz.MakeQuiz;

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

	public void addQuiz(SingleQuiz question) {
		data.addQues(question);
	}
	
	public int getCurrentQuizNum() {
		return currentQuestion;
	}

	public void setQuiz(MakeQuiz quiz) {
		this.quiz = quiz;
	}

	public boolean checkValid(int first, int second, String operation) {

		boolean valid = true;
		int result = 0;

		switch (operation) {
			case "+":
				result = first + second;
				break;

			case "-":
				result = first - second;
				break;

			case "/":
				valid = first % second == 0; 
				result = first / second;
				break;

			case "*":
				result = first * second;
				break;
		}

		if (result > 99 || result <= 0) {
			valid = false;
		}

		return valid;
	}

	public SingleQuiz getQuestion(int first, int second, String operation) {

		int result = 0;
		String operationString = "";

		switch (operation) {
			case "+":
				result = first + second;
				operationString = " + ";
				break;

			case "-":
				result = first - second;
				operationString = " − ";
				break;

			case "/":
				result = first / second;
				operationString = " ÷ ";
				break;

			case "*":
				result = first * second;
				operationString = " × ";
				break;
		}

		SingleQuiz question = new SingleQuiz(
			currentQuestion, 
			first + operationString + second, 
			result
		);

		return question;
	}

}
