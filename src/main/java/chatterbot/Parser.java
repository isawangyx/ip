package chatterbot;

import chatterbot.exceptions.UnknownCommandException;
import chatterbot.exceptions.EmptyDescriptionException;
import chatterbot.tasks.*;

import java.util.List;

/**
 * Parses user commands and executes the appropriate actions.
 */
public class Parser {
    /**
     * Processes the user command and executes the corresponding action.
     *
     * @param userInput The user's command input.
     * @param tasks The task list to be modified.
     * @param ui The user interface to display messages.
     * @param storage The storage system to save tasks.
     * @return {@code true} if the chatbot should continue running, {@code false} if it should exit.
     * @throws EmptyDescriptionException If the command requires a description but none is provided.
     * @throws UnknownCommandException If the command is not recognized.
     */
    public static boolean handleCommand(String userInput, TaskList tasks, Ui ui, Storage storage)
            throws EmptyDescriptionException, UnknownCommandException {

        if (userInput.equals("bye")) {
            ui.showExitMessage();
            return false;
        } else if (userInput.equals("list")) {
            tasks.printTasks(ui);
        } else if (userInput.startsWith("todo")) {
            handleTodoCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("deadline")) {
            handleDeadlineCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("event")) {
            handleEventCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("mark")) {
            handleMarkCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("unmark")) {
            handleUnmarkCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("delete")) {
            handleDeleteCommand(userInput, tasks, ui);
        } else if (userInput.startsWith("find")) {
            handleFindCommand(userInput, tasks, ui);
        } else {
            throw new UnknownCommandException();
        }

        return true;
    }

    /**
     * Handles the creation of a new ToDo task.
     *
     * @param userInput The full user command input.
     * @param tasks The task list to add the ToDo task to.
     * @param ui The user interface for displaying messages.
     * @throws EmptyDescriptionException If the ToDo description is empty.
     */
    private static void handleTodoCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles the creation of a new Deadline task.
     *
     * @param userInput The full user command input.
     * @param tasks The task list to add the Deadline task to.
     * @param ui The user interface for displaying messages.
     * @throws EmptyDescriptionException If the Deadline description or date is empty.
     */
    private static void handleDeadlineCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String[] parts = userInput.substring(8).split(" /by ");
        if (parts.length < 2) {
            throw new EmptyDescriptionException("deadline");
        }
        Task newTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles the creation of a new Event task.
     *
     * @param userInput The full user command input.
     * @param tasks The task list to add the Event task to.
     * @param ui The user interface for displaying messages.
     * @throws EmptyDescriptionException If the Event description, start time, or end time is missing.
     */
    private static void handleEventCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String[] parts = userInput.substring(5).split(" /from | /to ");
        if (parts.length < 3) {
            throw new EmptyDescriptionException("event");
        }
        Task newTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles marking a task as done.
     *
     * @param userInput The full user command input.
     * @param tasks The task list containing the tasks.
     * @param ui The user interface for displaying messages.
     */
    private static void handleMarkCommand(String userInput, TaskList tasks, Ui ui) {
        int taskIdx = Integer.parseInt(userInput.substring(5)) - 1;
        tasks.markTaskAsDone(taskIdx);
        ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.getTask(taskIdx));
    }

    /**
     * Handles unmarking a task (marking it as not done).
     *
     * @param userInput The full user command input.
     * @param tasks The task list containing the tasks.
     * @param ui The user interface for displaying messages.
     */
    private static void handleUnmarkCommand(String userInput, TaskList tasks, Ui ui) {
        int taskIdx = Integer.parseInt(userInput.substring(7)) - 1;
        tasks.markTaskAsNotDone(taskIdx);
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + tasks.getTask(taskIdx));
    }

    /**
     * Handles deleting a task from the task list.
     *
     * @param userInput The full user command input.
     * @param tasks The task list from which the task should be removed.
     * @param ui The user interface for displaying messages.
     */
    private static void handleDeleteCommand(String userInput, TaskList tasks, Ui ui) {
        try {
            int taskIdx = Integer.parseInt(userInput.substring(7)) - 1;
            Task removedTask = tasks.removeTask(taskIdx);
            ui.showMessage("Noted. I've removed this task:\n  " + removedTask +
                    "\nNow you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            System.out.println("Please specify a valid task number to delete.");
        }
    }

    private static void handleFindCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String keyword = userInput.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new EmptyDescriptionException("find");
        }
        List<Task> matchingTasks = tasks.findTasks(keyword);
        ui.showMatchingTasks(matchingTasks);
    }

}
