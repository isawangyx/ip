package chatterbot.tasks;  //same package as the class being tested

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {

    @Test
    public void testTodoCreation() {
        Todo task = new Todo("Read book");
        assertEquals("Read book", task.description);
        assertEquals(false, task.isDone);
    }

    @Test
    public void testMarkAsDone() {
        Todo task = new Todo("Read book");
        task.markAsDone();
        assertEquals(true, task.isDone);
    }

    @Test
    public void testMarkAsNotDone() {
        Todo task = new Todo("Read book");
        task.markAsDone();  // Mark as done first
        task.markAsNotDone();
        assertEquals(false, task.isDone);
    }

    @Test
    public void testToFileFormat() {
        Todo task = new Todo("Read book");
        assertEquals("T | 0 | Read book", task.toFileFormat());

        task.markAsDone();
        assertEquals("T | 1 | Read book", task.toFileFormat());
    }

    @Test
    public void testToString() {
        Todo task = new Todo("Read book");
        assertEquals("[T][ ] Read book", task.toString());

        task.markAsDone();
        assertEquals("[T][X] Read book", task.toString());
    }
}
