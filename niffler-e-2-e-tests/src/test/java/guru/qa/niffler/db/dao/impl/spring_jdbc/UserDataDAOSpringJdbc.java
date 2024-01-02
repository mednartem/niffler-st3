package guru.qa.niffler.db.dao.impl.spring_jdbc;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.jbdc.DataSourceProvider;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.userdata.UserDataEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcTransactionManager;

public class UserDataDAOSpringJdbc implements UserDataUserDAO {

    private final JdbcTemplate userdataJdbcTemplate;

    public UserDataDAOSpringJdbc() {
        JdbcTransactionManager userdataTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDB.USERDATA));

        this.userdataJdbcTemplate = new JdbcTemplate(userdataTm.getDataSource());
    }

    @Override
    public UserDataEntity getUserInUserData(String username) {
        return null;
    }

    @Override
    public void createUserInUserData(UserDataEntity user) {
        userdataJdbcTemplate.update(
                "INSERT INTO users (username, currency) VALUES (?, ?)",
                user.getUsername(),
                CurrencyValues.RUB.name()
        );
    }

    @Override
    public void deleteUserByUsernameInUserData(String username) {
        userdataJdbcTemplate.update("DELETE FROM users WHERE username = ?", username);
    }

    @Override
    public void updateUserInUserData(UserDataEntity user) {

    }
}
