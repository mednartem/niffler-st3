package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.annotation.WebTest;
import org.junit.jupiter.api.BeforeEach;

@WebTest
public abstract class BaseWebTest {

    protected static final Config CFG = Config.getInstance();
}
