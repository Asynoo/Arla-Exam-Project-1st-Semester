package Bll;

import Be.User;
import Exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void createNewUserSuccess() {
        UserManager userMAn = new UserManager();
        User correctUser = new User(null,"UnittestUsername", "0da5ed160b519bf5afc2d9fe55711c94bd9c0b15676f0eb9a3e6b90c983b1f0f","MarcoDanyTommy","MegaName",false,null);
        String answer = null;
        try {
            answer = userMAn.createNewUser(correctUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"User is sent to be created in database");

        try {
            userMAn.deleteTestUser(correctUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createNewUserFail() {
        UserManager userMAn = new UserManager();
        User correctUser = new User(null,null, "0da5ed160b519bf5afc2d9fe55711c94bd9c0b15676f0eb9a3e6b90c983b1f0f","MarcoDanyTommy","MegaName",false,null);
        String answer = null;
        try {
            answer = userMAn.createNewUser(correctUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"A user with provided info cannot be created");
    }

    @Test
    void deleteUserSuccess() {
        UserManager userMAn = new UserManager();
        User correctUser = new User(100000,"UnittestUsername", "0da5ed160b519bf5afc2d9fe55711c94bd9c0b15676f0eb9a3e6b90c983b1f0f","MarcoDanyTommy","MegaName",false,null);
        String answer = null;
        try {
            answer = userMAn.deleteUser(correctUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"User is successfully sent to be deleted");
    }

    @Test
    void deleteUserFail() {
        UserManager userMAn = new UserManager();
        User correctUser = new User(null,"UnittestUsername", "0da5ed160b519bf5afc2d9fe55711c94bd9c0b15676f0eb9a3e6b90c983b1f0f","MarcoDanyTommy","MegaName",false,null);
        String answer = null;
        try {
            answer = userMAn.deleteUser(correctUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(answer,"A user with provided info cannot be deleted");
    }
}