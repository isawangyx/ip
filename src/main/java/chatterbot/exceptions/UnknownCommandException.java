package chatterbot.exceptions;

public class UnknownCommandException extends ChatterBotException {
    public UnknownCommandException() {
        super("Looks like you got creative with the input." + System.lineSeparator()
                + "Let’s try one of the following commands:" + System.lineSeparator()
                + "list" + System.lineSeparator()
                + "todo <desc>" + System.lineSeparator()
                + "deadline <desc> /by <date yyyy-MM-dd>" + System.lineSeparator()
                + "event <desc> /from <start yyyy-MM-dd HHmm> /to <end yyyy-MM-dd HHmm>" + System.lineSeparator()
                + "mark <num>" + System.lineSeparator()
                + "unmark <num>" + System.lineSeparator()
                + "delete <num>" + System.lineSeparator()
                + "find <keyword>" + System.lineSeparator()
                + "free <hours>" + System.lineSeparator()
                + "bye (to exit the program)");
    }
}
