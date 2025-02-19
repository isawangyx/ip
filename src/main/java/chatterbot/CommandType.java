package chatterbot;

public enum CommandType {
    BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, FIND, FREE, UNKNOWN;

    /**
     * Parses a command string and returns the corresponding CommandType.
     * If the command is unrecognized, it returns UNKNOWN.
     *
     * @param command The command string.
     * @return The corresponding CommandType.
     */
    public static CommandType fromString(String command) {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

