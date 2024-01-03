package guru.qa.niffler.page.widget;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.ProfilePage;

import static com.codeborne.selenide.Selenide.$;

public class HeaderWidget {
    private final SelenideElement profileLink = $("[data-tooltip-id=profile]");

    public ProfilePage clickProfile() {
        profileLink.click();
        return new ProfilePage();
    }
}
