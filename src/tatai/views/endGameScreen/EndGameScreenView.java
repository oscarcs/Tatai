package tatai.views.endGameScreen;

import tatai.views.View;

/**
 * Represents the View of the EndGameScreen (with regards to MVC).
 * See {@link tatai.views.View}
 */
public class EndGameScreenView extends View{

    public EndGameScreenView() {
        super(EndGameScreen.class.getResource("endGameScreen.fxml"));
    }
}
