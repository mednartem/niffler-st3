package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.db.model.auth.AuthUserEntity;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement usernameInput = $("[name=username]");
    private final SelenideElement passwordInput = $("[name=password]");
    private final SelenideElement submitBtn = $("[type=submit]");
    private final SelenideElement namePage = $(".form__paragraph");
    private final SelenideElement signUpBtn = $("a[href*=register]");

    public MainPage signIn(AuthUserEntity user) {
        usernameInput.setValue(user.getUsername());
        passwordInput.setValue(user.getPassword());
        submitBtn.click();
        return new MainPage();
    }

    public void loginPageShouldBeOpen() {
        namePage.shouldHave(text("Please sign in"));
    }

    public RegisterPage goToRegisterPage() {
        signUpBtn.click();
        return new RegisterPage();
    }
}
