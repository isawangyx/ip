package chatterbot.exceptions;

public class EmptyDescriptionException extends ChatterBotException {
    public EmptyDescriptionException(String type) {
        super(String.format("Oops! The %s is empty, just like my heart "
                + "when you don’t give me enough information.", type));
    }
}
