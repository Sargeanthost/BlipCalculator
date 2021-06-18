package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.NoSuchElementException;

public class Controller {
    private TierHelper tierHelper;

    private double blipTopHeight = 0;
    private double startingHeight = 0;
    private double blipBottomHeight = 0;

    private double lastBlipHeight = 0;

    @FXML private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML private TextField startingHeightInput;

    @FXML private TextField blipTopHeightInput;

    @FXML private TextField blipBottomHeightTextField;

    @FXML private TextArea outputTextArea;

    @FXML private TextField jumpApexTextField;

    @FXML private TextField nearestCombinedOffsetTextField;

    @FXML private Spinner<Integer> chainSpinner;

    @FXML
    private void initialize() {
        var helper = new Helper();
        tierHelper = new TierHelper();
        helper.setNumericFormatter(startingHeightInput);
        helper.setNumericFormatter(blipTopHeightInput);

        PrintStream printStream = new PrintStream(new Console(outputTextArea));
        System.setErr(printStream);
        System.setOut(printStream);
    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        e.consume();
        App.stage.setAlwaysOnTop(alwaysOnTopCheckMenuItem.isSelected());
    }

    @FXML
    private void generateBlipStatistics(ActionEvent e) {
        e.consume();
        // isn't persistent across builds as I expected, will make a stack exchange post later

        int chain = chainSpinner.getValue();
        try {
            startingHeight = Double.parseDouble(String.valueOf(startingHeightInput.getText()));
            blipTopHeight = Double.parseDouble(String.valueOf(blipTopHeightInput.getText()));
            blipBottomHeight =
                    Double.parseDouble(String.valueOf(blipBottomHeightTextField.getText()));
        } catch (Exception ignore) {
            System.out.println("Blip height and starting height not assigned");
        }

        calculateBlip(chain, startingHeight, blipTopHeight);
    }

    @FXML
    private void printOffsets(ActionEvent e) {
        e.consume();
        System.out.println("\nOffsets: ");
        //        .0 to .0125: td ceiling
        //        .1875 to .2: td floor
        //        .5 to .5125: bean + cake
        //        .5625 to .575: bed + piston

        //        String[] entranceArray = {"Trapdoor ceiling", "Trapdoor Floor", "Cocoa bean and
        // cake", "Bed and piston head"};
        tierHelper.getOffsetArrayStream().forEach(c -> System.out.println(lastBlipHeight + c));
    }

    private void calculateBlip(int chain, double startingHeight, final double blipTopHeight) {

        double heightDelta;
        double jumpApex = 0;
        double nearestCombinedOffset = 0;
        double nextOffset = 0;
        double nearestOffset = 0;

        for (int i = 0; i < chain; i++) {
            heightDelta = startingHeight - blipTopHeight;
            nearestCombinedOffset =
                    new BigDecimal(
                                    String.valueOf(
                                            tierHelper.getNearestOffset(
                                                    startingHeight, -heightDelta)),
                                    new MathContext(9, RoundingMode.FLOOR))
                            .doubleValue();
            jumpApex =
                    new BigDecimal(
                                    String.valueOf(tierHelper.getJumpApex(startingHeight)),
                                    new MathContext(9, RoundingMode.FLOOR))
                            .doubleValue();
            // poss checking
            nearestOffset =
                    new BigDecimal(
                                    String.valueOf(tierHelper.getOffset(-heightDelta)),
                                    new MathContext(9, RoundingMode.FLOOR))
                            .doubleValue(); // key from map
            nextOffset = tierHelper.getNextOffset(nearestOffset);

            startingHeight = nearestCombinedOffset;
        }

        System.out.println(
                tierHelper.isBlipPossible(
                                nearestOffset,
                                nearestCombinedOffset,
                                blipBottomHeight)
                        ? "Blip is possible."
                        : "Blip is not possible.");
        nearestCombinedOffsetTextField.setText(String.valueOf(nearestCombinedOffset));
        jumpApexTextField.setText(String.valueOf(jumpApex));

        lastBlipHeight = nearestCombinedOffset;
    }

    public static class Console extends OutputStream {
        private final TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) {
            appendText(String.valueOf((char) b));
        }
    }
}
