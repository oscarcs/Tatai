package views.end_game_screen;

import views.View;

/**
 * Represents the View of the EndGameScreen (with regards to MVC).
 * See {@link views.View}
 */
public class EndGameScreenView extends View{

    public EndGameScreenView() {
        super(EndGameScreen.class.getResource("endGameScreen.fxml"));
    }
}
