package tatai.views.gameMenu;

import tatai.views.View;
/**
 * Represents the View of the GameMenu (with regards to MVC).
 * See {@link tatai.views.View}
 */
public class GameMenuView extends View {
    public GameMenuView() {
        super(GameMenu.class.getResource("gameMenu.fxml"));
    }
}
