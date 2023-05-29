package gameoflife.View;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class RadioButtonBox extends GridPane{
  public RadioButtonBox(boolean graphAvailable,Callback<Boolean, Void> onGridToggle, Callback<Boolean, Void> onGraphToggle) {
    this.setId("radio-button-box");
    Label gridLabel = new Label("Show Grid");
    this.add(gridLabel, 0, 0);
    RadioButton showGrid = new RadioButton();
    showGrid.fire();
    showGrid.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
      onGridToggle.call(isNowSelected);
    });
    this.add(showGrid, 1, 0);

    if (graphAvailable) {
      Label graphLabel = new Label("Show Graph");
      this.add(graphLabel, 2, 0);
      RadioButton showGraph = new RadioButton();
      showGraph.fire();
      showGraph.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
        onGraphToggle.call(isNowSelected);
      });
      this.add(showGraph, 3, 0);
    }
  }

}
