package girraiffe;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * Currently only helps with formatting text fields.
 */
public class Helper {

    // TODO prevent negatives
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.0");

    /**
     * Formats a text field to a decimal format.
     *
     * @param textField the text field to format
     */
    public static void setDecimalNumericFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }
            ParsePosition parsePosition = new ParsePosition(0);
            Object object = decimalFormat.parse(c.getControlNewText(), parsePosition);
            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
    }
    // TODO make integer formatter
}
