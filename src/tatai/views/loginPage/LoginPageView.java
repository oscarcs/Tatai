package tatai.views.loginPage;

import tatai.views.View;

/**
 * Represents the View of the LoginPage (with regards to MVC).
 * See {@link tatai.views.View}
 */
public class LoginPageView extends View {

    public LoginPageView() {
        super(LoginPage.class.getResource("loginPage.fxml"));
    }
}
