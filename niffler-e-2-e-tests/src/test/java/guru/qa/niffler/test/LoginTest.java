package guru.qa.niffler.test;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseWebTest {

    @Test
    void openSignUpInSignInPage() {
        new WelcomePage()
                .goToLoginPage()
                .goToRegisterPage()
                .registerPageShouldBeOpen();
    }

    @Test
    @DBUser(username = "test8", password = "12345")
//    @DBUser(username = "", password = "")
    void mainPageShouldBeVisibleAfterLogin(AuthUserEntity user) {
        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
    }
}
