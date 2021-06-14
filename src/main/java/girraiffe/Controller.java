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
    TextArea outputTextArea;

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

    //remake this so that when the generate button is clicked all the math and file choosing happens there.

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
        System.out.println("\nHeight Difference: " + heightDifference);
        System.out.println("Blip Tier Offset: " +tierHelper.getBlipTier(-heightDifference));
        System.out.println("Blip Height: " + tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference));
        System.out.println("Jump Apex: " + tierHelper.getNewApex(startingHeight));

        //2nd blip apex is at 54.47297
        //2nd blip is at 53.22378
        //2nd blip is calculated at 53.2238
        //2nd blip apex is calculated  at 54.4730

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

