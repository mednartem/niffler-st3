package guru.qa.niffler.test;

import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.page.ProfilePage;
import guru.qa.niffler.page.WelcomePage;
import guru.qa.niffler.page.widget.AlertWidget;
import guru.qa.niffler.page.widget.HeaderWidget;
import org.junit.jupiter.api.Test;

public class ProfileTest extends BaseWebTest {

    @Test
    @DBUser(username = "", password = "")
    void firstNameShouldBeEmptyByDefaultInProfile(AuthUserEntity user) {
        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
        new HeaderWidget()
                .clickProfile()
                .firstNameShouldBe("");
    }

    @Test
    @DBUser(username = "", password = "")
    void surNameShouldBeEmptyByDefaultInProfile(AuthUserEntity user) {
        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
        new HeaderWidget()
                .clickProfile()
                .firstNameShouldBe("");
    }

    @Test
    @DBUser(username = "", password = "")
    void shouldAddFirstNameInProfile(AuthUserEntity user) {
        String name = "test name";

        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
        new HeaderWidget()
                .clickProfile()
                .enterFirstName(name)
                .clickSubmitBtn();
        new AlertWidget()
                .alertShouldHaveProfileUpdated();
        new ProfilePage()
                .firstNameShouldBe(name);
    }

    @Test
    @DBUser(username = "", password = "")
    void shouldAddSurNameInProfile(AuthUserEntity user) {
        String name = "test name";

        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
        new HeaderWidget()
                .clickProfile()
                .enterSurName(name)
                .clickSubmitBtn();
        new AlertWidget()
                .alertShouldHaveProfileUpdated();
        new ProfilePage()
                .surNameShouldBe(name);
    }

    @Test
    @DBUser(username = "", password = "")
    void shouldAddCategoryInProfile(AuthUserEntity user) {
        String category = "test category name";

        new WelcomePage()
                .goToLoginPage()
                .signIn(user)
                .mainPageShouldBeOpened();
        new HeaderWidget()
                .clickProfile()
                .enterCategory(category)
                .clickCreateCategoryBtn();
        new AlertWidget()
                .alertShouldHaveNewCategoryAdded();
        new ProfilePage()
                .listCategoriesShouldHave(category);
    }
}
