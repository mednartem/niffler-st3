package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProfilePage {
    private final SelenideElement firstNameInput = $("[name=firstname]");
    private final SelenideElement surNameInput = $("[name=surname]");
    private final SelenideElement categoryInput = $("[name=category]");
    private final SelenideElement createCategoryBtn = $(".add-category__input-container .button");
    private final SelenideElement submitBtn = $("[type=submit]");
    private final ElementsCollection listCategories = $$(".categories__item");

    public ProfilePage enterFirstName(String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    public ProfilePage enterSurName(String surName) {
        surNameInput.setValue(surName);
        return this;
    }

    public ProfilePage enterCategory(String category) {
        categoryInput.setValue(category);
        return this;
    }

    public void clickSubmitBtn() {
        submitBtn.scrollTo().click();
    }

    public void clickCreateCategoryBtn() {
        createCategoryBtn.click();
    }

    public void firstNameShouldBe(String firstName) {
        firstNameInput.shouldHave(value(firstName));
    }

    public void surNameShouldBe(String surName) {
        surNameInput.shouldHave(value(surName));
    }

    public void listCategoriesShouldHave(String category) {
        listCategories.shouldHave(itemWithText(category));
    }
}
