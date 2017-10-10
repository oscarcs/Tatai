package views.game_info;

import views.View;

/**
 * Represents the View of the GameInfo (with regards to MVC).
 * See {@link views.View}
 */
public class GameInfoView extends View{

    public GameInfoView() {
        super(GameInfo.class.getResource("gameInfo.fxml"));
    }
}
