package numbers;

/**
 * Class that represents a question in the game.
 */
public class Question {
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
	 * String representation of question used by game in GUI.
	 */
	@Override
	public String toString() {
		return representation;
	}

	/**
	 * Returns the correct answer to the question. 
	 */
	public int answer() {
		return answer;
	}
}
