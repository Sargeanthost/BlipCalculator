package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {
    private Helper helper;
    private TierHelper tierHelper;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private TextField startingYLevelInput;

    @FXML
    private TextField blipHeightInput;

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
        //TODO update tier values to labels instead of console and get rid (?) of console.
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

    //remake this so that when the generate button is clicked all the math happens and have another button to save those offsets.

    @FXML
    private void generateResults(ActionEvent e) {
        //isn't persistent across builds as I expected, will make a stack exchange post later
        var startingHeight = 0.0;
        var blipHeight = 0.0;
        int chain =chainSpinner.getValue();
        try{
        startingHeight = Double.parseDouble(String.valueOf(startingYLevelInput.getText()));
        blipHeight = Double.parseDouble(String.valueOf(blipHeightInput.getText()));
        } catch (Exception ignore){
        }
//        //getting save dir
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
        //writing csv

        calculateBlip(chain, startingHeight, blipHeight);
//        double heightDifference = startingHeight - blipHeight;

        //tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference)
        //tierHelper.getNewApex(startingHeight)
//        nearestTierTextField.setText(String.valueOf(tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference))); // starting height - tier offset
//        jumpApexTextField.setText(String.valueOf(tierHelper.getNewApex(startingHeight)));
    }

    private void calculateBlip(int chain, double startingHeight, final double blipHeight) {
        //blipHeight will be the y level of the blip -- constant
        //blipOffset will be the startingHeight - tier offset of where you blip at -- recompute
        //jump apex is the jump of where you blip at -- recompute
        //starting height will be the blip tier offset -- recompute

        //get rid of assignment to math here
        double heightDifference = 0;
        double jumpApex = 0;
        double nearestTier = 0;

    System.out.println(chain);
        for(int i = 0; i < chain; i++){
            //calculates .1041 too high somehow
            heightDifference = startingHeight - blipHeight;
            nearestTier = tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference);
            jumpApex = tierHelper.getNewApex(startingHeight); // done
            System.out.println(nearestTier); //106.26169999999999
            System.out.println(jumpApex); // done  107.51089999999999

            startingHeight = nearestTier;
            System.out.println("I: " + i);
        }
        System.out.println("\n" + nearestTier);
        System.out.println(jumpApex);
        nearestTierTextField.setText(String.valueOf(nearestTier)); // starting height - tier offset
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

        public void write(int b) throws IOException {
            appendText(String.valueOf((char)b));
        }
    }
}

