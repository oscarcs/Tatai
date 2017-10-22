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
import numbers.Equation;
import numbers.Pronunciation;
import processes.ProcessOutput;
import views.play_quiz.PlayQuiz;

public class QuizGame {

	 public static final String SOUND_FILE = "foo.wav";
	    public static final String RECOUT_FILE = "recout.mlf";
	    public static final String SOUND_DIR = ".";
	    private int currentRound;
	    private Pronunciation pronunciation;
	    private Equation currentEquation;
	    private int score = 0;
	    private PlayQuiz playQuiz;
	    private int totalRounds;
	    private GameData gameData;
	    private String receivedAnswer;
	    private SpeechRecognitionServiceFactory serviceFactory;
	    private int currentAttempt;
	    public HashMap<Integer, Boolean> roundCorrect;
	    private QuizData data;

	    /**
	     * Constructor, it takes the equationFactory that will be used and how many rounds the game will be.
	     * @param equationFactory
	     * @param rounds
	     */
	    public QuizGame(QuizData data) {
	        this.currentRound = 1;
	        this.currentAttempt = 1;
	        this.totalRounds = data.getTotalQues();
	        this.gameData = new GameData("Quiz: " + data.getName(), data.getTotalQues());
	        pronunciation = new Pronunciation();
	        currentEquation = data.getTheQuiz(currentRound);
	        serviceFactory = new SpeechRecognitionServiceFactory();
	        this.roundCorrect = new HashMap<Integer, Boolean>();
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
	                playQuiz.recordingDone();
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
	     * Process that is called when processing (creation of MLF file) is finished. It checks the MLF file and decides on the next action based of whether
	     * the user answer the question correctly and the current state of the game.
	     */
	    private void processingDone() {
	        boolean answerCorrect = checkAnswer();
	        if (answerCorrect) {
	            winRound();
	        } else if (currentAttempt >= 2) {
	            loseRound();
	        } else {
	            currentAttempt++;
	            playQuiz.failedAttempt();
	        }

	        // Delete the created files.
	        File soundDir = new File(SOUND_DIR);
	        for (File file : soundDir.listFiles()) {
	            if (file.getName().equals(RECOUT_FILE) || file.getName().equals(SOUND_FILE)) {
	                file.delete();
	            }
	        }

	        // Tell the controller that processing is now done.
	        playQuiz.processingDone();
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
	        String answer = pronunciation.getPronunciation(currentEquation.answer());
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
	        gameData.addRound(currentRound, true, receivedAnswer + "", pronunciation.getPronunciation(currentEquation.answer())+ "", currentEquation.toString(), currentAttempt);
	        playQuiz.answerCorrect();
	        roundCorrect.put(currentRound, true);
	        endRound();

	    }

	    /**
	     * Method that is called when the user has gotten the wrong answer twice.
	     */
	    private void loseRound() {
	        gameData.addRound(currentRound, false, receivedAnswer + "", pronunciation.getPronunciation(currentEquation.answer())+ "", currentEquation.toString(), currentAttempt);
	        playQuiz.answerWrong();
	        roundCorrect.put(currentRound, false);
	        endRound();
	    }

	    /**
	     * Method that is called when the current round is over.
	     */
	    private void endRound() {
	        currentAttempt = 1;
	        currentRound++;
	        currentEquation = data.getTheQuiz(currentRound);
	        playQuiz.setRoundColour(roundCorrect);
	        if (currentRound <= totalRounds) {
	            playQuiz.nextQuiz();
	        } else {
	            playQuiz.endGame();
	        }
	    }
	    
	    public HashMap<Integer,Boolean> getRoundData(){
	    	return roundCorrect;
	    }
	    
	    public void setColour() {
	    	playQuiz.setRoundColour(roundCorrect);
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
	     * Gets the game type essentially referring to which equation factory was used.
	     * @return
	     */
	    public String gameTypeName() {
	        return "Quiz: " + data.getName();
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
	    public void setPlayQuiz(PlayQuiz playQuiz) {
	        this.playQuiz = playQuiz;
	    }

	    /**
	     * Gets the question that the user will be asked in a String form.
	     * @return
	     */
	    public String equationText() {
	        return currentEquation.toString();
	    }
	
}
