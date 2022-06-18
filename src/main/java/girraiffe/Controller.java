package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {
    private TierHelper tierHelper;

    private static float blipTopHeight = 0;
    private float startingHeight = 0;
    private static float blipBottomHeight = 0;
    private float lastBlipHeight = 0;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private CheckMenuItem is1_9MenuItem;

    @FXML
    private TextField startingHeightInput;

    @FXML
    private TextField blipTopHeightInput;

    @FXML
    private TextField blipBottomHeightTextField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private TextField jumpApexTextField;

    @FXML
    private TextField nearestCombinedOffsetTextField;

    @FXML
    private Spinner<Integer> chainSpinner;

    @FXML
    private TextField minimumBottomBlipHeightTextField;

    @FXML
    private TextField isBlipPossibleTextField;

    private boolean is1_9 = false;

    @FXML
    private void initialize() {
        var helper = new Helper();
        tierHelper = new TierHelper();
        helper.setNumericFormatter(startingHeightInput);
        helper.setNumericFormatter(blipTopHeightInput);
        helper.setNumericFormatter(blipBottomHeightTextField);

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
        System.out.println("Offsets (if any): ");
        tierHelper.newGenerateOffset(lastBlipHeight).stream().map(tierHelper::entranceGenerator).filter(s -> !s.equals("")).forEach(System.out::println);
        System.out.println();
    }

    @FXML
    void is1_9(ActionEvent event) {
        event.consume();
        is1_9 = is1_9MenuItem.isSelected();
    }

    private void calculateBlip(int chain, float startingBlipHeight, float blipTopHeight) {
        float heightDelta;
        float jumpApex = 0;
        float nearestCombinedOffset = 0;

        for (int i = 0; i < chain; i++) {
            heightDelta = startingBlipHeight - blipTopHeight;
            tierHelper.calculateOffsets(heightDelta, is1_9);
            nearestCombinedOffset = startingBlipHeight + tierHelper.get_offset();
            jumpApex = tierHelper.calculateJumpApex(nearestCombinedOffset);
            startingBlipHeight = nearestCombinedOffset;
        }

        isBlipPossibleTextField.setText(
                tierHelper.isBlipPossible(nearestCombinedOffset, blipBottomHeight) ? "Yes" : "No");
        nearestCombinedOffsetTextField.setText(String.valueOf(nearestCombinedOffset));
        jumpApexTextField.setText(String.valueOf(jumpApex));
        minimumBottomBlipHeightTextField.setText(String.valueOf(tierHelper.getMinimumBottomBlipHeight()));

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