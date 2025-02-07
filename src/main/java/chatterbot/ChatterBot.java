package chatterbot;

import chatterbot.exceptions.UnknownCommandException;
import chatterbot.exceptions.EmptyDescriptionException;
import chatterbot.tasks.TaskList;

public class ChatterBot {
    private static final String FILE_PATH = "data/chatterbot.txt";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public ChatterBot() {
        this.ui = new Ui();
        this.storage = new Storage(FILE_PATH);
        this.tasks = new TaskList(storage.loadTasks());
    }

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

    public static void main(String[] args) {
        new ChatterBot().run();
    }
}