package chatterbot.tasks;

/**
 * Represents a simple todo task without a specific date or time.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo task with a description.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a formatted string representation of the Todo task.
     *
     * @return A string in the format [T][ ] task description.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     *
     * @return A string in the format T | 0 | description (where 0 represents not done, 1 represents done).
     */
    @Override
    public String toFileFormat() {
        return "T | " + (isDone ? "1" : "0") + " | " + description;
    }
}
