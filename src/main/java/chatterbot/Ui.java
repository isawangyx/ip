package chatterbot;

import java.util.List;
import java.util.Scanner;

import chatterbot.tasks.Task;

/**
 * Handles interactions with the user.
 * Responsible for displaying messages and reading user input.
 */
public class Ui {
    private final Scanner scanner;
    private String lastMessage = ""; // Stores last chatbot message for GUI

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
     * Displays the chatbot's exit message.
     */
    public void showExitMessage() {
        showMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays a message.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        lastMessage = message;
        System.out.println(message);
    }

    /**
     * Displays a confirmation message when a task is added to the task list.
     *
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding the new task.
     */
    public void printAddedTask(Task task, int taskCount) {
        showMessage("Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.");
    }

    /**
     * Returns the last message for GUI display.
     *
     * @return The last printed message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

}


