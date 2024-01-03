package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class CongratulationsPage {
    private final SelenideElement congratulationsPage = $(".form__paragraph");
    private final SelenideElement loginBtn = $("a[href*=redirect]");

    public void pageShouldHaveTextCongratulationsYouHaveRegistered() {
        congratulationsPage.shouldHave(text("Congratulations! You've registered!"));
    }

    public LoginPage goToLoginPage() {
        loginBtn.click();
        return new LoginPage();
    }
}
