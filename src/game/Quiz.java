package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Set;
import java.util.LinkedHashSet;

import question.Question;
import question.Operator;
import views.main_container.MainContainer;

/**
 * Function representing the data of a quiz.
 */
public class Quiz implements Serializable {

	private String name;
	private Set<Question> questions;

	public Quiz(String name) {
		this.name = name;
		this.questions = new LinkedHashSet<Question>();
	}

	/**
	 * Check that a given question is valid.
	 */
	public boolean checkQuestion(int first, int second, Operator operator) {

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

	/**
	 * Create a question from component parts.
	 */
	public Question createQuestion(int first, int second, Operator operator) {

		if (checkQuestion(first, second, operator)) {
			int result = operator.getResult(first, second);
			String questionText = first + " " + operator.toString() + " " + second;
			
			return new Question(questionText, result);
		}
		return null;
	}

	/**
	 * Add a question to the quiz.
	 */
	public void addQuestion(int first, int second, Operator operator) {
		Question question = createQuestion(first, second, operator);
		addQuestion(question);
	}

	/**
	 * Add a question to the quiz.
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}

	/**
	 * Get an individual question by question number.
	 */
	public Question getQuestion(int num) {
		int idx = 0;
		for (Question q : getQuestions()) {
			if (idx == num - 1) {
				return q;
			}
			idx++;
		}
		return null;
	}

	/**
	 * Get a set containting all of the questions in this quiz.
	 */
	public Set<Question> getQuestions() {
		return questions;
	}

	/**
	 * Get the total number of questions.
	 */
	public int getTotalQuestions() {
		return questions.size();
	}

	/**
	 * Get the name of this quiz.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Serialize this quiz.
	 */
	public void save() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new FileOutputStream(MainContainer.QUIZ_DIRECTORY + name)
			);
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
