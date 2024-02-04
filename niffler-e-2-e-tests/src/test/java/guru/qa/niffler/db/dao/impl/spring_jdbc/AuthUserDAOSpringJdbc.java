package guru.qa.niffler.db.dao.impl.spring_jdbc;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.AuthUserDAO;
import guru.qa.niffler.db.jbdc.DataSourceProvider;
import guru.qa.niffler.db.model.auth.AuthUserEntity;
import guru.qa.niffler.db.model.auth.Authority;
import guru.qa.niffler.db.model.auth.AuthorityEntity;
import guru.qa.niffler.db.spring_jdbc.AuthorityEntityRowMapper;
import guru.qa.niffler.db.spring_jdbc.UserEntityRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class AuthUserDAOSpringJdbc implements AuthUserDAO {

    private final TransactionTemplate authTpl;
    private final JdbcTemplate authJdbcTemplate;

    public AuthUserDAOSpringJdbc() {
        JdbcTransactionManager authTm = new JdbcTransactionManager(DataSourceProvider.INSTANCE.getDataSource(ServiceDB.AUTH));

        this.authTpl = new TransactionTemplate(authTm);
        this.authJdbcTemplate = new JdbcTemplate(authTm.getDataSource());
    }

    @Override
    public AuthUserEntity getUser(String username) {
        return authJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE username = ?",
                UserEntityRowMapper.instance,
                username
        );
    }

    @Override
    public AuthUserEntity getUserById(UUID userId) {
        AuthUserEntity user = authJdbcTemplate.queryForObject(
                "SELECT * FROM users WHERE id = ?",
                UserEntityRowMapper.instance,
                userId
        );
        List<AuthorityEntity> authorities = authJdbcTemplate.query(
                "SELECT * FROM authorities WHERE user_id = ?",
                AuthorityEntityRowMapper.instance,
                userId
        );
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    @SuppressWarnings("uncheked")
    public int createUser(AuthUserEntity user) {
        return authTpl.execute(status -> {
            KeyHolder kh = new GeneratedKeyHolder();

            authJdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) " +
                        "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUsername());
                ps.setString(2, pe.encode(user.getPassword()));
                ps.setBoolean(3, user.getEnabled());
                ps.setBoolean(4, user.getAccountNonExpired());
                ps.setBoolean(5, user.getAccountNonLocked());
                ps.setBoolean(6, user.getCredentialsNonExpired());

                return ps;
            }, kh);
            final UUID userId = (UUID) kh.getKeyList().get(0).get("id");
            user.setId(userId);
            authJdbcTemplate.batchUpdate("INSERT INTO authorities (user_id, authority) VALUES (?, ?)", new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setObject(1, userId);
                    ps.setObject(2, Authority.values()[i].name());
                }

                @Override
                public int getBatchSize() {
                    return Authority.values().length;
                }
            });

            return 0;
        });
    }

    @Override
    public AuthUserEntity updateUser(AuthUserEntity user) {
        authJdbcTemplate.update("UPDATE users SET password = ?, enabled = ?, account_non_expired = ?," +
                        "account_non_locked = ? WHERE id = ?",
                pe.encode(user.getPassword()), user.getEnabled(), user.getAccountNonExpired(),
                user.getAccountNonLocked(), user.getId()
        );
        return getUserById(user.getId());
    }

    @Override
    public void deleteUser(AuthUserEntity user) {
        authTpl.execute(status -> {
            authJdbcTemplate.update(con -> {
                PreparedStatement authorityPs = con.prepareStatement("DELETE from authorities WHERE user_id = ?");
                authorityPs.setObject(1, user.getId());
                return authorityPs;
            });
            authJdbcTemplate.update(con -> {
                PreparedStatement usersPs = con.prepareStatement("DELETE from users WHERE id = ?");
                usersPs.setObject(1, user.getId());
                return usersPs;
            });
            return 0;
        });
    }
}
