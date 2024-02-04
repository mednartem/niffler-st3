package guru.qa.niffler.test;

import guru.qa.niffler.db.dao.NifflerUserRepository;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.auth.Authority;
import guru.qa.niffler.db.model.auth.AuthorityEntity;
import guru.qa.niffler.page.WelcomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class LoginWithLoggingTest extends BaseWebTest {
    private final NifflerUserRepository nifflerUserRepository = new NifflerUserRepository();
    AuthUserEntity createdUser = new AuthUserEntity();

    @BeforeEach
    void before() {
        createdUser
                .setUsername("My user")
                .setPassword("12345")
                .setEnabled(true)
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setCredentialsNonExpired(true)
                .setAuthorities(Arrays.stream(Authority.values())
                        .map(a -> {
                            AuthorityEntity ae = new AuthorityEntity();
                            ae.setAuthority(a);
                            ae.setUser(createdUser);
                            return ae;
                        }).toList());

        nifflerUserRepository.createUserForTest(createdUser);
    }

    @AfterEach
    void after() {
        nifflerUserRepository.removeAfterTest(createdUser);
    }

    @Test
    void mainPageShouldBeVisibleAfterLogin() {
        new WelcomePage()
                .goToLoginPage()
                .signIn(createdUser)
                .mainPageShouldBeOpened();
    }
}
