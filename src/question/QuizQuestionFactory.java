package question;

import game.Quiz;

/**
 * Use the questions from a saved quiz
 */
public class QuizQuestionFactory extends QuestionFactory {

    private Quiz quiz;
    private int questionNumber = 1;

    /**
    * Constructor.
    */
    public QuizQuestionFactory(Quiz quiz) {
        this.quiz = quiz;
    }

    /**
    * Get a question from the Quiz. 
    */
    @Override
    public Question generate() {
        Question question = quiz.getQuestion(questionNumber);

        questionNumber++;
        return question;
    }

    /**
    * Return the name of the type of questions produced. 
    */
    @Override
    public String toString() {
        return "Quiz";
    }
}