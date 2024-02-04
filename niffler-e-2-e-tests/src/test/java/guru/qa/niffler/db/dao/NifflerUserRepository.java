package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.dao.impl.hibernate.AuthUserDAOHibernate;
import guru.qa.niffler.db.dao.impl.hibernate.UserDataDAOHibernate;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public class NifflerUserRepository {
    private final AuthUserDAO authUserDAO = new AuthUserDAOHibernate();
    private final UserDataUserDAO userDataUserDAO = new UserDataDAOHibernate();

    public void createUserForTest(AuthUserEntity user) {
        authUserDAO.createUser(user);
        userDataUserDAO.createUserInUserData(fromAuthUser(user));
    }

    public void removeAfterTest(AuthUserEntity user) {
        userDataUserDAO.deleteUserByUsernameInUserData(user.getUsername());
        authUserDAO.deleteUser(user);
    }

    private UserDataEntity fromAuthUser(AuthUserEntity user) {
        UserDataEntity userData = new UserDataEntity();
        userData.setUsername(user.getUsername());
        userData.setCurrency(CurrencyValues.RUB);
        return userData;
    }
}
