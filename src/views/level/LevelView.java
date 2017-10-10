package views.level;

import views.View;

/**
 * Represents the View of the Level (with regards to MVC).
 * See {@link views.View}
 */
public class LevelView extends View{

    public LevelView() {
        super(Level.class.getResource("level.fxml"));
    }
}
