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
import java.util.stream.DoubleStream;

public class Controller {
    private TierHelper tierHelper;

    private float blipTopHeight = 0;
    private float startingHeight = 0;
    private float blipBottomHeight = 0;

    private float lastBlipHeight = 0;

    @FXML private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML private TextField startingHeightInput;

    @FXML private TextField blipTopHeightInput;

    @FXML private TextField blipBottomHeightTextField;

    @FXML private TextArea outputTextArea;

    @FXML private TextField jumpApexTextField;

    @FXML private TextField nearestCombinedOffsetTextField;

    @FXML private Spinner<Integer> chainSpinner;

    @FXML private TextField minimumBottomBlipHeightTextField;

    @FXML private TextField isBlipPossibleTextField;

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

        int chain = chainSpinner.getValue();
        try {
            startingHeight = Float.parseFloat(startingHeightInput.getText());
            blipTopHeight = Float.parseFloat(blipTopHeightInput.getText());
            blipBottomHeight = Float.parseFloat(blipBottomHeightTextField.getText());
        } catch (Exception ignore) {
            System.out.println("Blip height and starting height not assigned");
        }

        calculateBlip(chain, startingHeight, blipTopHeight);
    }

    @FXML
    private void printOffsets(ActionEvent e) {
        e.consume();
        System.out.println("\nOffsets (if any): ");
        //        .0 to .0125: td ceiling
        //        .1875 to .2: td floor
        //        .5 to .5125: cake + bean
        //        .5625 to .575: bed + piston

        var offsetStream = tierHelper.getOffsetArrayStream()
                .filter(d -> lastBlipHeight + (float)d > 0 &&
                        (((lastBlipHeight + (float)d) % 1 > 0 && (lastBlipHeight + (float)d) % 1<  .0125) ||
                        ((lastBlipHeight + (float)d) % 1 > .1875 && (lastBlipHeight + (float)d) % 1<  .2) ||
                        ((lastBlipHeight + (float)d) % 1 > .5 && (lastBlipHeight + (float)d) % 1<  .5125) ||
                        ((lastBlipHeight + (float)d) % 1 > .5625 && (lastBlipHeight + (float)d) % 1<  .575)))
                // will look at the case where lastBlipHeight + d is true and filter on this predicate, but then not
                // append the lastBlipHeight + d to the array, so a map is needed to do this for us
                .flatMap(d -> DoubleStream.of(lastBlipHeight + (float)d));
        offsetStream.forEach(System.out::println);
    }

    private void calculateBlip(int chain, float startingHeight, float blipTopHeight) {

        float heightDelta;
        float jumpApex = 0;
        float nearestCombinedOffset = 0;
        float nearestOffset = 0;

        for (int i = 0; i < chain; i++) {
            heightDelta = startingHeight - blipTopHeight;
            nearestCombinedOffset =
                    tierHelper.getNearestOffset(startingHeight, -heightDelta);
            jumpApex = tierHelper.getJumpApex(startingHeight);
            // poss checking
            nearestOffset = tierHelper.getOffset(-heightDelta); // key from map
            startingHeight = nearestCombinedOffset;
        }

        isBlipPossibleTextField.setText(
                tierHelper.isBlipPossible(
                                nearestOffset,
                                nearestCombinedOffset,
                                blipBottomHeight)
                        ? "Yes"
                        : "No");
        nearestCombinedOffsetTextField.setText(String.valueOf(nearestCombinedOffset));
        jumpApexTextField.setText(String.valueOf(jumpApex));
        minimumBottomBlipHeightTextField.setText(String.valueOf(tierHelper.minimumBottomBlipHeight));

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
