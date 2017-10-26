package numbers;

import java.util.ArrayList;

/**
 * Factory to create simple questions 
 */
public class SimpleQuestionFactory extends QuestionFactory {

	/**
	 * Default constructor.
	 */
	public SimpleQuestionFactory() {

	}

	/**
	 * Create a new SimpleQuestionFactory.
	 * @param max Maximum value of the question.
	 * @param firstNumber Value of the first number.
	 */
	public SimpleQuestionFactory(int min, int max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * 
	 */
	public Question generate() {
		
		Operator operator = generateOperator();
		int firstNumber = 0;
		int secondNumber = 0;
		int answer = 0;

		switch (operator) {

			case ADD:
				{
					firstNumber = randomNumber(1, max);
					answer = randomNumber(firstNumber, max);
					secondNumber = answer - firstNumber;
				}
				break;

			case SUBTRACT:
				{
					firstNumber = randomNumber(1, max);
					answer = randomNumber(1, firstNumber);
					secondNumber = firstNumber - answer;
				}
				break;

			case MULTIPLY:
				{
					answer = randomNumber(1, max);
					ArrayList<Integer> factors = getFactors(answer);
					int factorSeed = randomNumber(0, factors.size() - 1);
					firstNumber = factors.get(factorSeed);
					secondNumber = answer / firstNumber;
				}
				break;
				
			case DIVIDE:
				{
					firstNumber = randomNumber(1, max);
					ArrayList<Integer> factors = getFactors(firstNumber);
					int factorSeed = randomNumber(0, factors.size() - 1);
					secondNumber = factors.get(factorSeed);
					answer = firstNumber / secondNumber;
				}
				break;
		}
		
		return new Question(firstNumber + " " + operator.toString() + " " + secondNumber, answer);
	}

	/**
	 * randomly generate the operator used for the question
	 * 
	 * @return the operator
	 */
	public Operator generateOperator() {
		int index = randomNumber(0, 3);
		return Operator.values()[index];
	}

	/**
	 * Calculate the factors of a number.
	 * @param n The number that wants to find factors from.
	 * @return The factor of the number.
	 */
	public ArrayList<Integer> getFactors(int n) {
		ArrayList<Integer> factors = new ArrayList<>();
		for (int i = 1; i <= n; i++) {
			if (n % i == 0) {
				factors.add(i);
			}
		}
		return factors;
	}

	/**
	 * Return the name of the type of questions produced. 
	 */
	public String toString() {
		return "Simple Questions";
	}
}
