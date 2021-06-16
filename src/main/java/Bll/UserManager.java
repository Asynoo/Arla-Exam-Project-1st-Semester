package Bll;



import Be.User;
import Dal.UserDao;
import Dal.UserDaoDB;
import Exceptions.DatabaseException;

import java.util.List;

public class UserManager {

    UserDao userDaoDB;

    public UserManager(){
        this.userDaoDB = new UserDaoDB();
    }

    public List<User> getUserList() throws DatabaseException {
        return userDaoDB.getUserList();
    }

    public String createNewUser(User newUser) throws DatabaseException {
        if(newUser.getUsername()==null||newUser.getPasswordHash()==null){
            String fail = "A user with provided info cannot be created";
            return fail;
        }else {
            String success = "User is sent to be created in database";
            userDaoDB.createNewUser(newUser);
            return success;
        }
    }

    public String deleteUser(User user) throws DatabaseException {
        if(user.getUserID()==null){
            String fail = "A user with provided info cannot be deleted";
            return fail;
        }else {
            String success = "User is successfully sent to be deleted";
            userDaoDB.deleteUser(user);
            return success;
        }

    }

    public void updateUser(User updatedUser) throws DatabaseException {
        userDaoDB.updateUser(updatedUser);}

    public void deleteTestUser(User user)throws DatabaseException{
        userDaoDB.deleteTestUser(user);
    }
}
