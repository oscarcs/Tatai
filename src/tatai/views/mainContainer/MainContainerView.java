package tatai.views.mainContainer;

import tatai.views.View;

/**
 * Represents the View of the MainContainer (with regards to MVC).
 * See {@link tatai.views.View}
 */
public class MainContainerView extends View {

    public MainContainerView() {
        super(MainContainer.class.getResource("mainContainer.fxml"));
    }

}
