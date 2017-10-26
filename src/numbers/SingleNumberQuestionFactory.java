package numbers;

/**
 * Creates questions that are just a single number.
 */
public class SingleNumberQuestionFactory extends QuestionFactory {

	/**
	 * Constructor.
	 */
	public SingleNumberQuestionFactory() {

	}

	/**
	 * Generate a question, which just consists of a single number.
	 */
	@Override
	public Question generate() {
		int number = randomNumber(min, max);
		return new Question(number + "", number);
	}

	/**
	 * Return the name of the type of questions produced. 
	 */
	@Override
	public String toString() {
		return "Single Number";
	}
}
