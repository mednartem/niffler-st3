package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.dao.impl.spring_jdbc.AuthUserDAOSpringJdbc;
import guru.qa.niffler.db.dao.impl.spring_jdbc.UserDataDAOSpringJdbc;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.auth.Authority;
import guru.qa.niffler.db.model.auth.AuthorityEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;
import guru.qa.niffler.jupiter.annotation.DBUser;
import guru.qa.niffler.util.RandomData;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;

public class DBUserExtension implements BeforeEachCallback, AfterTestExecutionCallback, ParameterResolver {

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(DBUserExtension.class);
//    private final AuthUserDAO authUserDAO = new AuthUserDAOJdbc();
//    private final UserDataUserDAO userDataUserDAO = new UserDataDAOJdbc();
    private final AuthUserDAO authUserDAO = new AuthUserDAOSpringJdbc();
    private final UserDataUserDAO userDataUserDAO = new UserDataDAOSpringJdbc();
//    private final AuthUserDAO authUserDAO = new AuthUserDAOHibernate();
//    private final UserDataUserDAO userDataUserDAO = new UserDataDAOHibernate();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        DBUser annotation = context.getRequiredTestMethod().getAnnotation(DBUser.class);
        if (annotation != null) {
            AuthUserEntity createdUser = new AuthUserEntity();
            createdUser
                    .setUsername(annotation.username().isEmpty() ? RandomData.generateName() : annotation.username())
                    .setPassword(annotation.password().isEmpty() ? RandomData.generatePassword() : annotation.password())
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
            authUserDAO.createUser(createdUser);

            UserDataEntity userdata = new UserDataEntity();
            userdata.setUsername(createdUser.getUsername());
            userdata.setCurrency(CurrencyValues.RUB);

            userDataUserDAO.createUserInUserData(userdata);

            context.getStore(NAMESPACE).put(context.getUniqueId(), createdUser);
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        AuthUserEntity user = context.getStore(NAMESPACE).get(context.getUniqueId(), AuthUserEntity.class);
        userDataUserDAO.deleteUserByUsernameInUserData(user.getUsername());
        authUserDAO.deleteUser(user);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter()
                .getType()
                .isAssignableFrom(AuthUserEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext
                .getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), AuthUserEntity.class);
    }
}
