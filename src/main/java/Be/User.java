package Be;
/**
 *User entity used in crud operations and assignment of complex structures to the database
 * */
public class User {

    private Integer userID,templateID;

    private String username,passwordHash,firstName,lastName;

    private Boolean isAdmin;

    public User(Integer userID, String username, String passwordHash, String firstName, String lastName, Boolean isAdmin,Integer templateID){
        this.userID = userID;
        this.username = username;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdmin = isAdmin;
        this.templateID = templateID;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public Integer getUserID() {
        return userID;
    }
    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

}
