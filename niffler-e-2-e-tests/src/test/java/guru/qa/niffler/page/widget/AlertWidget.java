package guru.qa.niffler.page.widget;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class AlertWidget {
    private final SelenideElement alert = $(".Toastify__toast-body");

    public void alertShouldHaveProfileUpdated() {
        alert.shouldHave(text("Profile updated!"));
    }

    public void alertShouldHaveNewCategoryAdded() {
        alert.shouldHave(text("New category added!"), Duration.ofSeconds(10));
    }
}
