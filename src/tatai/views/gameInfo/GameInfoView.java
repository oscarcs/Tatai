package tatai.views.gameInfo;

import tatai.views.View;

/**
 * Represents the View of the GameInfo (with regards to MVC).
 * See {@link tatai.views.View}
 */
public class GameInfoView extends View{

    public GameInfoView() {
        super(GameInfo.class.getResource("gameInfo.fxml"));
    }
}
