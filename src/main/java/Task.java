public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                Task todo = new Todo(description);
                if (isDone) todo.markAsDone();
                return todo;
            case "D":
                if (parts.length < 4) return null;
                Task deadline = new Deadline(description, parts[3]);
                if (isDone) deadline.markAsDone();
                return deadline;
            case "E":
                if (parts.length < 5) return null;
                Task event = new Event(description, parts[3], parts[4]);
                if (isDone) event.markAsDone();
                return event;
            default:
                return null;
        }
    }

    public abstract String toFileFormat();

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // Mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

}
