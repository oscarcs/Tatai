package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import processes.ProcessOutput;
import numbers.Question;
import numbers.QuestionFactory;
import numbers.Pronunciation;
import views.level.Level;

/**
 * Class that is the object that represents a Game Model (with respect to MVC).
 */
public class Game {

    public static final String SOUND_FILE = "foo.wav";
    public static final String RECOUT_FILE = "recout.mlf";
    public static final String SOUND_DIR = ".";
    
    private QuestionFactory questionFactory;
    private int currentRound;
    private Pronunciation pronunciation;
    private Question currentQuestion;
    private int score = 0;
    private Level level;
    private int totalRounds;
    private GameData gameData;
    private String receivedAnswer;
    private SpeechRecognitionServiceFactory serviceFactory;
    private int currentAttempt;
    public HashMap<Integer, Boolean> roundCorrect;

    /**
     * Constructor.
     * @param questionFactory
     * @param rounds How many rounds the game will be.
     */
    public Game(QuestionFactory questionFactory, int rounds) {
        this.questionFactory = questionFactory;
        this.currentRound = 1;
        this.currentAttempt = 1;
        this.totalRounds = rounds;
        this.gameData = new GameData(questionFactory.asString(), rounds);
        this.roundCorrect = new HashMap<Integer, Boolean>();
        
        pronunciation = new Pronunciation();
        serviceFactory = new SpeechRecognitionServiceFactory();
        currentQuestion = this.questionFactory.generate();
    }

    /**
     * Turns the MLF file created by HTK to a string that usable by the game.
     * @return
     */
    public ArrayList<String> mlfToString() {
        ArrayList<String> wordsSpoken = new ArrayList<>();
        try {
            // Read the recout file
            File recoutFile = new File(SOUND_DIR + "/" + RECOUT_FILE);
            FileInputStream fileInputStream = new FileInputStream(recoutFile);
            byte[] data = new byte[(int) recoutFile.length()];
            fileInputStream.read(data);
            fileInputStream.close();
            String fullFile = new String(data, "UTF-8");
            String[] fileLines = fullFile.split("\n");

            // Remove the "sil" and the first two lines and the last line (see the for loop parameters).
            for (int i = 2; i < fileLines.length - 1; i++) {
                if (!fileLines[i].equals("sil")) {
                    wordsSpoken.add(fileLines[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordsSpoken;
    }

    /**
     * Process that is called by the Controller when the user presses the record button, on finishing it will call the Controller's recordingDone() function.
     */
    public void record() {
        Service<ProcessOutput> recordService = serviceFactory.makeService(SOUND_DIR,SpeechRecognitionServiceFactory.RECORD, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                level.recordingDone();
            }
        });
        recordService.start();
    }

    /**
     * Process that called by the Controller, it will parse the sound file "foo.wav" and create a MLF file. On finishing it will called processingDone() on 'this'.
     */
    public void process() {
        Service<ProcessOutput> processService = serviceFactory.makeService(SOUND_DIR,SpeechRecognitionServiceFactory.PROCESS, new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                processingDone();
            }
        });
        processService.start();
    }

    /**
     * Process that is called when processing (creation of MLF file) is finished. 
     * It checks the MLF file and decides on the next action based on whether the user 
     * answered the question correctly and the current state of the game.
     */
    private void processingDone() {
        boolean answerCorrect = checkAnswer();
        if (answerCorrect) {
            winRound();
        } else if (currentAttempt >= 2) {
            loseRound();
        } else {
            currentAttempt++;
            level.failedAttempt();
        }

        // Delete the created files.
        File soundDir = new File(SOUND_DIR);
        for (File file : soundDir.listFiles()) {
            if (file.getName().equals(RECOUT_FILE) || file.getName().equals(SOUND_FILE)) {
                file.delete();
            }
        }

        // Tell the controller that processing is now done.
        level.processingDone();
    }

    /**
     * Method that checks the parsed user input against the actual answer and returns whether they got it right or wrong.
     * @return
     */
    private boolean checkAnswer() {
        ArrayList<String> lines = mlfToString();
        String userAnswer = "";
        for (String line : lines) {
            userAnswer += " " + line;
            System.out.println(line);
        }
        receivedAnswer = userAnswer;
        String answer = pronunciation.getPronunciation(currentQuestion.answer());
        List<String> answers = Arrays.asList(answer.split(" "));
        if (lines.containsAll(answers)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method that is called when the user has gotten the correct answer.
     */
    public void winRound() {
        score++;
        gameData.addRound(currentRound, true, receivedAnswer + "", pronunciation.getPronunciation(currentQuestion.answer())+ "", currentQuestion.toString(), currentAttempt);
        level.answerCorrect();
        roundCorrect.put(currentRound, true);
        endRound();
    }

    /**
     * Method that is called when the user has gotten the wrong answer twice.
     */
    private void loseRound() {
        gameData.addRound(currentRound, false, receivedAnswer + "", pronunciation.getPronunciation(currentQuestion.answer())+ "", currentQuestion.toString(), currentAttempt);
        level.answerWrong();
        roundCorrect.put(currentRound, false);
        endRound();
    }

    /**
     * Method that is called when the current round is over.
     */
    private void endRound() {
        currentAttempt = 1;
        currentRound++;
        currentQuestion = questionFactory.generate();
        level.setRoundColour(roundCorrect);
        if (currentRound <= totalRounds) {
            level.nextLevel();
        } else {
            level.endGame();
        }
    }
    
    public HashMap<Integer,Boolean> getRoundData(){
    	return roundCorrect;
    }
    
    public void setColour() {
    	level.setRoundColour(roundCorrect);
    }

    /**
     * Gets the current round number.
     * @return
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Gets a string representation of the score in the format "SCORE/ROUNDS"
     * @return
     */
    public String getScoreAsString() {
        return score + " / " + totalRounds;
    }

    /**
     * Gets the score as an int
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the GameData which is the saved data regarding how the user did on this game.
     * @return
     */
    public GameData gameData() {
        return gameData;
    }

    /**
     * Gets the game type essentially referring to which question factory was used.
     * @return
     */
    public String gameTypeName() {
        return questionFactory.asString();
    }

    /**
     * Gets the current attempt that the user is on (how many tries they've had at this question.
     * @return
     */
    public int getCurrentAttempt() {
        return currentAttempt;
    }

    /**
     * Gets the received answer (the string is the result of the user's answer).
     * @return
     */
    public String getReceivedAnswer() {
        return receivedAnswer;
    }

    /**
     * Set a level controller.
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Gets the question that the user will be asked in a String form.
     * @return
     */
    public String questionText() {
        return currentQuestion.toString();
    }
}
