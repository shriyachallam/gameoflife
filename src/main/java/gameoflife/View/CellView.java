package gameoflife.View;

import javafx.scene.shape.*;

/**
 * This class creates a cell view that is used to display the cells in the simulation
 * @author Sophie
 */
public class CellView {
  /**
   * Constructor for the CellView class
   * @param width the width of the cell
   * @param height the height of the cell
   */
  private Shape cellView;
  public CellView(double width, double height, String cellShape) {
    if (cellShape.equals("Rectangle")) {
      cellView = new CellViewRectangle(width, height);
    }
    if (cellShape.equals("Circle")) {
      double radius = width/2;
      cellView = new CellViewCircle(radius);
    }
    if (cellShape.equals("Ellipse")) {
      double radiusX = (width/2);
      double radiusY = (height/2);
      cellView = new CellViewEllipse(radiusX, radiusY);
    }
  }

  public Shape getCellView() {
    return cellView;
  }
}
