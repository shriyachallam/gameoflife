package gameoflife.View;

import javafx.scene.shape.Circle;

public class CellViewCircle extends Circle {
  public CellViewCircle(double radius) {
    super(radius);
    this.setId("gridcell");
  }
}
