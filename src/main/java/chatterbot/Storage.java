package chatterbot;

import chatterbot.tasks.Task;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
