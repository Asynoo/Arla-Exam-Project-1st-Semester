package Exceptions;

/**
 *Exception fired when there occure problems with database, upon this problem, app will shut down.
 * */

public class DatabaseException extends Exception {
    public DatabaseException(String message) {
        super(message);
    }
}
