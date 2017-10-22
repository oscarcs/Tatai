package game;

import java.io.Serializable;

import numbers.Equation;

public class SingleQuiz implements Comparable<SingleQuiz>, Serializable{
	
	private int quizNum;
	private String representation;
	private int answer;

	
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
		
		this.quizNum = quizNum;
		this.representation = representation;
		this.answer = answer;
		
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
