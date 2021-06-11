package girraiffe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.prefs.BackingStoreException;

public class Controller {
    private Helper helper;

    @FXML
    private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML
    private TextField startingYLevelInput;

    @FXML
    private TextField lowestBlipMediumYLevelInput;

    @FXML
    private TextField highestBlipMediumYLevelInput;

    @FXML
    private void initialize() {
        helper = new Helper();

        helper.setNumericFormatter(startingYLevelInput);
        helper.setNumericFormatter(lowestBlipMediumYLevelInput);
        helper.setNumericFormatter(highestBlipMediumYLevelInput);
    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        App.stage.setAlwaysOnTop(alwaysOnTopCheckMenuItem.isSelected());
    }

    //remake this so that when the generate button is clicked all the math and file choosing happens there.
    @FXML
    private void openCsvFileLocation(ActionEvent e) {
        //isn't persistent across builds as I expected, will make a stack exchange post later
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(
                new File(helper.PREFERENCES.get(helper.preferencesKeyArray[0], helper.DOCUMENTS_DIRECTORY)));
        File selectedDirectory = directoryChooser.showDialog(App.stage);

        try {
            helper.PREFERENCES.put(helper.preferencesKeyArray[0], selectedDirectory.getAbsolutePath());
        } catch (Exception ignored) {
        }

        try {
            helper.PREFERENCES.flush();
        } catch (BackingStoreException backingStoreException) {
            backingStoreException.printStackTrace();
        }
    }

    @FXML
    public void generateResults(ActionEvent e){
        System.out.println("click");
    }


}
