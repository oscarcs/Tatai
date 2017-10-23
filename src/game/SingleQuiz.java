package game;

import java.io.Serializable;

import numbers.Question;

public class SingleQuiz extends Question implements Comparable<SingleQuiz>, Serializable {
	
	private int quizNum;

	
	   @Override
	    public int compareTo(SingleQuiz o) {
	        if (quizNum == o.quizNum) {
	            return 0;
	        } else if (this.quizNum < o.quizNum) {
	            return 1;
	        } else {
	            return -1;
	        }
	    }
	
	public SingleQuiz(int quizNum, String representation, int answer) {
		super(representation, answer);
		this.quizNum = quizNum;
		
	}

	public int getQuizNum() {
		return quizNum;
	}
	
	public int getAnswer() {
		return answer;
	}
	
	public String getRepresentation() {
		return representation;
	}
	
}
