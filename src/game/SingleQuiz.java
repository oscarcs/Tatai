package game;

import java.io.Serializable;

import numbers.Equation;

public class SingleQuiz extends Equation implements Serializable{
	
	private int quizNum;

	
	
	
	public SingleQuiz(int quizNum, String representation, int answer) {
		super(representation, answer);
		this.quizNum = quizNum;
		
	}

	public int quizNumber() {
		return quizNum;
	}
	
}
