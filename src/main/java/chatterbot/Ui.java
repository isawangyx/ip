package chatterbot;

import chatterbot.tasks.Task;
import java.util.Scanner;

/**
 * Handles interactions with the user.
 * Responsible for displaying messages and reading user input.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Constructs a Ui instance and initializes the scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads the next command from the user.
     *
     * @return The user input as a trimmed string.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Displays a welcome message when the chatbot starts.
     */
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm ChatterBot");
        System.out.println("What can I do for you?");
    }

    /**
     * Displays the chatbot's exit message.
     */
    public void showExitMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Displays a message.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a confirmation message when a task is added to the task list.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding the new task.
     */
    public void printAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}


