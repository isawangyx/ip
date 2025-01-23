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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + ". " + tasks.get(i));
                }
            } else if (userInput.startsWith("mark ")) {
                int taskIdx = Integer.parseInt(userInput.substring(5));
                if (taskIdx >= 0 && taskIdx < tasks.size()) {
                    tasks.get(taskIdx - 1).markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(taskIdx - 1));
                } else {
                    System.out.println(" Invalid task number.");
                }
            } else if (userInput.startsWith("unmark ")) {
                int taskIdx = Integer.parseInt(userInput.substring(7));
                if (taskIdx >= 0 && taskIdx < tasks.size()) {
                    tasks.get(taskIdx - 1).markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(taskIdx - 1));
                } else {
                    System.out.println(" Invalid task number.");
                }
            } else {
                Task newTask = new Task(userInput);
                tasks.add(newTask);
                System.out.println("added: " + userInput);
            }
        }

    }
}
