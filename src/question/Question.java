package question;

import java.io.Serializable;

/**
 * Class that represents a question in the game.
 */
public class Question implements Serializable {
	
	protected String representation;
	protected int answer;

	/**
	 * Constructor.
	 * @param representation The string that will be shown on the GUI
	 * @param answer The correct answer
	 */
	public Question(String representation, int answer) {
		this.representation = representation;
		this.answer = answer;
	}

	/**
	 * Returns the correct answer to the question. 
	 */
	public int getAnswer() {
		return answer;
	}

	/**
	 * Fallback for PropertyValueFactory
	 */
	public String getRepresentation() {
		return toString();
	}

	/**
	 * String representation of question used by game in GUI.
	 */
	@Override
	public String toString() {
		return representation;
	}
}
