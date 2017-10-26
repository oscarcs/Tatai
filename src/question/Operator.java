package question;

/**
 * Maths operators
 */
public enum Operator {
	ADD("+"), 
	SUBTRACT("-"), 
	MULTIPLY("*"), 
	DIVIDE("/");

	private final String op;

	/**
	 * @param op The operator in Java operator form.
	 */
	Operator(String op) {
		this.op = op;
	}

	/**
	 * Return the operator as a 'java-style' operator.
	 */
	public String getOp() {
		return op;
	}

	/**
	 * Perform the actual calculation.
	 */
	public int getResult(int a, int b)
	{
		switch (op) {
			case "+":
				return a + b;

			case "-":
				return a - b;

			case "*":
				return a * b;

			case "/":
				return a / b;
		}
		return a;
	}

	/**
	 * Return the operator as a 'display' string, using the correct unicode character.
	 */
	@Override
	public String toString() {
		switch (op) {
			case "+":
				return "+";

			case "-":
				return "−";

			case "*":
				return "×";

			case "/":
				return "÷";
		}
		return "";
	}
}
