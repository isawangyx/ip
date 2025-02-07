package chatterbot.exceptions;

public class UnknownCommandException extends ChatterBotException {
    public UnknownCommandException() {
        super("Looks like you got creative with the input. Let’s stick to what I know for now!");
    }
}
