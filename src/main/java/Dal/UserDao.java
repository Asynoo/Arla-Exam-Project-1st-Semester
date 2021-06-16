package Dal;

import Be.User;
import Exceptions.DatabaseException;

import java.util.List;

public interface UserDao {
    List<User> getUserList() throws DatabaseException;

    void createNewUser(User newUser) throws DatabaseException;

    void deleteUser(User user) throws DatabaseException;

    void updateUser(User updatedUser) throws DatabaseException;

    void deleteTestUser(User user) throws DatabaseException;
}



