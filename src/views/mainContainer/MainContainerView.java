package views.mainContainer;

import views.View;

/**
 * Represents the View of the MainContainer (with regards to MVC).
 * See {@link views.View}
 */
public class MainContainerView extends View {

    public MainContainerView() {
        super(MainContainer.class.getResource("mainContainer.fxml"));
    }

}
