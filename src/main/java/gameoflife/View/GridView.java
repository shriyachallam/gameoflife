package gameoflife.View;

import java.util.Properties;
import java.util.function.BiFunction;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * This class represents the grid view of the simulation
 */
public class GridView extends GridPane {

  private static int SIZE=400;
  private final Properties myColorProps;
  /**
   * Constructor for GridView
   * @param rows number of rows in the grid
   * @param columns number of columns in the grid
   * @param colorProps properties file containing the color of each state
   * @param onCellClicked callback function to be called when a cell is clicked
   * @param cellShape shape of each cell
   */
  public GridView(int rows, int columns, Properties colorProps, BiFunction<Integer, Integer, Void> onCellClicked, String cellShape) {
    this.setId("grid");
    double cellHeight=SIZE/rows;
    double cellWidth=SIZE/columns;

    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        CellView cell = new CellView(cellWidth, cellHeight, cellShape);
        int finalRow = row;
        int finalCol = col;
        cell.getCellView().setOnMouseClicked(event -> this.setOnMouseClicked(e->onCellClicked.apply(finalRow, finalCol)));
        this.add(cell.getCellView(), col, row);
      }
    }
    myColorProps=colorProps;
  }
  /**
   * Updates the color of a cell
   * @param row row of the cell
   * @param col column of the cell
   * @param state state of the cell
   */
  public void updateCell(int row, int col, Enum state) {
    Shape cell = (Shape) this.getChildren().get(col + row * this.getColumnCount());
    Color color = Color.web(myColorProps.getProperty(String.valueOf(state)));
    cell.setFill(color);
  }
}
