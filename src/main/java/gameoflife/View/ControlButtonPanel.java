package gameoflife.View;

import gameoflife.Main;
import java.util.Objects;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * This class is responsible for creating the control button panel
 * @author Sophie
 */
public class ControlButtonPanel extends GridPane {

  /**
   * This method generates a button with the given image
   * @param playCallback - the callback for the play button
   * @param pauseCallback - the callback for the pause button
   * @param updateRateCallback - the callback for the rate slider
   * @param stepCallback - the callback for the step button
   * @param saveCallback - the callback for the save button
   * @param uploadCallback - the callback for the upload button
   */
  public ControlButtonPanel(Callback<Void, Void> playCallback, Callback<Void, Void> pauseCallback,
      Callback<Double, Void> updateRateCallback, Callback<Void, Void> stepCallback,
      Callback<Void, Void> saveCallback, Callback<Void, Void> uploadCallback) {
    super();
    generatePlayButton(playCallback);
    generatePauseButton(pauseCallback);
    generateStepButton(stepCallback);
    generateRateSlider(updateRateCallback);
    generateSaveButton(saveCallback);
    generateUploadButton(uploadCallback);
  }

  private void generateRateSlider(Callback<Double, Void> updateRateCallback) {
    Slider slider = new Slider(1, 8, 1);
    slider.setId("slider");
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> updateRateCallback.call(newValue.doubleValue()));
    this.add(slider, 3, 0);
  }

  private void generatePlayButton(Callback<Void, Void> playCallback) {
    Button playButton = generateButton("play");
    playButton.setOnAction(event -> playCallback.call(null));
    this.add(playButton, 0, 0);
  }

  private static Button generateButton(String buttonType) {
    Button button=new Button();
    Image image=new Image(Objects.requireNonNull(Main.class.getResourceAsStream(String.format(
        "/gameoflife/icons/%s.png", buttonType))));
    ImageView imageView = new ImageView(image);
    imageView.setFitWidth(20);
    imageView.setFitHeight(20);
    button.setGraphic(imageView);
    return button;
  }

  private void generatePauseButton(Callback<Void, Void> pauseCallback) {
    Button pauseButton = generateButton("pause");
    pauseButton.setOnAction(event -> pauseCallback.call(null));
    this.add(pauseButton, 1, 0);
  }

  private void generateStepButton(Callback<Void, Void> stepCallback) {
    Button stepButton = generateButton("step");
    stepButton.setOnAction(event -> stepCallback.call(null));
    this.add(stepButton, 2, 0);
  }

  private void generateSaveButton(Callback<Void, Void> saveCallback) {
    Button playButton = generateButton("diskette");
    playButton.setOnAction(event -> saveCallback.call(null));
    this.add(playButton, 4, 0);
  }

  private void generateUploadButton(Callback<Void, Void> uploadCallback) {
    Button playButton = generateButton("upload");
    playButton.setOnAction(event -> uploadCallback.call(null));
    this.add(playButton, 5, 0);
  }

}
