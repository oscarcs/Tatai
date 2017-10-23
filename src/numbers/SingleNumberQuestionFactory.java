package numbers;

import java.util.Random;

/**
 * return a unique random number
 * 
 */
public class SingleNumberQuestionFactory implements QuestionFactory {

	private int max;
	private int min;

	public SingleNumberQuestionFactory() {
		max = 9;
		min = 1;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public Question generate() {
		int number = randomNumber(min, max);
		return new Question(number + "", number);
	}

	@Override
	public String asString() {
		return "Single Number";
	}

	/**
	 * Randomly generate a question between 'min' and 'max'.
	 * @param min lower limit
	 * @param max upper limit
	 * @return the number generated
	 */
	private int randomNumber(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min, max: " + max + "min :" + min);
		} 
		else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public void setMin(int min) {
		this.min = min;
	}
}
