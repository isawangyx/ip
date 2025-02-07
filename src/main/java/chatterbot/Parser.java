package chatterbot;

import chatterbot.exceptions.UnknownCommandException;
import chatterbot.exceptions.EmptyDescriptionException;
import chatterbot.tasks.*;

public class Parser {
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
        } else {
            throw new UnknownCommandException();
        }

        return true;
    }

    private static void handleTodoCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String description = userInput.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }
        Task newTask = new Todo(description);
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    private static void handleDeadlineCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String[] parts = userInput.substring(8).split(" /by ");
        if (parts.length < 2) {
            throw new EmptyDescriptionException("deadline");
        }
        Task newTask = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    private static void handleEventCommand(String userInput, TaskList tasks, Ui ui) throws EmptyDescriptionException {
        String[] parts = userInput.substring(5).split(" /from | /to ");
        if (parts.length < 3) {
            throw new EmptyDescriptionException("event");
        }
        Task newTask = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.addTask(newTask);
        ui.printAddedTask(newTask, tasks.size());
    }

    private static void handleMarkCommand(String userInput, TaskList tasks, Ui ui) {
        int taskIdx = Integer.parseInt(userInput.substring(5)) - 1;
        tasks.markTaskAsDone(taskIdx);
        ui.showMessage("Nice! I've marked this task as done:\n  " + tasks.getTask(taskIdx));
    }

    private static void handleUnmarkCommand(String userInput, TaskList tasks, Ui ui) {
        int taskIdx = Integer.parseInt(userInput.substring(7)) - 1;
        tasks.markTaskAsNotDone(taskIdx);
        ui.showMessage("OK, I've marked this task as not done yet:\n  " + tasks.getTask(taskIdx));
    }

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
}
