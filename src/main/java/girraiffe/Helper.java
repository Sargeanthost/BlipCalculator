package girraiffe;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.prefs.Preferences;

import javax.swing.filechooser.FileSystemView;

public class Helper {

    // TODO prevent negatives
    private static final DecimalFormat format = new DecimalFormat("#.0");

    public static void setNumericFormatter(TextField textField) {
        textField.setTextFormatter(
                new TextFormatter<>(
                        c -> {
                            if (c.getControlNewText().isEmpty()) {
                                return c;
                            }
                            ParsePosition parsePosition = new ParsePosition(0);
                            Object object = format.parse(c.getControlNewText(), parsePosition);
                            if (object == null
                                    || parsePosition.getIndex() < c.getControlNewText().length()) {
                                return null;
                            } else {
                                return c;
                            }
                        }));
    }
}
