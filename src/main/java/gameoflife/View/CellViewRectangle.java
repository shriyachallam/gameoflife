package gameoflife.View;

import javafx.scene.shape.Rectangle;

public class CellViewRectangle extends Rectangle {
  public CellViewRectangle(double width, double height) {
    super(width, height);
    this.setId("gridcell");
  }
}
