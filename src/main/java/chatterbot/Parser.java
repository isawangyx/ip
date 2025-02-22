package chatterbot;

import java.util.List;
import java.util.stream.Collectors;

import chatterbot.exceptions.EmptyDescriptionException;
import chatterbot.exceptions.UnknownCommandException;
import chatterbot.tasks.*;

/**
 * Parses user commands and executes the appropriate actions.
 */
public class Parser {

    /**
     * Processes the user command and executes the corresponding action.
     *
     * @param userInput The user's command input.
     * @param tasks     The task list to be modified.
     * @param ui        The user interface to display messages.
     * @param storage   The storage system to save tasks.
     * @return {@code true} if the chatbot should continue running, {@code false} if it should exit.
     * @throws EmptyDescriptionException If the command requires a description but none is provided.
     * @throws UnknownCommandException   If the command is not recognized.
     */
    public static boolean handleCommand(String userInput, TaskList tasks, Ui ui, Storage storage)
            throws EmptyDescriptionException, UnknownCommandException {

        // Extract the command keyword from the input
        String[] parts = userInput.split(" ", 2);
        String commandString = parts[0];

        CommandType command = CommandType.fromString(commandString);

        switch (command) {
        case BYE:
            ui.showExitMessage();
            return false;

        case LIST:
            tasks.printTasks(ui);
            break;

        case TODO:
            handleTodoCommand(userInput, tasks, ui);
            break;

        case DEADLINE:
            handleDeadlineCommand(userInput, tasks, ui);
            break;

        case EVENT:
            handleEventCommand(userInput, tasks, ui);
            break;

        case MARK:
            handleMarkCommand(userInput, tasks, ui);
            break;

        case UNMARK:
            handleUnmarkCommand(userInput, tasks, ui);
            break;

        case DELETE:
            handleDeleteCommand(userInput, tasks, ui);
            break;

        case FIND:
            handleFindCommand(userInput, tasks, ui);
            break;

        case FREE:
            handleFreeCommand(userInput, tasks, ui);
            break;

        case UNKNOWN:
        default:
            throw new UnknownCommandException();
        }

        return true;
    }

    /**
     * Handles the creation of a new ToDo task.
     *
     * @param userInput The full user command input.
     * @param tasks     The task list to add the ToDo task to.
     * @param ui        The user interface for displaying messages.
     * @throws EmptyDescriptionException If the ToDo description is empty.
     */
    private static void handleTodoCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo <desc>");
        }
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles the creation of a new Deadline task.
     *
     * @param userInput The full user command input.
     * @param tasks     The task list to add the Deadline task to.
     * @param ui        The user interface for displaying messages.
     * @throws EmptyDescriptionException If the Deadline description or date is empty.
     */
    private static void handleDeadlineCommand(String userInput, TaskList tasks, Ui ui)
            throws EmptyDescriptionException {
        String[] parts = userInput.substring(8).split(" /by ");
        if (parts.length < 2) {
            throw new EmptyDescriptionException("deadline <desc> /by <date yyyy-MM-dd HHmm>");
        }
        Task newTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles the creation of a new Event task.
     *
     * @param userInput The full user command input.
     * @param tasks     The task list to add the Event task to.
     * @param ui        The user interface for displaying messages.
     * @throws EmptyDescriptionException If the Event description, start time, or end time is missing.
     */
    private static void handleEventCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String[] parts = userInput.substring(5).split(" /from | /to ");
        if (parts.length < 3) {
            throw new EmptyDescriptionException("event <desc> /from <start yyyy-MM-dd HHmm> /to <end yyyy-MM-dd HHmm>");
        }
        Task newTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    /**
     * Handles marking a task as done.
     *
     * @param userInput The full user command input.
     * @param tasks     The task list containing the tasks.
     * @param ui        The user interface for displaying messages.
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
     * @param tasks     The task list containing the tasks.
     * @param ui        The user interface for displaying messages.
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
     * @param tasks     The task list from which the task should be removed.
     * @param ui        The user interface for displaying messages.
     */
    private static void handleDeleteCommand(String userInput, TaskList tasks, Ui ui) {
        try {
            int taskIdx = Integer.parseInt(userInput.substring(7)) - 1;
            Task removedTask = tasks.removeTask(taskIdx);
            ui.showMessage("Noted. I've removed this task:\n  " + removedTask
                    + "\nNow you have " + tasks.size() + " tasks in the list.");
        } catch (NumberFormatException e) {
            System.out.println("Please specify a valid task number to delete.");
        }
    }

    /**
     * Handles the "find" command by searching for tasks containing the specified keyword.
     * If no matching tasks are found, an appropriate message is displayed.
     *
     * @param userInput The full user command input containing the search keyword.
     * @param tasks     The task list to search for matching tasks.
     * @param ui        The user interface for displaying search results.
     * @throws EmptyDescriptionException If the keyword is empty or not provided.
     */
    private static void handleFindCommand(String userInput, TaskList tasks, Ui ui)
            throws EmptyDescriptionException {
        String keyword = userInput.substring(4).trim();
        if (keyword.isEmpty()) {
            throw new EmptyDescriptionException("find");
        }

        String matchingTasks = tasks.findTasks(keyword).stream()
                .map(task -> (tasks.getAllTasks().indexOf(task) + 1) + ". " + task)
                .collect(Collectors.joining("\n"));

        ui.showMessage(matchingTasks.isEmpty() ? "No matching tasks found."
                : "Here are the matching tasks in your list:\n" + matchingTasks);
    }

    /**
     * Handles the "free" command to find an available time slot of the specified duration.
     *
     * @param userInput The user's command input.
     * @param tasks     The task list to check for free time slots.
     * @param ui        The user interface to display messages.
     */
    private static void handleFreeCommand(String userInput, TaskList tasks, Ui ui) {
        try {
            String[] parts = userInput.split(" ");
            if (parts.length < 2 || !parts[1].endsWith("h")) {
                ui.showMessage("Invalid format! Use: free <hours> (e.g., free 4h)");
                return;
            }

            int hours = Integer.parseInt(parts[1].replace("h", ""));
            String freeSlot = tasks.findFreeTime(hours);
            ui.showMessage(freeSlot);

        } catch (NumberFormatException e) {
            ui.showMessage("Invalid number format! Use: free <hours> (e.g., free 4h)");
        }
    }
}
