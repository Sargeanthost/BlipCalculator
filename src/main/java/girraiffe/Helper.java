package girraiffe;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class Helper {

    // TODO prevent negatives
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.0");
    private static final DecimalFormat integerFormat = new DecimalFormat("#");

    public static void setDecimalNumericFormatter(TextField textField) {
        textField.setTextFormatter(
                new TextFormatter<>(
                        c -> {
                            if (c.getControlNewText().isEmpty()) {
                                return c;
                            }
                            ParsePosition parsePosition = new ParsePosition(0);
                            Object object = decimalFormat.parse(c.getControlNewText(), parsePosition);
                            if (object == null
                                    || parsePosition.getIndex() < c.getControlNewText().length()) {
                                return null;
                            } else {
                                return c;
                            }
                        }));
    }
    public static void setIntegerNumericFormatter(TextField textField) {
        //TODO make this work
        integerFormat.setMaximumFractionDigits(0);
        textField.setTextFormatter(
                new TextFormatter<>(
                        c -> {
                            if (c.getControlNewText().isEmpty()) {
                                return c;
                            }
                            ParsePosition parsePosition = new ParsePosition(0);
                            Object object = integerFormat.parse(c.getControlNewText(), parsePosition);
                            if (object == null
                                    || parsePosition.getIndex() < c.getControlNewText().length()) {
                                return null;
                            } else {
                                return c;
                            }
                        }));
    }
}
