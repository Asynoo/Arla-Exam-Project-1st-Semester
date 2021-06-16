package Exceptions;

/**
 *Exception fired upon problems with creating or retrieving files.
 * */

public class FileHandlingException extends Exception {
    public FileHandlingException(String message) {
        super(message);
    }
}
