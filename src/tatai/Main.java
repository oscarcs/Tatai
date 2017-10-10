package tatai;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import views.mainContainer.MainContainerView;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) new MainContainerView().view();
		primaryStage.setTitle("Tātai!");
		primaryStage.setScene(new Scene(root));

		//Load the icon.
		primaryStage.getIcons().add(new Image("resources/images/maori.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
