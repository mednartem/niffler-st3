package guru.qa.niffler.db.dao.impl.hibernate;

import guru.qa.niffler.db.ServiceDB;
import guru.qa.niffler.db.dao.UserDataUserDAO;
import guru.qa.niffler.db.jpa.EntityManagerFactoryProvider;
import guru.qa.niffler.db.jpa.JpaService;
import guru.qa.niffler.db.model.userdata.UserDataEntity;

public class UserDataDAOHibernate extends JpaService implements UserDataUserDAO {

    public UserDataDAOHibernate() {
        super(EntityManagerFactoryProvider.INSTANCE.getDataSource(ServiceDB.USERDATA).createEntityManager());
    }

    @Override
    public UserDataEntity getUserInUserData(String username) {
        return em.createQuery("select u from UserDataEntity u where u.username=:username", UserDataEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void createUserInUserData(UserDataEntity user) {
        persist(user);
    }

    @Override
    public void deleteUserByUsernameInUserData(String username) {
        remove(getUserInUserData(username));
    }

    @Override
    public void updateUserInUserData(UserDataEntity user) {
        merge(user);
    }
}
