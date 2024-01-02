package guru.qa.niffler.test;


import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static guru.qa.niffler.jupiter.annotation.User.UserType.WITH_FRIENDS;

public class FriendsWebTest extends BaseWebTest {

    @BeforeEach
    void doLogin(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        Selenide.open("http://127.0.0.1:3000/main");
        $("a[href*='redirect']").click();
        $("input[name='username']").setValue(userForTest.getUsername());
        $("input[name='password']").setValue(userForTest.getPassword());
        $("button[type='submit']").click();
    }

    @Test
    @AllureId("101")
    void tableShouldHaveFriends0(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        $("[data-tooltip-id=friends]").click();
        $$("[data-tooltip-id=remove-friend]").shouldHave(size(1));
    }

    @Test
    @AllureId("102")
    void tableShouldHaveFriends1(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        $("[data-tooltip-id=friends]").click();
        $$("[data-tooltip-id=remove-friend]").shouldHave(size(1));
    }

    @Test
    @AllureId("103")
    void tableShouldHaveFriends2(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        $("[data-tooltip-id=friends]").click();
        $$("[data-tooltip-id=remove-friend]").shouldHave(size(1));
    }

    @Test
    @AllureId("104")
    void tableShouldHaveFriends3(@User(userType = WITH_FRIENDS) UserJson userForTest) {
        $("[data-tooltip-id=friends]").click();
        $$("[data-tooltip-id=remove-friend]").shouldHave(size(1));
    }
}
