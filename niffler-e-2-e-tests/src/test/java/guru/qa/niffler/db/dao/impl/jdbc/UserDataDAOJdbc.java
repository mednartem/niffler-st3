package guru.qa.niffler.db.dao.impl.jdbc;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.jbdc.DataSourceProvider;
import guru.qa.niffler.db.model.CurrencyValues;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserDataDAOJdbc implements UserDataUserDAO {

    private static final DataSource userdataDs = DataSourceProvider.INSTANCE.getDataSource(ServiceDB.USERDATA);

    @Override
    public UserDataEntity getUserInUserData(String username) {
        try (Connection conn = userdataDs.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM users WHERE username = ?")) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    UserDataEntity user = new UserDataEntity();
                    user.setId(UUID.fromString(rs.getString("id")));
                    user.setUsername(rs.getString("username"));
                    user.setCurrency(CurrencyValues.valueOf(rs.getString("currency")));
                    user.setFirstname(rs.getString("firstname"));
                    user.setSurname(rs.getString("surname"));
                    user.setPhoto(rs.getBytes("photo"));

                    return user;
                } else {
                    throw new IllegalArgumentException("User in UserData with username " + username + " not found");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUserInUserData(UserDataEntity user) {
        try (Connection conn = userdataDs.getConnection()) {
            try (PreparedStatement usersPs = conn.prepareStatement(
                    "INSERT INTO users (username, currency) " +
                            "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {

                usersPs.setString(1, user.getUsername());
                usersPs.setString(2, CurrencyValues.RUB.name());

                usersPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserInUserData(UserDataEntity user) {
        try (Connection conn = userdataDs.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "UPDATE users SET currency = ?, firstname = ?, surname = ?, photo = ? WHERE id = ?")) {

            ps.setString(1, user.getCurrency().name());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getSurname());
            ps.setBytes(4, user.getPhoto());
            ps.setObject(5, user.getId());

            int updatedRows = ps.executeUpdate();
            if (updatedRows == 0) {
                throw new IllegalArgumentException("User in UserData with id " + user.getId() + " not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUserByUsernameInUserData(String username) {
        try (Connection conn = userdataDs.getConnection()) {
            try (PreparedStatement userPs = conn.prepareStatement("DELETE FROM users WHERE username = ?")) {
                userPs.setString(1, username);

                userPs.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
