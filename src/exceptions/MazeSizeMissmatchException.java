package exceptions;

/**
 * a custom exception which is to be thrown if the maze data doesn't match the specified dimensions.
 */
public class MazeSizeMissmatchException extends Exception {
    /**
     * A constructor which takes no arguments and creates
     * a new instance of MazeMissmatchException with default
     * message.
     */
    public MazeSizeMissmatchException() {
        super();
        System.out.println("//!// MazeSizeMissmatchException");
    }

    /**
     * A constructor which takes a message and creates a
     * new instance of MazeMissmatchException with the given message
     * @param message a message to be included in the exception and printed
     */
    public MazeSizeMissmatchException(String message) {
        super(message);
        System.out.print("//!// MazeSizeMissmatchException: ");
        System.out.println(message);
    }
}
