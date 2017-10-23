package game;

import java.io.Serializable;

/**
 * This class represents a saved round.
 */
public class RoundData implements Comparable<RoundData>, Serializable{
    private int roundNumber;
    private boolean correct;
    private String userAnswer;
    private String correctAnswer;
    private int attempts;
    private String question;

    /**
     * Constructor that includes all the parameters that are needed to comprehensively save all the data from a round.
     * @param roundNumber
     * @param correct
     * @param userAnswer
     * @param correctAnswer
     * @param question
     * @param attempts
     */
    public RoundData(int roundNumber, boolean correct, String userAnswer, String correctAnswer, String question, int attempts) {
        this.roundNumber = roundNumber;
        this.correct = correct;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.question = question;
        this.attempts = attempts;

    }

    /**
     * Used for sorting during tableView, will sort RoundData by roundNumber.
     * @param o
     * @return
     */
    @Override
    public int compareTo(RoundData o) {
        if (roundNumber == o.roundNumber) {
            return 0;
        } else if (this.roundNumber < o.roundNumber) {
            return 1;
        } else {
            return -1;
        }
    }

    // Below are setters and getters that are used by the TableView.

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
