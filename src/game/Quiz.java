package game;

import java.util.HashMap;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import numbers.Question;
import numbers.Operator;
import views.make_quiz.MakeQuiz;

public class Quiz {

	private int totalQuestions;
	private int currentQuestion;
	private QuizData data;
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

	public boolean checkValid(int first, int second, Operator operator) {

		boolean valid = true;
		int result = operator.getResult(first, second);

		if (operator == Operator.DIVIDE) {
			valid = first % second == 0;
		}

		if (result > 99 || result <= 0) {
			valid = false;
		}

		return valid;
	}

	public SingleQuiz getQuestion(int first, int second, Operator operator) {

		int result = operator.getResult(first, second);

		SingleQuiz question = new SingleQuiz(
			currentQuestion, 
			first + " " + operator.toString() + " " + second, 
			result
		);

		return question;
	}
}
