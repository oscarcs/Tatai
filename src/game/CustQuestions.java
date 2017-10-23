package game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import numbers.Question;
import views.main_container.MainContainer;

public class CustQuestions implements Serializable{
	
	String name;
	ArrayList<Question> questions;
	
	public CustQuestions(String quesName) {
		name = quesName;
		questions = new ArrayList<Question>();
	}
	
	public void addQuiz(Question ques) {
		questions.add(ques);
	}
	
	public ArrayList<Question> getQuiz(){
		return questions;
	}
	
	public String getName() {
		return name;
	}
	
	public void save() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(MainContainer.QUIZ_DIRECTORY + name));
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
