    package views.login_page;

import views.View;

/**
 * Represents the View of the LoginPage (with regards to MVC).
 * See {@link views.View}
 */
public class LoginPageView extends View {

    public LoginPageView() {
        super(LoginPage.class.getResource("loginPage.fxml"));
    }
}
