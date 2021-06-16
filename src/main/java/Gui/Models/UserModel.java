package Gui.Models;



import Be.User;
import Bll.UserManager;
import Exceptions.DatabaseException;

import java.util.List;

/**
 * Model responsible for caching and handling users in the system crud functionality.
 * */

public class UserModel {

    private List<User> listUsers;
    private UserManager userManager;


    public UserModel() throws DatabaseException {
        this.userManager = new UserManager();
        refreshUsers();
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void refreshUsers() throws DatabaseException {
        listUsers = userManager.getUserList();
    }

    public void createNewUser(User newUser) throws DatabaseException {
        userManager.createNewUser(newUser);
        refreshUsers();
    }

    public void deleteUser(User user) throws DatabaseException {
        userManager.deleteUser(user);
        refreshUsers();
    }

    public void updateUser(User updatedUser) throws DatabaseException {
        userManager.updateUser(updatedUser);
        refreshUsers();
    }
}
