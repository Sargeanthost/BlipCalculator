package girraiffe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextField;

public class Controller {
    private Helper helper;
    private TierHelper tierHelper;
    private double heightDifference = 0;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private TextField startingYLevelInput;

    @FXML
    private TextField blipHeightInput;

    @FXML
    private void initialize() {
        helper = new Helper();
        tierHelper = new TierHelper();
        helper.setNumericFormatter(startingYLevelInput);
        helper.setNumericFormatter(blipHeightInput);
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

        heightDifference = startingHeight - blipHeight;
        System.out.println("\nHeight Difference: " + heightDifference);
        System.out.println("Blip Tier Offset: " +tierHelper.getBlipTier(-heightDifference));
        System.out.println("Blip Height: " + tierHelper.getDifferenceFromTierAndStartHeight(startingHeight, -heightDifference));
        System.out.println("Jump Apex: " + tierHelper.getNewApex(startingHeight));

        //2nd blip apex is at 54.47297
        //2nd blip is at 53.22378
        //2nd blip is calculated at 53.2238
        //2nd blip apex is calculated  at 54.4730

    }




}
