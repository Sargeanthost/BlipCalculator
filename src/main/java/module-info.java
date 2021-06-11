module girraiffe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.prefs;

    opens girraiffe to javafx.fxml;
    exports girraiffe;
}
