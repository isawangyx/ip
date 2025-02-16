package chatterbot.ui;

import chatterbot.ChatterBot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private ChatterBot chatterbot;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image chatterbotImage = new Image(this.getClass().getResourceAsStream("/images/aibot.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the ChatterBot instance */
    public void setChatterBot(ChatterBot c) {
        chatterbot = c;

        String welcomeMessage = "Hello! I'm ChatterBot\nWhat can I do for you?";
        dialogContainer.getChildren().add(DialogBox.getChatterBotDialog(welcomeMessage, chatterbotImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chatterbot.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getChatterBotDialog(response, chatterbotImage)
        );
        userInput.clear();
    }
}

