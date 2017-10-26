package question;

/**
 * Factory for practising a single question over and over.
 */
public class PracticeQuestionFactory extends QuestionFactory {

    private Question question;

	/**
	 * Constructor.
	 */
	public QuizQuestionFactory(Question question) {
        this.question = question;
	}

	/**
	 * Get the question.
	 */
	@Override
	public Question generate() {
        return question;
	}

	/**
	 * Return the name of the type of questions produced. 
	 */
	@Override
	public String toString() {
		return "Practise";
	}
}