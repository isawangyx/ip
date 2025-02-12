package chatterbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void testAddTask() {
        TaskList tasks = new TaskList();
        Task task = new Todo("Read book");
        tasks.addTask(task);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.getTask(0));
    }

    @Test
    public void testRemoveTask() {
        TaskList tasks = new TaskList();
        Task task = new Todo("Read book");
        tasks.addTask(task);
        tasks.removeTask(0);
        assertEquals(0, tasks.size());
    }
}
