package chatterbot;

import chatterbot.exceptions.EmptyDescriptionException;
import chatterbot.exceptions.UnknownCommandException;
import chatterbot.tasks.TaskList;

/**
 * Represents the ChatterBot chatbot application.
 * Handles user commands, interacts with the TaskList, and manages storage.
 */
public class ChatterBot {
    private static final String FILE_PATH = "data/chatterbot.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;


    /**
     * Constructs a ChatterBot instance.
     * Initializes UI, storage, and loads existing tasks from file.
     */
    public ChatterBot() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
        this.tasks = new TaskList(storage.loadTasks());
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input The user input string.
     * @return The chatbot's response.
     */
    public String getResponse(String input) {
        try {
            boolean shouldContinue = Parser.handleCommand(input, tasks, ui, storage);
            storage.saveTasks(tasks.getAllTasks());
            return ui.getLastMessage();
        } catch (EmptyDescriptionException | UnknownCommandException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred: " + e.getMessage();
        }
    }
}
