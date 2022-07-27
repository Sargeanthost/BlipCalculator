module girraiffe.parkourTool {
    requires javafx.controls;
    requires javafx.fxml;

    opens girraiffe to javafx.fxml;
    exports girraiffe;
}