package girraiffe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;

public class App extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        //TODO add chain option so you dont have to plug in computed values
        //TODO bind System.out.println() to a text area in the gui
        //TODO add chain check mark and display last blip statistics - WORKING
        App.stage = stage;
        Parent root = new FXMLLoader((App.class.getResource("primary.fxml"))).load();
        Scene scene = new Scene(root, 450, 300.0);
        stage.setResizable(false);
        stage.setTitle("Blip Calculator");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}