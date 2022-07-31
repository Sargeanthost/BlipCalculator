package girraiffe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    //menu
    @FXML private CheckMenuItem alwaysOnTopCmi;
    //blip calculator
    @FXML private TextField bcStartingHeightTf;
    @FXML private TextField bcBlipBottomHeightTf;
    @FXML private TextField bcBlipPossibleTf;
    @FXML private TextField bcBlipTopHeightTf;
    @FXML private TextField bcJumpApexTf;
    @FXML private TextField bcMinimumBottomBlipHeightTf;
    @FXML private TextField bcNearestTierTf;
    @FXML private Spinner<Integer> bcChainS;
    @FXML private TextArea bcOutputTa;
    //backwards speeds calculator
    @FXML private TextField bscInitSpeedBackwardsTf;
    @FXML private TextField bscNumJumpsTf;
    @FXML private TextField bscJumpAngleTf;
    @FXML private ComboBox<String> bscMmTypeCb;
    @FXML private ComboBox<String> bscStrafeCb;
    @FXML private ComboBox<String> bscJumpTypeCb;
    @FXML private Spinner<Integer> bscSwiftnessS;
    @FXML private Spinner<Integer> bscTierMmS;
    @FXML private Spinner<Integer> bscSlownessS;
    @FXML private TextArea bscOutputTa;
    //jump calculator
    @FXML private TextField jcBoundaryTf;
    @FXML private TextField jcInitSpeedTf;
    @FXML private TextField jcYLimitTf;
    @FXML private TextField jcUpdaryTf;
    @FXML private CheckBox jcDelayedCb;
//    @FXML private CheckBox jcAirborneCb;
    @FXML private ComboBox<String> jcFloorTypeCb;
    @FXML private ComboBox<String> jcMmTypeCb;
    @FXML private Spinner<Integer> jcSlownessS;
    @FXML private Spinner<Integer> jcSwiftnessS;
    @FXML private TextArea jcOutputTa;
    //tier calculator
    @FXML private TextField tcStartingHeightTf;
    @FXML private CheckBox tcIsJump;
    @FXML private TextArea tcOutputTa;
    //slime calculator
    @FXML private TextField sStartingHeightTf;
    @FXML private TextField sSlimeHeightTf;
    @FXML private CheckBox sIsJumpCb;
    @FXML private TextArea sOutputTa;
    //global
    @FXML private TabPane tabPane;
    private Console bcConsole;
    private Console tcConsole;
    private Console bscConsole;
    private Console jcConsole;
    private Console sConsole;

    @FXML
    private void initialize() {
        //call these inside calculate function
        bcConsole = new Console(bcOutputTa);
        tcConsole = new Console(tcOutputTa);
        bscConsole = new Console(bscOutputTa);
        jcConsole = new Console(jcOutputTa);
        sConsole = new Console(sOutputTa);

        initComboBoxes();
        initNumericFormat();
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
                                case "Tier" -> setStandardOut(new PrintStream(tcConsole));
                                case "Slime" -> setStandardOut(new PrintStream(sConsole));
//                                case "Landing" -> setStandardOut(new PrintStream(lConsole));
                                case "Backwards Speed Block" -> setStandardOut(new PrintStream(bscConsole));
//                                case "Backwards Speed Input" -> setStandardOut(new PrintStream(bsiConsole));
                                case "Jump" -> setStandardOut(new PrintStream(jcConsole));
                                default -> setStandardOut(new PrintStream(bcConsole));
                            }
                        });
    }

    private void initComboBoxes() {
        bscMmTypeCb
                .getItems()
                .setAll(new ArrayList<>(List.of("No 45 Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45")));
        bscMmTypeCb.getSelectionModel().selectFirst();
        bscStrafeCb
                .getItems()
                .setAll(new ArrayList<>(List.of("No", "Yes")));
        bscStrafeCb.getSelectionModel().selectFirst();
        bscJumpTypeCb
                .getItems()
                .setAll(new ArrayList<>(List.of("Jam", "Fmm", "Pessi")));
        bscJumpTypeCb.getSelectionModel().selectFirst();
        jcMmTypeCb
                .getItems()
                .setAll(new ArrayList<>(List.of("No 45 Strafe", "45 Strafe", "Half Angle", "Cyn 45", "Optifine 45")));
        jcMmTypeCb.getSelectionModel().selectFirst();
        jcFloorTypeCb
                .getItems()
                .setAll(new ArrayList<>(List.of("Normal", "Ice", "Slime")));
        jcFloorTypeCb.getSelectionModel().selectFirst();
    }

    private void initNumericFormat() {
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
    private void clearAllOutputs(ActionEvent e) {
        e.consume();
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == Console.class) {
                try {
                    Method clear = field.getType().getDeclaredMethod("clear");
                    clear.invoke(field.get(this));
                } catch (NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    @FXML
    private void clearCurrentOutput(ActionEvent e) {
        e.consume();
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        switch (currentTab.getText()) {
            case "Backwards Speed Block" -> bscConsole.console.clear();
//            case "Backwards Speed Input" -> bsiConsole.console.clear();
            case "Jump" -> jcConsole.console.clear();
            case "Tier" -> tcConsole.console.clear();
//            case "Slime" -> sConsole.console.clear();
//            case "Landing" -> lConsole.console.clear();
            default -> bcConsole.console.clear();
        }
    }
    private void setStandardOut(PrintStream printStream) {
        System.setErr(printStream);
        System.setOut(printStream);
    }

    @FXML
    private void bcCalculateAndPrint(ActionEvent e) {
        e.consume();
        try {
            BlipCalculator blipCalculator = new BlipCalculator();
            blipCalculator.calculateBlip(
                    bcChainS.getValue(),
                    Float.parseFloat(bcStartingHeightTf.getText()),
                    Float.parseFloat(bcBlipTopHeightTf.getText()),
                    Float.parseFloat(bcBlipBottomHeightTf.getText()),
                    this);
        } catch (Exception ignore) {
            System.out.println(
                    "One of the variables has not been assigned!");
        }
    }

    @FXML
    private void tcCalculateAndPrint(ActionEvent e) {
        e.consume();
        try {
            TierCalculator tc = new TierCalculator(Float.parseFloat(tcStartingHeightTf.getText()), tcIsJump.isSelected());
            tc.calculateAndOutputTier();
        } catch (Exception ignore) {
            System.out.println("Starting height has not been assigned!");
        }
    }

    @FXML
    private void bscCalculateAndPrint(ActionEvent e){
        e.consume();
        try{
            BackwardsSpeedBlockCalculator bsc = new BackwardsSpeedBlockCalculator(
                    Integer.parseInt(bscNumJumpsTf.getText()),
                    bscSwiftnessS.getValue(),
                    bscSlownessS.getValue(),
                    bscTierMmS.getValue(),
                    Double.parseDouble(bscInitSpeedBackwardsTf.getText()),
                    Float.parseFloat(bscJumpAngleTf.getText()),
                    bscMmTypeCb.getValue(),
                    bscJumpTypeCb.getValue(),
                    Boolean.parseBoolean(bscStrafeCb.getValue())
            );
            bsc.calculateBackwardsSpeed();
        } catch (Exception ignore){
            System.out.println("One of the variables has not been assigned!");
        }
    }

    @FXML
    private void jcCalculateAndPrint(ActionEvent e){
        e.consume();
        try{
            JumpCalculator jc = new JumpCalculator(
                    jcSwiftnessS.getValue(),
                    jcSlownessS.getValue(),
                    Double.parseDouble(jcInitSpeedTf.getText()),
                    Double.parseDouble(jcYLimitTf.getText()),
                    Double.parseDouble(jcBoundaryTf.getText()),
                    Double.parseDouble(jcUpdaryTf.getText()),
                    jcFloorTypeCb.getValue(),
                    jcMmTypeCb.getValue(),
                    jcDelayedCb.isSelected());
            jc.calculateJump();
        } catch (Exception ignore){
            System.out.println("One of the variables has not been assigned!");
        }
    }

    @FXML
    private void sCalculateAndPrint(ActionEvent e){
        e.consume();
        try{
                SlimeCalculator s = new SlimeCalculator(
                        Float.parseFloat(sStartingHeightTf.getText()),
                        Float.parseFloat(sSlimeHeightTf.getText()),
                        sIsJumpCb.isSelected()
                        );
                s.calculateSlime();
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
