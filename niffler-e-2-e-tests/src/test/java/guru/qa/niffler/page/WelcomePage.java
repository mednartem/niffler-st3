package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class WelcomePage {
    private final SelenideElement loginBtn = $("a[href*=redirect]");
    private final SelenideElement registerBtn = $("a[href*=register]");

    public LoginPage goToLoginPage() {
        loginBtn.click();
        return new LoginPage();
    }

    public RegisterPage goToRegisterPage() {
        registerBtn.click();
        return new RegisterPage();
    }
}
