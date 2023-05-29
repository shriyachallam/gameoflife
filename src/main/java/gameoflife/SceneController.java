package gameoflife;

import gameoflife.Model.Simulator;
import gameoflife.View.SimulationScene;
import gameoflife.View.SplashScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 * This class is responsible for controlling the scenes of the application
 */
public class SceneController {
    private Stage stage;
    /**
     * Constructor for the SceneController class
     * @param stage the stage of the application
     */
    public SceneController(Stage stage) {
        SplashScene splashScene = new SplashScene(new BorderPane(), this::generateSimScene);
        this.stage = stage;
        this.stage.setScene(splashScene);
        this.stage.show();
    }

    /**
     * Generates a new simulation scene
     * @param simulation the simulation to be displayed
     * @param replaceCurrent whether or not to replace the current scene
     * @return null
     */
    public Void generateSimScene(Simulator simulation, boolean replaceCurrent){
        if(!replaceCurrent){
            stage=new Stage();
        }
        SimulationScene simulationScene=new SimulationScene(new GridPane(), simulation, this::generateSimScene);
        stage.setScene(simulationScene);
        stage.show();
        return null;
    }
}

