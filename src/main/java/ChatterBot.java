import exceptions.UnknownCommandException;
import exceptions.EmptyDescriptionException;
import java.util.Scanner;
import java.util.ArrayList;

public class ChatterBot {
    private static final String FILE_PATH = "data/chatterbot.txt";
    private static Storage storage;
    private static ArrayList<Task> tasks;

    public static void main(String[] args) {
        storage = new Storage(FILE_PATH);
        tasks = new ArrayList<>(storage.loadTasks());

        Scanner sc = new Scanner(System.in);
        System.out.println("Hello! I'm ChatterBot");
        System.out.println("What can I do for you?");

        while (true) {
            try {
                String userInput = sc.nextLine().trim();
                if (userInput.equals("bye")) {
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                } else if (userInput.equals("list")) {
                    handleListCommand();
                } else if (userInput.startsWith("todo")) {
                    handleTodoCommand(userInput);
                } else if (userInput.startsWith("deadline")) {
                    handleDeadlineCommand(userInput);
                } else if (userInput.startsWith("event")) {
                    handleEventCommand(userInput);
                } else if (userInput.startsWith("mark")) {
                    handleMarkCommand(userInput);
                } else if (userInput.startsWith("unmark")) {
                    handleUnmarkCommand(userInput);
                } else if (userInput.startsWith("delete")) {
                    handleDeleteCommand(userInput);
                } else {
                    throw new UnknownCommandException();
                }
                storage.saveTasks(tasks);
            } catch (EmptyDescriptionException e) {
                System.out.println(e.getMessage());
            } catch (UnknownCommandException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }

        }

        sc.close();
    }

    private static void handleListCommand() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    private static void handleTodoCommand(String userInput) throws EmptyDescriptionException {
        String description = userInput.length() > 4 ? userInput.substring(4).trim() : "";
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        Task newTask = new Todo(description);
        tasks.add(newTask);
        printAddedTask(newTask, tasks.size());
    }

    private static void handleDeadlineCommand(String userInput)
            throws EmptyDescriptionException {
        String[] parts = userInput.substring(8).split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }
        Task newTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(newTask);
        printAddedTask(newTask, tasks.size());
    }

    private static void handleEventCommand(String userInput) throws EmptyDescriptionException {
        String[] parts = userInput.substring(5).split(" /from | /to ");
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }
        Task newTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.add(newTask);
        printAddedTask(newTask, tasks.size());
    }

    private static void handleMarkCommand(String userInput) {
        int taskIdx = Integer.parseInt(userInput.substring(5)) - 1;
        if (taskIdx >= 0 && taskIdx < tasks.size()) {
            tasks.get(taskIdx).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.get(taskIdx));
        } else {
            System.out.println("Invalid task number. Please enter a valid task number to mark.");
        }
    }

    private static void handleUnmarkCommand(String userInput) {
        int taskIdx = Integer.parseInt(userInput.substring(7)) - 1;
        if (taskIdx >= 0 && taskIdx < tasks.size()) {
            tasks.get(taskIdx).markAsNotDone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.get(taskIdx));
        } else {
            System.out.println("Invalid task number. Please enter a valid task number to unmark.");
        }
    }

    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    private static void handleDeleteCommand(String userInput) {
        try {
            int taskIdx = Integer.parseInt(userInput.substring(7).trim()) - 1;
            if (taskIdx >= 0 && taskIdx < tasks.size()) {
                Task removedTask = tasks.remove(taskIdx);
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + removedTask);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            } else {
                System.out.println("Invalid task number. Please enter a valid task number to delete.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please specify a valid task number to delete.");
        }
    }
}
