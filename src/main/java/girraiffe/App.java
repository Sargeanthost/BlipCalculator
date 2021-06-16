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
        //TODO program doesn't seem to have an offset that would allow for blip failures.
        //carpet 1 tier above on quad try is 53.4789, which is calculated correctly, yet going one tier down im still
        //above .0625, yet in the game you're not.
        //0 tick on quad is .0313. carpet is .0625; do we have to ask for blip medium height...
        //TODO try to see where 0.4435 comes from -- FOUND 0.3434 + .1041
        //TODO calculate next tier. if the difference
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