package game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import views.main_container.MainContainerView;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = (Parent) new MainContainerView().view();

		stage.setTitle("Tatai!");
		stage.setMinWidth(750);
		stage.setMinHeight(500);

		stage.setScene(new Scene(root));

		// Load the icon.
		stage.getIcons().add(new Image("resources/maori.png"));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
