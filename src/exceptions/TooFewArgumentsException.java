package exceptions;

public class TooFewArgumentsException extends Exception {
    public TooFewArgumentsException() {
        super("Too few arguments.");
    }

    public TooFewArgumentsException(String message) {
        super(message);
    }
}
