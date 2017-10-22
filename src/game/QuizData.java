package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

import views.main_container.MainContainer;

public class QuizData implements Serializable {

	private String name;
	private HashSet<SingleQuiz> question;
	private int totalQues;

	public QuizData(String name) {
		this.name = name;
		totalQues = 10;
		question = new HashSet<SingleQuiz>();

	}

	public void addQues(SingleQuiz quiz) {
		question.add(quiz);
	}

	public HashSet<SingleQuiz> getQuestion() {
		return question;
	}

	public SingleQuiz getTheQuiz(int num) {
		for (SingleQuiz q : getQuestion()) {
			if (q.getQuizNum() == num) {
				return q;
			}
		}
		return null;
	}
	
	
	public int getTotalQues() {
		return totalQues;
	}

	public String getName() {
		return name;
	}

	public void save() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new FileOutputStream(MainContainer.QUIZ_DIRECTORY + name));
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
