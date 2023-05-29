package gameoflife.View;

import gameoflife.Model.Cell;
import gameoflife.Model.Simulator;
import gameoflife.SimulationGenerator;
import gameoflife.View.Statistics.StatisticsFactory;
import gameoflife.View.Statistics.StatisticsView;
import gameoflife.XMLGenerator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

public class SimulationScene extends Scene {
    private static final double SECOND_DELAY = 1;
    public static final String SIM_CONFIGURATION= "gameoflife/simulation/%s";
    private static final String STYLE_PATH = "gameoflife/style.css";
    private Simulator mySimulation;
    private final Timeline myAnimation=new Timeline();
    private final static FileChooser FILE_CHOOSER = SimFileChooser.makeChooser();
    private final GridView myGrid;
    private final InfoBox myInfoBox;
    private final StatisticsView mysView;
    private final BiFunction<Simulator, Boolean, Void> generateSimScene;
    private final HBox viewPanel;

    /**
     * Creates a new SimulationScene
     * @param root the root of the scene
     * @param simulation the simulation to be displayed
     * @param generateSimScene the function to generate a new simulation scene
     */
    public SimulationScene(GridPane root, Simulator simulation, BiFunction<Simulator, Boolean, Void> generateSimScene) {
        super(root, 1300, 700);

        this.generateSimScene=generateSimScene;
        this.mySimulation=simulation;

        this.getStylesheets().add(STYLE_PATH);
        root.setId("root");

        Properties colorProps = generateColorProps();
        myGrid =new GridView(mySimulation.getRows(), mySimulation.getCols(), colorProps, this::onCellClicked, mySimulation.getCellShape());
        ControlButtonPanel controlButtonPanel=new ControlButtonPanel(this::playAnimation,this::pauseAnimation, this::updateAnimationRate,  this::stepAnimation, this::saveSimulation, this::uploadSimulation);
        controlButtonPanel.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        viewPanel=new HBox(myGrid);

        myInfoBox=new InfoBox(mySimulation.getTitle(),mySimulation.getAuthor(),mySimulation.getDescription(), mySimulation.getSimType());
        ColorBox colorBox=new ColorBox(colorProps, this::updateGridView);
        mysView = StatisticsFactory.createStatisticsView(mySimulation);
        if(mysView!=null) {
            viewPanel.getChildren().add(mysView.getChart());
        }
        RadioButtonBox radioButton=new RadioButtonBox(mysView!=null, this::onGridToggle,this::onGraphToggle);
        radioButton.setAlignment(javafx.geometry.Pos.TOP_CENTER);

        VBox rightSidePanel=new VBox(radioButton, viewPanel, controlButtonPanel);
        root.add(rightSidePanel, 1, 1);

        VBox leftSidePanel=new VBox(myInfoBox,colorBox);


        if(!mySimulation.getParams().isEmpty()){
            ParamBox myParamBox = new ParamBox(mySimulation.getParams(), this::updateParameters);
            leftSidePanel.getChildren().add(myParamBox);
        }
        root.add(leftSidePanel, 0, 1);

        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
        updateGridView(null);
    }

    private Void onCellClicked(Integer row, Integer col) {
        mySimulation.rotateCell(row,col);
        updateGridView(null);
        return null;
    }

    private Properties generateColorProps() {
        Properties colorProps = new Properties();
        ResourceBundle colorBundle=ResourceBundle.getBundle(String.format(SIM_CONFIGURATION,mySimulation.getSimType()));
        String[] states=colorBundle.getString("states").split(",");
        String[] colors=colorBundle.getString("colors").split(",");
        for(int i=0;i< states.length;i++){
            colorProps.setProperty(states[i],colors[i]);
        }
        return colorProps;
    }

    private Void updateGridView(Void unused) {
        for(int row = 0; row< mySimulation.getRows(); row++){
            for(int col = 0; col< mySimulation.getCols(); col++){
                Cell c = mySimulation.getCell(row, col);
                Enum state = c.getCurrentState();
                myGrid.updateCell(row,col,state);
            }
        }
        return null;
    }

    private void step(){
        mySimulation.simulate();
        if(mysView != null) {
            mysView.updateStat();
        }
        updateGridView(null);
    }

    private Void pauseAnimation(Void unused){
        myAnimation.pause();
        return null;
    }

    private Void playAnimation(Void unused){
        myAnimation.play();
        return null;
    }

    private Void updateAnimationRate(Double newValue){
        myAnimation.setRate(newValue);
        return null;
    }

    private Void stepAnimation(Void unused){
        myAnimation.pause();
        this.step();
        return null;
    }

    private Void saveSimulation(Void unused) {
        File myFile = FILE_CHOOSER.showSaveDialog(new Stage());
        Properties simInfo=myInfoBox.retrieveInfo();
        Properties simParams=mySimulation.getParams();
        simInfo.setProperty("cells", mySimulation.toString());
        simInfo.setProperty("width", String.valueOf(mySimulation.getCols()));
        simInfo.setProperty("height", String.valueOf(mySimulation.getRows()));
        simInfo.setProperty("simtype", String.valueOf(mySimulation.getSimType()));
        XMLGenerator.generateXMLFile(myFile, simInfo, simParams);
        return null;
    }

    private Void uploadSimulation(Void unused){
        File myFile = FILE_CHOOSER.showOpenDialog(new Stage());
        if (myFile != null) {
            Simulator simulation=SimulationGenerator.generateSimulationFromXML(myFile);
            generateSimScene.apply(simulation, true);
        }
        return null;
    }
    private Void updateParameters(Properties paramProps){
        Properties simInfo=myInfoBox.retrieveInfo();

        List<List<String>> cells = new ArrayList<>();
        String[] cell_rows = mySimulation.toString().split("\n");

        for (String cellRow : cell_rows) {
            List<String> cell_rows_arraylist = Arrays.asList(cellRow.split(" "));
            cells.add(cell_rows_arraylist);
        }
        mySimulation=SimulationGenerator.constructSimulation(mySimulation.getSimType(),
            Integer.toString(mySimulation.getRows()), Integer.toString(mySimulation.getCols()), cells, paramProps, simInfo.getProperty("title"),
            simInfo.getProperty("author"), simInfo.getProperty("description"), simInfo.getProperty("cellShape"));
        return null;
    }

    private Void onGridToggle(Boolean showGrid){
        if(showGrid){
            viewPanel.getChildren().add(myGrid);
        }
        else{
            viewPanel.getChildren().remove(myGrid);
        }
        return null;
    }

    private Void onGraphToggle(Boolean showGraph){
        if(showGraph){
            viewPanel.getChildren().add(mysView.getChart());
        }
        else{
            viewPanel.getChildren().remove(mysView.getChart());
        }
        return null;
    }

}