import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showWelcomeMessage() {
        System.out.println("Hello! I'm ChatterBot");
        System.out.println("What can I do for you?");
    }

    public void showExitMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}


