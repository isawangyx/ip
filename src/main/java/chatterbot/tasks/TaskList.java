package chatterbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     * Finds the nearest free slot of the given duration.
     *
     * @param durationInHours The required free duration (in hours).
     * @return A string representing the nearest free time slot.
     */
    public String findFreeTime(int durationInHours) {
        List<Event> events = new ArrayList<>();
        for (Task task : tasks) {
            if (task instanceof Event) {
                events.add((Event) task);
            }
        }
        events.sort(Comparator.comparing(Event::getStartTime));

        LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

        for (int dayOffset = 0; dayOffset < 7; dayOffset++) {  // Check next 7 days
            LocalDateTime searchStart = now.plusDays(dayOffset);

            // Define working hours from 8 AM to 10 PM
            LocalDateTime startOfDay = searchStart.withHour(8).withMinute(0);
            LocalDateTime endOfDay = searchStart.withHour(22).withMinute(0);

            // If it's today, start from current time; otherwise, from 8 AM
            if (dayOffset == 0 && now.isAfter(startOfDay) && now.isBefore(endOfDay)) {
                searchStart = now;
            } else {
                searchStart = startOfDay;
            }

            LocalDateTime lastEndTime = searchStart;

            for (Event event : events) {
                LocalDateTime start = event.getStartTime();
                LocalDateTime end = event.getEndTime();

                // Skip past events
                if (end.isBefore(lastEndTime)) {
                    continue;
                }

                if (ChronoUnit.HOURS.between(lastEndTime, start) >= durationInHours
                        && lastEndTime.plusHours(durationInHours).isBefore(endOfDay)) {
                    return formatFreeSlot(lastEndTime, durationInHours);
                }

                lastEndTime = end;
            }

            if (ChronoUnit.HOURS.between(lastEndTime, endOfDay) >= durationInHours) {
                return formatFreeSlot(lastEndTime, durationInHours);
            }
        }

        return "No free slots available in the next 7 days for " + durationInHours + " hours.";
    }

    /**
     * Formats the output message for a free slot.
     */
    private String formatFreeSlot(LocalDateTime startTime, int durationInHours) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy h:mm a");

        return "The nearest " + durationInHours + "-hour free slot is on "
                + startTime.format(formatter) + " to "
                + startTime.plusHours(durationInHours).format(formatter);
    }
}