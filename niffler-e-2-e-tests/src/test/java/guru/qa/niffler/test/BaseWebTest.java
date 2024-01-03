package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.WebTest;
import org.junit.jupiter.api.BeforeEach;

@WebTest
public abstract class BaseWebTest {

    @BeforeEach
    void openBasePage() {
        Selenide.open("http://127.0.0.1:3000/main");
    }
}
