package chatterbot;

import chatterbot.tasks.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing tasks to a file for persistence.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage instance.
     *
     * @param filePath The path of the file used for storage.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     */
    public void saveTasks(List<Task> tasks) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs(); // Create directory if it doesn’t exist
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving chatterbot.tasks: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return A list of tasks loaded from the file.
     */
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = reader.readLine()) != null) {
                Task task = Task.fromFileFormat(str);
                if (task != null) {
                    tasks.add(task);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading chatterbot.tasks: " + e.getMessage());
        }
        return tasks;
    }
}
