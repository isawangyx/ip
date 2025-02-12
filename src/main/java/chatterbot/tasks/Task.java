package chatterbot.tasks;

/**
 * Represents a task with a description and completion status.
 * This is an abstract class meant to be extended by specific task types.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task instance with a given description.
     * Initially, the task is not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Converts a formatted string from the storage file back into a Task object.
     * This method is used to restore tasks when loading from the saved file.
     *
     * @param line The formatted string representing a task (e.g., "T | 1 | read book").
     * @return A Task object (Todo, Deadline, or Event) if the format is valid, otherwise null.
     */
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
        case "T":
            Task todo = new Todo(description);
            if (isDone) {
                todo.markAsDone();
            }
            return todo;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            Task deadline = new Deadline(description, parts[3]);
            if (isDone) {
                deadline.markAsDone();
            }
            return deadline;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            Task event = new Event(description, parts[3], parts[4]);
            if (isDone) {
                event.markAsDone();
            }
            return event;
        default:
            return null;
        }
    }

    /**
     * Converts the task to a file-friendly format for storage.
     *
     * @return A string representation of the task for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, otherwise a space " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // Mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Converts the task to a string representation.
     *
     * @return A formatted string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

}
