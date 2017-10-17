package game;

import java.io.Serializable;
import java.util.HashSet;

import numbers.Equation;

public class QuizData implements Serializable{
	
	
	private HashSet<Equation> question;
	private int totalQues;
	
	public QuizData(int questions) {
		totalQues = questions;
		question = new HashSet<Equation>();
		
	}
	
	public void addQues(String equation, int answer) {
		question.add(new Equation(equation, answer));
	}
	
	 public HashSet<Equation> getQuestion() {
	        return question;
	    }
	 
	 public int getTotalQues() {
		 return totalQues;
	 }
	
}
