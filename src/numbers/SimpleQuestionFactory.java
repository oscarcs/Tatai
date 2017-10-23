package numbers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class returns a unique simple question genereted s
 */
public class SimpleQuestionFactory implements QuestionFactory {
	private int firstNumber;
	private int secondNumber;
	private Operator operator;
	private int answer;
	private int max;
	private int min;

	/**
	 * return the string "Simple Questions"
	 */
	public String asString() {
		return "Simple Questions";
	}

	/**
	 * default constructor
	 */
	public SimpleQuestionFactory() {

	}

	/**
	 * constructor of the class
	 * 
	 * @param max
	 *            the maximum value the question would take
	 * @param firstNumber
	 *            the value of the first number
	 */
	public SimpleQuestionFactory(int max, int firstNumber) {
		this.firstNumber = firstNumber;
		this.max = max;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * generate the unique question, the operation it can take is add, divide,
	 * minus, and multiply
	 */
	private void createQuestion() {
		operator = generateOperator();
		if (operator == Operator.ADD) {
			firstNumber = randomNumber(1, max);
			answer = randomNumber(firstNumber, max);
			secondNumber = answer - firstNumber;
		} else if (operator == Operator.DIVIDE) {
			firstNumber = randomNumber(1, max);
			ArrayList<Integer> factors = getFactors(firstNumber);
			int factorSeed = randomNumber(0, factors.size() - 1);
			secondNumber = factors.get(factorSeed);
			answer = firstNumber / secondNumber;
		} else if (operator == Operator.MINUS) {
			firstNumber = randomNumber(1, max);
			answer = randomNumber(1, firstNumber);
			secondNumber = firstNumber - answer;
		} else if (operator == Operator.MULTIPLY) {
			answer = randomNumber(1, max);
			ArrayList<Integer> factors = getFactors(answer);
			int factorSeed = randomNumber(0, factors.size() - 1);
			firstNumber = factors.get(factorSeed);
			secondNumber = answer / firstNumber;
		}
	}

	/**
	 * return a question generated, in type Question
	 */
	public Question generate() {
		createQuestion();
		return new Question(firstNumber + operator.toString() + secondNumber, answer);
	}

	/**
	 * randomly generate the operator used for the question
	 * 
	 * @return the operator
	 */
	public Operator generateOperator() {
		int seed = randomNumber(1, 4);
		for (Operator operator : Operator.values()) {
			if (seed == operator.getSeed()) {
				return operator;
			}
		}
		throw new RuntimeException("Error in generating an operator");
	}

	/**
	 * randomly generate a number between the limits
	 * 
	 * @param min
	 *            the minimum value it would take
	 * @param max
	 *            the maximum value it would take
	 * @return the number generated
	 */
	public int randomNumber(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("max must be greater than min, max: " + max + "min :" + min);
		} else if (min == max) {
			return min;
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	/**
	 * calculate all the factors of a number
	 * 
	 * @param n
	 *            the number that wants to find factors from
	 * @return the factor of the number
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

	@Override
	public void setMin(int min) {
		this.min = min;
	}
}
