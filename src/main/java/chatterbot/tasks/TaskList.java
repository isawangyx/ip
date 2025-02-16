package chatterbot.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import chatterbot.Ui;

/**
 * Manages a list of tasks and provides methods to add, remove, and modify tasks.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with a given list of tasks.
     *
     * @param tasks The list of tasks to initialize the TaskList with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        assert task != null : "Task to be added cannot be null";
        tasks.add(task);
    }

    /**
     * Removes a task from the task list.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     */
    public Task removeTask(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds for removing task";
        return tasks.remove(index);
    }

    /**
     * Retrieves a specific task from the task list.
     *
     * @param index The index of the task to retrieve.
     * @return The requested task.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the total number of tasks in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the list of tasks.
     *
     * @return The task list.
     */
    public List<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Marks a task as done.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTaskAsDone(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done.
     *
     * @param index The index of the task to unmark.
     */
    public void markTaskAsNotDone(int index) {
        tasks.get(index).markAsNotDone();
    }

    /**
     * Prints all tasks in the task list.
     * If the task list is empty, it notifies the user.
     *
     * @param ui The UI used to display messages.
     */
    public void printTasks(Ui ui) {
        if (tasks.isEmpty()) {
            ui.showMessage("Your task list is empty!");
            return;
        }

        String taskList = tasks.stream()
                .map(task -> (tasks.indexOf(task) + 1) + ". " + task)
                .collect(Collectors.joining("\n"));

        ui.showMessage("Here are the tasks in your list:\n" + taskList);
    }


    /**
     * Finds and returns a list of tasks that contain the given keyword.
     *
     * @param keyword The search keyword.
     * @return A list of tasks matching the keyword.
     */
    public List<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.description.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

}
