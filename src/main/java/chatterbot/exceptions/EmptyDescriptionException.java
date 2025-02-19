package chatterbot.exceptions;

public class EmptyDescriptionException extends ChatterBotException {
    public EmptyDescriptionException(String message) {
        super(String.format("Oops! Something is missing :("
                + System.lineSeparator() + "Try " + message));
    }
}
