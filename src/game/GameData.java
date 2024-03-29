package game;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Represents the saved data of a completed game.
 * @author szhu842, osim082
 */
public class GameData implements Serializable{
    private HashSet<RoundData> rounds;
    private LocalDate time;
    private int totalRounds = 0;
    private int score = 0;
    private double scoreAsPercentage = 0;
    private String gameType;
    private int maxNumber;
    public LocalDate getTime() {
        return time;
    }

    /**
     * Constructor, takes the game type of the game and the max number that was selected by the user.
     * @param gameType
     * @param maxNumber
     */
    public GameData(String gameType, int maxNumber) {
        rounds = new HashSet<>();
        time = LocalDate.now();
        this.gameType = gameType;
        this.maxNumber = maxNumber;
    }

    /**
     * Add the information from a round.
     * @param roundNumber
     * @param correct
     * @param userAnswer
     * @param correctAnswer
     * @param question
     * @param attempts
     */
    public void addRound(int roundNumber, boolean correct, String userAnswer, 
        String correctAnswer, String question, int attempts
    ) {
        totalRounds++;
        if (correct) {
            score++;
        }
        
        // Calculate the score as a %
        scoreAsPercentage = (((double) score) / ((double) totalRounds)) * 100;
        
        rounds.add(new RoundData(
            roundNumber, 
            correct, 
            userAnswer, 
            correctAnswer, 
            question, 
            attempts
        ));
    }

    /**
     * Sets the 'time' field, which represents when the game was played. 
     * @param time
     */
    public void setTime(LocalDate time) {
        this.time = time;
    }

    /**
     * Returns the game type that was played. 
     * it is used when the game checks whether the user is allowed to proceed 
     * to level 2 of a given game type.
     * @return
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Set the game type that was played.
     * @param gameType
     */
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    /**
     * Get the max number that was selected by the user before the game started. 
     * @return
     */
    public int getMaxNumber() {
        return maxNumber;
    }

    /**
     * Set hte maximum number that the game was allowed to ask questions up to. 
     * @param maxNumber
     */
    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * Gets the individual round data.
     * @return
     */
    public HashSet<RoundData> rounds() {
        return rounds;
    }

    /**
     * Get the total rounds that were played. 
     * @return
     */
    public int getTotalRounds() {
        return totalRounds;
    }

    /**
     * Set the field that represents the total rounds that were played. 
     * @param totalRounds
     */
    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    /**
     * Gets the score that the user achieved during the game.
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets teh score that was achieved by the user during the game. Necessary for TableView and testing purposes.
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the score as percentage that the user achieved.
     * @return
     */
    public double getScoreAsPercentage() {
        return scoreAsPercentage;
    }

    /**
     * Sets the score as percentage that as achieved by the user.
     * @param scoreAsPercentage
     */
    public void setScoreAsPercentage(double scoreAsPercentage) {
        this.scoreAsPercentage = scoreAsPercentage;
    }
}
