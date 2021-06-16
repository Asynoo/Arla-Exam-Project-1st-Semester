package Dal;


import Be.User;
import Exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoDB implements UserDao {

    private static DataAccess dataAccess = new DataAccess();

    @Override
    public List<User> getUserList() throws DatabaseException {
        ArrayList<User> listOfUsers = new ArrayList<>();
        try(Connection con = dataAccess.getConnection()){
            String sql = "SELECT * FROM Users";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                int userId = rs.getInt("UserID");
                String username = rs.getString("Username");
                String passwordHash = rs.getString("PasswordHash");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                Boolean isAdmin = rs.getBoolean("IsAdmin");
                Integer templateId = rs.getInt("TemplateID");
                User user = new User(userId, username, passwordHash, firstName, lastName, isAdmin,templateId);
                listOfUsers.add(user);
            }

        }catch (Exception e){
            throw new DatabaseException("Couldn't fetch data from the User table in the database");
        }
        return listOfUsers;
    }

    @Override
    public void createNewUser(User newUser) throws DatabaseException {
        try(Connection con = dataAccess.getConnection()){
            String sql;
            if(newUser.getTemplateID() != null) {
                sql = "INSERT INTO Users (Username,PasswordHash,FirstName,LastName,IsAdmin,TemplateID) VALUES (?,?,?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);

                statement.setString(1, newUser.getUsername());
                statement.setString(2, newUser.getPasswordHash());
                statement.setString(3, newUser.getFirstName());
                statement.setString(4, newUser.getLastName());
                statement.setBoolean(5, newUser.getAdmin());
                statement.setInt(6, newUser.getTemplateID());
                statement.executeUpdate();
            }
            else {
                sql = "INSERT INTO Users (Username,PasswordHash,FirstName,LastName,IsAdmin) VALUES (?,?,?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);

                statement.setString(1, newUser.getUsername());
                statement.setString(2, newUser.getPasswordHash());
                statement.setString(3, newUser.getFirstName());
                statement.setString(4, newUser.getLastName());
                statement.setBoolean(5, newUser.getAdmin());
                statement.executeUpdate();
            }


        }catch (Exception e){
            throw new DatabaseException("Couldn't access the User table in the database");
        }
    }

    @Override
    public void deleteUser(User user) throws DatabaseException {
        try (Connection con = dataAccess.getConnection()) {
            String sql1 = "DELETE FROM Users WHERE userID = ?";
            PreparedStatement statement1 = con.prepareStatement(sql1);
            statement1.setInt(1, user.getUserID());
            statement1.executeUpdate();
        } catch (Exception e){
            throw new DatabaseException("Couldn't access the User table in the database");
        }
    }

    @Override
    public void updateUser(User updatedUser) throws DatabaseException {
        try(Connection con = dataAccess.getConnection()){
            String sql = "UPDATE Users SET [Username] = ?, [PasswordHash] = ?, [FirstName] = ?, [LastName] = ?, [IsAdmin]=?, [TemplateID]=?  WHERE UserID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,updatedUser.getUsername());
            statement.setString(2,updatedUser.getPasswordHash());
            statement.setString(3,updatedUser.getFirstName());
            statement.setString(4,updatedUser.getLastName());
            statement.setBoolean(5,updatedUser.getAdmin());
            if (updatedUser.getTemplateID() == null)
                statement.setNull(6,Types.NULL);
            else
                statement.setInt(6, updatedUser.getTemplateID());
            statement.setInt(7,updatedUser.getUserID());
            statement.executeUpdate();

        }catch (Exception e){
            throw new DatabaseException("Couldn't access the User table in the database");
        }

    }

    public void deleteTestUser(User user) throws DatabaseException {
        try (Connection con = dataAccess.getConnection()) {
            String sql1 = "DELETE FROM Users WHERE Username = ?";
            PreparedStatement statement1 = con.prepareStatement(sql1);
            statement1.setString(1, user.getUsername());
            statement1.executeUpdate();
        } catch (Exception e){
            throw new DatabaseException("Couldn't access the User table in the database");
        }
    }
}
