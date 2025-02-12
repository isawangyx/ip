package chatterbot;

import chatterbot.exceptions.UnknownCommandException;
import chatterbot.exceptions.EmptyDescriptionException;
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
     * Runs the chatbot, continuously accepting user input.
     */
    public void run() {
        ui.showWelcomeMessage();
        boolean isRunning = true;

        while (isRunning) {
            try {
                String userInput = ui.readCommand();
                isRunning = Parser.handleCommand(userInput, tasks, ui, storage);
                storage.saveTasks(tasks.getAllTasks());
            } catch (EmptyDescriptionException e) {
                ui.showMessage(e.getMessage());
            } catch (UnknownCommandException e) {
                ui.showMessage(e.getMessage());
            } catch (Exception e) {
                ui.showMessage("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * The main entry point of the chatbot application.
     * Initializes and runs ChatterBot.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new ChatterBot().run();
    }
}