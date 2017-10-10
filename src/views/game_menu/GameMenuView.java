package views.game_menu;

import views.View;
/**
 * Represents the View of the GameMenu (with regards to MVC).
 * See {@link views.View}
 */
public class GameMenuView extends View {
    public GameMenuView() {
        super(GameMenu.class.getResource("gameMenu.fxml"));
    }
}
