package exceptions;

/**
 * A custom exception which is to be thrown if the maze data doesn't match the given format.
 */
public class MazeMalformedException extends Exception {
    /**
     * A constructor which takes no arguments and
     * creates a new instance of MazeMalformedException with default
     * message.
     */
    public MazeMalformedException() {
        super();
        System.out.println("//!// MazeMalformedException");
    }

    /**
     * A constructor which takes a message and
     * creates a new instance of MazeMalformedException with the given message
     * @param message a message to be included in the exception and printed
     */
    public MazeMalformedException(String message) {
        super(message);
        System.out.print("//!// MazeMalformedException: ");
        System.out.println(message);
    }
}
