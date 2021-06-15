package girraiffe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        //MAYBE convert to integer and then floating point? it would get rid of error but not sure if i want to change all the types
        //TODO fixe fp errors
        App.stage = stage;
        Parent root = new FXMLLoader((App.class.getResource("primary.fxml"))).load();
        Scene scene = new Scene(root, 450, 345.0);
        stage.setResizable(false);
        stage.setTitle("Blip Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}