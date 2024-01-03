package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.page.CongratulationsPage;
import guru.qa.niffler.page.WelcomePage;
import guru.qa.niffler.util.RandomData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterTest extends BaseWebTest {

    @BeforeEach
    void beforeEach() {
        Selenide.open("http://127.0.0.1:3000/main");
    }

    @Test
    void register() {
        String username = RandomData.generateName();
        String password = RandomData.generatePassword();

        new WelcomePage()
                .goToRegisterPage()
                .enterUsername(username)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickSignUpBtn();
        new CongratulationsPage()
                .pageShouldHaveTextCongratulationsYouHaveRegistered();
    }

    @Test
    void passwordInputShouldHaveErrorLengthShouldBeFrom3To12Characters() {
        String username = RandomData.generateName();
        String password = "12";

        new WelcomePage()
                .goToRegisterPage()
                .enterUsername(username)
                .enterPassword(password)
                .enterConfirmPassword(password)
                .clickSignUpBtn()
                .passwordInputShouldHaveError("Allowed password length should be from 3 to 12 characters");
    }

    @Test
    void openSignInInSignUpPage() {
        new WelcomePage()
                .goToRegisterPage()
                .clickSignInBtn()
                .loginPageShouldBeOpen();
    }
}
