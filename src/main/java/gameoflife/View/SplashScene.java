package gameoflife.View;

import gameoflife.Model.Simulator;
import gameoflife.SimulationGenerator;
import java.io.File;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SplashScene extends Scene {

  public static final int WIDTH = 500;
  public static final int HEIGHT = 450;
  private final static FileChooser FILE_CHOOSER = SimFileChooser.makeChooser();
  private final BiFunction<Simulator, Boolean, Void> generateSimScene;
  private static final String STYLE_PATH = "gameoflife/style.css";
  public static final String INTERNAL_CONFIGURATION = "gameoflife.text";
  private final ResourceBundle myResources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);

  /**
   * Creates a new SplashScene
   * @param root the root of the scene
   * @param generateSimScene the function to generate a new simulation scene
   */
  public SplashScene(BorderPane root, BiFunction<Simulator, Boolean, Void> generateSimScene) {
    super(root, WIDTH, HEIGHT);

    this.getStylesheets().add(STYLE_PATH);
    root.setId("splash-root");

    this.generateSimScene = generateSimScene;


    Label title = new Label(myResources.getString("MainTitle"));
    Label instructions = new Label(myResources.getString("InstructionsTitle"));
    Button uploadButton = new Button(myResources.getString("UploadButton"));
    uploadButton.setOnMouseClicked(e -> uploadFile());
    VBox splashBox = new VBox(title, instructions, uploadButton);
    splashBox.setId("splash-box");
    root.setCenter(splashBox);
  }
  private void uploadFile() {
    File myFile = FILE_CHOOSER.showOpenDialog(new Stage());
    if (myFile != null) {
      Simulator simulation= SimulationGenerator.generateSimulationFromXML(myFile);
      generateSimScene.apply(simulation, false);
    }
  }
}