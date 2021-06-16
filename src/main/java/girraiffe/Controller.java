package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Controller {
    private Helper helper;
    private TierHelper tierHelper;

    private double blipHeight = 0;
    private double startingHeight = 0;
    private  double blipBottomYLevel = 0;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private TextField startingYLevelInput;

    @FXML
    private TextField blipHeightInput;

    @FXML
    private TextField blipBottomYLevelTextField;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private TextField jumpApexTextField;

    @FXML
    private TextField nearestTierTextField;

    @FXML
    private Spinner<Integer> chainSpinner;

    @FXML
    private void initialize() {
        helper = new Helper();
        tierHelper = new TierHelper();
        helper.setNumericFormatter(startingYLevelInput);
        helper.setNumericFormatter(blipHeightInput);

        PrintStream printStream = new PrintStream(new Console(outputTextArea));
        System.setErr(printStream);
        System.setOut(printStream);

    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        App.stage.setAlwaysOnTop(alwaysOnTopCheckMenuItem.isSelected());
    }

    @FXML
    private void generateBlipStatistics(ActionEvent e) {
        //isn't persistent across builds as I expected, will make a stack exchange post later

        int chain = chainSpinner.getValue();
        try{
            startingHeight = Double.parseDouble(String.valueOf(startingYLevelInput.getText()));
            blipHeight = Double.parseDouble(String.valueOf(blipHeightInput.getText()));
            blipBottomYLevel = Double.parseDouble(String.valueOf(blipBottomYLevelTextField.getText()));
        } catch (Exception ignore){
            System.out.println("Blip height and starting height not assigned");
        }
        //do we have to return something for OCSV to catch?
        calculateBlip(chain, startingHeight, blipHeight);
    }

    @FXML
    private void saveCSV(){
    System.out.println("Save csv");
               //getting save dir
//        DirectoryChooser directoryChooser = new DirectoryChooser();
//        directoryChooser.setInitialDirectory(
//                new File(helper.PREFERENCES.get(helper.preferencesKeyArray[0], helper.DOCUMENTS_DIRECTORY)));
//        File selectedDirectory = directoryChooser.showDialog(App.stage);
//
//        try {
//            helper.PREFERENCES.put(helper.preferencesKeyArray[0], selectedDirectory.getAbsolutePath());
//        } catch (Exception ignored) {
//        }
//
//        try {
//            helper.PREFERENCES.flush();
//        } catch (BackingStoreException backingStoreException) {
//            backingStoreException.printStackTrace();
//        }
    }

    private void calculateBlip(int chain, double startingHeight, final double blipHeight) {

        double heightDifference;
        double jumpApex = 0;
        double nearestTierHeightOffset = 0;
        double nextTierOffset = 0;
        double nearestTierOffset = 0;

        for(int i = 0; i < chain; i++){
            heightDifference = startingHeight - blipHeight;
            nearestTierHeightOffset = new BigDecimal(String.valueOf(tierHelper.getNearestTier(startingHeight, -heightDifference)), new MathContext(9, RoundingMode.FLOOR)).doubleValue();
            jumpApex = new BigDecimal(String.valueOf(tierHelper.getNewApex(startingHeight)), new MathContext(9, RoundingMode.FLOOR)).doubleValue();
            //poss checking
            nearestTierOffset = new BigDecimal(String.valueOf(tierHelper.getTierOffset(-heightDifference)), new MathContext(9, RoundingMode.FLOOR)).doubleValue(); //key from map
            nextTierOffset = tierHelper.getPreviousTierOffset(nearestTierOffset);

            startingHeight = nearestTierHeightOffset;
        }

        System.out.println(tierHelper.isBlipPossible(nearestTierOffset, nextTierOffset, nearestTierHeightOffset, blipHeight, blipBottomYLevel) ? "Blip is possible." : "Blip is not possible.");
        nearestTierTextField.setText(String.valueOf(nearestTierHeightOffset));
        jumpApexTextField.setText(String.valueOf(jumpApex));
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
            appendText(String.valueOf((char)b));
        }
    }
}

