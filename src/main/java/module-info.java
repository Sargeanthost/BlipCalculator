module girraiffe.parkourTool {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.desktop;

    opens girraiffe to javafx.fxml;
    exports girraiffe;
}