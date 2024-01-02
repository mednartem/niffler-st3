package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.model.userdata.UserDataEntity;

public interface UserDataUserDAO {

    void createUserInUserData(UserDataEntity user);

    UserDataEntity getUserInUserData(String userName);

    void deleteUserByUsernameInUserData(String username);

    void updateUserInUserData(UserDataEntity user);
}
