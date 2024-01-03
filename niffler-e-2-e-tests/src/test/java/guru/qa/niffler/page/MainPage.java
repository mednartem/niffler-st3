package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement statisticBlock = $(".main-content__section-stats");

    public MainPage mainPageShouldBeOpened() {
        statisticBlock.shouldBe(visible);
        return this;
    }
}
