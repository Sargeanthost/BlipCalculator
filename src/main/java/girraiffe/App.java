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
        App.stage = stage;
        //https://stackoverflow.com/a/10122335
        Parent root = new FXMLLoader((App.class.getResource("primary.fxml"))).load();
        Scene scene = new Scene(root, 600, 320);
        stage.setResizable(false);
        stage.setTitle("Parkour Tool");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
