package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Text;

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
    private TextField nearstTierTextField;

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

        double heightDifference = startingHeight - blipHeight;
        //TODO make some of this labels
        System.out.println("\nHeight Difference: " + heightDifference);
        nearstTierTextField.setText(String.valueOf(tierHelper.getBlipTierOffset(-heightDifference)));
        System.out.println("Blip Height: " + tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference));
        jumpApexTextField.setText(String.valueOf(tierHelper.getNewApex(startingHeight)));
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

