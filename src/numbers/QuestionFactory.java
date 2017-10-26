package numbers;

import java.util.Random;

/**
 * 
 */
public abstract class QuestionFactory {

	// Maximum and minimum values for each question:
	protected int min = 1;
	protected int max = 9;

	/**
	 * Generate a question
	 */
	public abstract Question generate();

	/**
	 * The lower limit of the range of values to generate.
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * The upper limit of the range of values to generate.
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * Randomly generate a number within a range.
	 * @param min Minimum value
	 * @param max Maximum value
	 * @return Random number.
	 */
	public int randomNumber(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException(
				"max must be greater than min, max: " + max + "min :" + min
			);
		} 
		else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
}
