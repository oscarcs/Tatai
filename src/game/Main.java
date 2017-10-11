package game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import views.main_container.MainContainerView;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) new MainContainerView().view();
		primaryStage.setTitle("T¨¡tai!");
		primaryStage.setScene(new Scene(root));

		// Load the icon.
		primaryStage.getIcons().add(new Image("resources/maori.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
