package girraiffe;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import javax.swing.filechooser.FileSystemView;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.prefs.Preferences;

public class Helper {
    public final String DOCUMENTS_DIRECTORY = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    public final Preferences PREFERENCES = Preferences.userNodeForPackage(App.class);
    public final String[] preferencesKeyArray = {"CSV_DIRECTORY"};

    private final DecimalFormat format = new DecimalFormat("#.0");

    public Helper(){
        initPrefs();
    }

    private void initPrefs() {
        PREFERENCES.put(preferencesKeyArray[0], DOCUMENTS_DIRECTORY);
    }

    public void setNumericFormatter(TextField textField){
        textField.setTextFormatter(new TextFormatter<>(c -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
    }
}
