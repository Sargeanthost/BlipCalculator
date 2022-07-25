package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;

import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {

    //menu
    @FXML private CheckMenuItem alwaysOnTopCmi;
    @FXML private MenuItem clearOutputMi;
    //blip calculator
    @FXML private TextField bcBlipBottomHeightTf;
    @FXML private TextField bcBlipPossibleTf;
    @FXML private TextField bcBlipTopHeightTf;
    @FXML private Spinner<Integer> bcChainS;
    @FXML private TextField bcJumpApexTf;
    @FXML private TextField bcMinimumBottomBlipHeightTf;
    @FXML private TextField bcNearestTierTf;
    @FXML private TextArea bcOutputTa;
    @FXML private TextField bcStartingHeightTf;
    //backwards speeds calculator
    @FXML private TextField bscInitSpeedBackwardsTf;
    @FXML private ComboBox<String> bscMmTypeCb;
    @FXML private TextField bscNumJumpsTf;
    @FXML private TextArea bscOutputTa;
    @FXML private Spinner<Integer> bscSlownessS;
    @FXML private TextField bscJumpAngleTf;
    @FXML private Spinner<Integer> bscSwiftnessS;
    @FXML private ComboBox<String> bscJumpTypeCb;
    @FXML private ComboBox<String> bscStrafeCb;
    //jump calculator
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
    //tier calculator
    @FXML private TextArea tcOutputTa;
    @FXML private TextField tcStartingHeightTf;
    @FXML private CheckBox tcIsJump;
    //global
    @FXML private TabPane tabPane;
    private BlipCalculator blipCalculator;
    private TierCalculator tierCalculator;
    private Console bcConsole;
    private Console tcConsole;
    private Console bscConsole;
    private Console jcConsole;

    @FXML
    private void initialize() {
        //call these inside calculate function
        blipCalculator = new BlipCalculator();
        tierCalculator = new TierCalculator();
        bcConsole = new Console(bcOutputTa);
        tcConsole = new Console(tcOutputTa);
        bscConsole = new Console(bscOutputTa);
        jcConsole = new Console(jcOutputTa);

        setComboBoxes();
        setNumericFormat();
        initOutputPaneListener();
    }

    private void initOutputPaneListener() {
        //comment out to get sout to terminal
        setStandardOut(new PrintStream(bcConsole));
        tabPane.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observableValue, oldTab, newTab) -> {
                            switch (newTab.getText()) {
                                case "Tier Calculator":
                                    setStandardOut(new PrintStream(tcConsole));
                                    break;
                                case "Backwards Speed Calculator":
                                    setStandardOut(new PrintStream(bscConsole));
                                    break;
                                case "Jump Calculator":
                                    setStandardOut(new PrintStream(jcConsole));
                                    break;
                                default:
                                    setStandardOut(new PrintStream(bcConsole));
                            }
                        });
    }

    private void setComboBoxes() {
        // .98  1  1.000048  1.00306323  1.08802
        bscMmTypeCb
                .getItems()
                .addAll("No 45 Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45");
        bscMmTypeCb.getSelectionModel().selectFirst();
        bscStrafeCb
                .getItems()
                .addAll("No", "Yes");
        bscStrafeCb.getSelectionModel().selectFirst();
        //check for strafe on each of these
        bscJumpTypeCb
                .getItems()
                .addAll("Jam", "Fmm", "Pessi");
        bscJumpTypeCb.getSelectionModel().selectFirst();
        jcMmTypeCb
                .getItems()
                .addAll("No 45 Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45");
        jcMmTypeCb.getSelectionModel().selectFirst();
    }

    private void setNumericFormat() {
        Helper.setDecimalNumericFormatter(bcStartingHeightTf);
        Helper.setDecimalNumericFormatter(bcBlipTopHeightTf);
        Helper.setDecimalNumericFormatter(bcBlipBottomHeightTf);
        Helper.setDecimalNumericFormatter(tcStartingHeightTf);
        Helper.setDecimalNumericFormatter(bscNumJumpsTf);
        Helper.setDecimalNumericFormatter(bscInitSpeedBackwardsTf);
        Helper.setDecimalNumericFormatter(jcInitSpeedTf);
        Helper.setDecimalNumericFormatter(jcYLimitTf);
        Helper.setDecimalNumericFormatter(jcUpdaryTf);
        Helper.setDecimalNumericFormatter(jcBoundaryTf);
    }

    @FXML
    private void isStageAlwaysOnTop(ActionEvent e) {
        e.consume();
        App.stage.setAlwaysOnTop(alwaysOnTopCmi.isSelected());
    }

    @FXML
    private void clearOutput(ActionEvent e) {
        //cant think of another way without reflection hell, and cant target current textarea so have to just clear them all
        bcConsole.console.clear();
        tcConsole.console.clear();
        bscConsole.console.clear();
        jcConsole.console.clear();

    }

    @FXML
    private void bcCalculateAndPrint(ActionEvent e) {
        e.consume();
        try {
            float topHeight = Float.parseFloat(bcBlipTopHeightTf.getText());
            float bottomHeight = Float.parseFloat(bcBlipBottomHeightTf.getText());
            blipCalculator.calculateBlip(
                    bcChainS.getValue(),
                    Float.parseFloat(bcStartingHeightTf.getText()),
                    topHeight,
                    bottomHeight,
                    this);
        } catch (Exception ignore) {
            System.out.println(
                    "Starting height, blip top height, or blip bottom height has not been assigned!");
        }
    }

    @FXML
    private void tcCalculateAndPrint(ActionEvent e) {
        e.consume();
        try {
            tierCalculator.calculateAndOutputTier(
                    Float.parseFloat(tcStartingHeightTf.getText()), tcIsJump.isSelected());
        } catch (Exception ignore) {
            System.out.println("Starting height has not been assigned!");
        }
    }

    @FXML
    private void bscCalculateAndPrint(ActionEvent e){
        e.consume();
        try{
            BackwardsSpeedCalculator bsc = new BackwardsSpeedCalculator(
                    Integer.parseInt(bscNumJumpsTf.getText()),
                    bscSwiftnessS.getValue(),
                    bscSlownessS.getValue(),
                    Float.parseFloat(bscInitSpeedBackwardsTf.getText()),
                    Float.parseFloat(bscJumpAngleTf.getText()),
                    bscMmTypeCb.getValue(),
                    bscJumpTypeCb.getValue(),
                    Boolean.valueOf(bscStrafeCb.getValue())
            );
        } catch (Exception ignore){
            System.out.println("One of the variables has not been assigned!");
        }
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

    public void setStandardOut(PrintStream printStream) {
        System.setErr(printStream);
        System.setOut(printStream);
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
