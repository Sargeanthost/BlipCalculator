package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {

    @FXML private CheckMenuItem alwaysOnTopCheckMenuItem;

    @FXML private TextField bcBlipBottomHeightTf;

    @FXML private TextField bcBlipPossibleTf;

    @FXML private TextField bcBlipTopHeightTf;

    @FXML private Spinner<Integer> bcChainS;

    @FXML private TextField bcJumpApexTf;

    @FXML private TextField bcMinimumBottomBlipHeightTf;

    @FXML private TextField bcNearestTierTf;

    @FXML private TextArea bcOutputTa;

    @FXML private TextField bcStartingHeightTf;

    @FXML private TextField bscInitSpeedBackwardsTf;

    @FXML private ComboBox<String> bscMmTypeCb;

    @FXML private TextField bscNumJumpsTf;

    @FXML private TextArea bscOutputTa;

    @FXML private Spinner<Integer> bscSlownessS;

    @FXML private Spinner<Integer> bscSwiftnessS;

    @FXML private TextField jcBoundaryTf;

    @FXML private CheckBox jcDelayedCb;

    @FXML private CheckBox jcIceCb;

    @FXML private TextField jcInitSpeedTf;

    @FXML private ComboBox<String> jcMmTypeCb;

    @FXML private TextArea jcOutputTa;

    @FXML private CheckBox jcSlimeCb;

    @FXML private Spinner<Integer> jcSlownessS;

    @FXML private Spinner<Integer> jcSwiftnessS;

    @FXML private TextField jcUpdaryTf;

    @FXML private TextField jcYLimitTf;

    @FXML private TextArea tcOutputTa;

    @FXML private TextField tcStartingHeightTf;

    @FXML private TabPane tabPane;

    private TierHelper tierHelper;
    private BlipCalculator blipCalculator;
    private float lastBlipHeight = 0;

    @FXML
    private void initialize() {
        tierHelper = new TierHelper();
        blipCalculator = new BlipCalculator();
        bscMmTypeCb
                .getItems()
                .addAll("No Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45");
        jcMmTypeCb
                .getItems()
                .addAll("No Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45");
        setNumericFormat();
        setStandardOut(new PrintStream(new Console(bcOutputTa)));
        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observableValue, oldTab, newTab) -> {
                            switch (newTab.getText()) {
                                case "Tier Calculator":
                                    setStandardOut(new PrintStream(new Console(tcOutputTa)));
                                    break;
                                case "Backwards Speed Calculator":
                                    setStandardOut(new PrintStream(new Console(bscOutputTa)));
                                    break;
                                case "Jump Calculator":
                                    setStandardOut(new PrintStream(new Console(jcOutputTa)));
                                    break;
                                default:
                                    setStandardOut(new PrintStream(new Console(bcOutputTa)));
                            }
                        });
    }

    private void setNumericFormat() {
        Helper.setNumericFormatter(bcStartingHeightTf);
        Helper.setNumericFormatter(bcBlipTopHeightTf);
        Helper.setNumericFormatter(bcBlipBottomHeightTf);
        Helper.setNumericFormatter(tcStartingHeightTf);
        Helper.setNumericFormatter(bscNumJumpsTf);
        Helper.setNumericFormatter(bscInitSpeedBackwardsTf);
        Helper.setNumericFormatter(jcInitSpeedTf);
        Helper.setNumericFormatter(jcYLimitTf);
        Helper.setNumericFormatter(jcUpdaryTf);
        Helper.setNumericFormatter(jcBoundaryTf);
    }

    public void setStandardOut(PrintStream printStream) {
        System.setErr(printStream);
        System.setOut(printStream);
    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        e.consume();
        App.stage.setAlwaysOnTop(alwaysOnTopCheckMenuItem.isSelected());
    }

    public void setLastBlipHeight(float lastBlipHeight) {
        this.lastBlipHeight = lastBlipHeight;
    }

    public void setBlipCalculatorOutput(
            String blipPossibleTf,
            String nearestTierTf,
            String jumpApexTf,
            String minimumBottomBlipHeightTf) {
        bcBlipPossibleTf.setText(blipPossibleTf);
        bcNearestTierTf.setText(nearestTierTf);
        bcJumpApexTf.setText(jumpApexTf);
        bcMinimumBottomBlipHeightTf.setText(minimumBottomBlipHeightTf);
    }

    @FXML
    private void bcGenerateBlipStatistics(ActionEvent e) {
        e.consume();
        try {
            blipCalculator.calculateBlip(
                    bcChainS.getValue(),
                    Float.parseFloat(bcStartingHeightTf.getText()),
                    Float.parseFloat(bcBlipTopHeightTf.getText()),
                    Float.parseFloat(bcBlipBottomHeightTf.getText()),
                    this);
        } catch (Exception ignore) {
            System.out.println("Blip height and starting height not assigned");
        }
    }

    @FXML
    private void bcPrintOffsets(ActionEvent e) {
        e.consume();
        System.out.println("Offsets (if any): ");
        tierHelper.newGenerateOffset(lastBlipHeight).stream()
                .map(tierHelper::entranceGenerator)
                .filter(s -> !s.equals(""))
                .forEach(System.out::println);
        System.out.println();
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
