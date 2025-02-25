package chatterbot.ui;

import java.io.IOException;

import chatterbot.ChatterBot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for ChatterBot using FXML.
 */
public class Main extends Application {

    private ChatterBot chatterbot = new ChatterBot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setChatterBot(chatterbot);  // inject the ChatterBot instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
