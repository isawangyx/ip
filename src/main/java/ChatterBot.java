import java.util.Scanner;
import java.util.ArrayList;

public class ChatterBot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm ChatterBot");
        System.out.println("What can I do for you?");

        while (true) {
            String userInput = sc.nextLine();

            if (userInput.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            } else if (userInput.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (userInput.startsWith("todo ")) {
                String description = userInput.substring(5);
                Task newTask = new Todo(description);
                tasks.add(newTask);
                printAddedTask(newTask, tasks.size());
            } else if (userInput.startsWith("deadline ")) {
                String[] parts = userInput.substring(9).split(" /by ");
                String description = parts[0];
                String by = parts[1];
                Task newTask = new Deadline(description, by);
                tasks.add(newTask);
                printAddedTask(newTask, tasks.size());
            } else if (userInput.startsWith("event ")) {
                String[] parts = userInput.substring(6).split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];
                Task newTask = new Event(description, from, to);
                tasks.add(newTask);
                printAddedTask(newTask, tasks.size());
            } else if (userInput.startsWith("mark ")) {
                int taskIdx = Integer.parseInt(userInput.substring(5));
                if (taskIdx >= 0 && taskIdx < tasks.size()) {
                    tasks.get(taskIdx - 1).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(taskIdx - 1));
                }
            } else if (userInput.startsWith("unmark ")) {
                int taskIdx = Integer.parseInt(userInput.substring(7));
                if (taskIdx >= 0 && taskIdx < tasks.size()) {
                    tasks.get(taskIdx - 1).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(taskIdx - 1));
                }
            } else {
                Task newTask = new Task(userInput);
                tasks.add(newTask);
                System.out.println("added: " + userInput);
            }
        }

    }

    private static void printAddedTask(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }
}
