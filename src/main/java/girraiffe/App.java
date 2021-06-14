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
        //TODO add chain option so you dont have to plug in computed values
        App.stage = stage;
        Parent root = new FXMLLoader((App.class.getResource("primary.fxml"))).load();
        Scene scene = new Scene(root, 290, 165);
        stage.setResizable(false);
        stage.setTitle("Blip Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }



}