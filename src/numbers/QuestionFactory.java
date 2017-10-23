package numbers;

/**
 * 
 */
public interface QuestionFactory {

	/**
	 * Generate a question
	 */
	Question generate();

	/**
	 * String representation, essentially "game type".
	 */
	String asString();

	/**
	 * The upper limit of the range of values to generate.
	 */
	void setMax(int max);

	/**
	 * The lower limit of the range of values to generate.
	 */
	void setMin(int min);
}
