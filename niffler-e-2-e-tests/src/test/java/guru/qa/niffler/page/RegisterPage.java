package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    private final SelenideElement usernameInput = $("[name=username]");
    private final SelenideElement passwordInput = $("[name=password]");
    private final SelenideElement passwordInputError = passwordInput.sibling(0);
    private final SelenideElement confirmPasswordInput = $("[name=passwordSubmit]");
    private final SelenideElement signUpBtn = $("[type=submit]");
    private final SelenideElement signIn = $("a[href*=redirect]");
    private final SelenideElement namePage = $(".form__paragraph");

    public RegisterPage enterUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage enterConfirmPassword(String password) {
        confirmPasswordInput.setValue(password);
        return this;
    }

    public RegisterPage clickSignUpBtn() {
        signUpBtn.click();
        return this;
    }

    public LoginPage clickSignInBtn() {
        signIn.click();
        return new LoginPage();
    }

    public void passwordInputShouldHaveError(String error) {
        passwordInputError.shouldHave(Condition.text(error));
    }

    public void registerPageShouldBeOpen() {
        namePage.shouldHave(text("Registration form"));
    }
}
