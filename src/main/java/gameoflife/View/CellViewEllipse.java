package gameoflife.View;

import javafx.scene.shape.Ellipse;

public class CellViewEllipse extends Ellipse {
  public CellViewEllipse(double radiusX, double radiusY) {
    super(radiusX, radiusY);
    this.setId("gridcell");
  }
}
