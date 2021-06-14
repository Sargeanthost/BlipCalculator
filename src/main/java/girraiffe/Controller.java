package girraiffe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextField;

public class Controller {
    private Helper helper;
    private TierHelper tierHelper;
    private double yDifference = 0;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private TextField startingYLevelInput;

    @FXML
    private TextField highestBlipMediumYLevelInput;

    @FXML
    private void initialize() {
        helper = new Helper();
        tierHelper = new TierHelper();
        helper.setNumericFormatter(startingYLevelInput);
        helper.setNumericFormatter(highestBlipMediumYLevelInput);
    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        App.stage.setAlwaysOnTop(alwaysOnTopCheckMenuItem.isSelected());
    }

    //remake this so that when the generate button is clicked all the math and file choosing happens there.

    @FXML
    private void generateResults(ActionEvent e) {
        //isn't persistent across builds as I expected, will make a stack exchange post later
        var yLevel = 0.0;
        var higherBlipYLevel = 0.0;
        try{
        yLevel = Double.parseDouble(String.valueOf(startingYLevelInput.getText()));
        higherBlipYLevel = Double.parseDouble(String.valueOf(highestBlipMediumYLevelInput.getText()));
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
        yDifference = yLevel - higherBlipYLevel;
        System.out.println(yDifference);
        System.out.println(tierHelper.getBlipTier(-yDifference));
        System.out.println(tierHelper.getNewApex() + yDifference);
    }




}
