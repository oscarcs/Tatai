package views.game_end;

import views.View;

/**
 * Represents the View of the GameEnd (with regards to MVC).
 */
public class GameEndView extends View{

    public GameEndView() {
        super(GameEnd.class.getResource("gameEnd.fxml"));
    }
}
