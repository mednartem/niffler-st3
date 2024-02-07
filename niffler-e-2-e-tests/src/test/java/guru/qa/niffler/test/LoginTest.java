package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.ApiLogin;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.page.MainPage;
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
//    @DBUser(username = "test8", password = "12345")
    @DBUser(username = "", password = "")
    void mainPageShouldBeVisibleAfterLogin(AuthUserEntity user) {
        Selenide.open(CFG.nifflerFrontUrl());
        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
    }

    @Test
//    @DBUser(username = "test8", password = "12345")
    @DBUser(username = "", password = "")
    @ApiLogin
    void mainPageShouldBeVisible() {
        Selenide.open(CFG.nifflerFrontUrl());
        new MainPage()
                .mainPageShouldBeOpened();
    }
}
