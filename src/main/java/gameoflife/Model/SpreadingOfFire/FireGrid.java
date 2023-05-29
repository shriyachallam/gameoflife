package gameoflife.Model.SpreadingOfFire;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.Grid;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * FireGrid class: a collection of fire cells using 2D array, initialized using vonNeumann neighbour
 */

public class FireGrid extends Grid {
  private final double probCatch;

  protected FireGrid(int numRow, int numCol,List<List<String>> cells, double probCatch) {
    super(numRow, numCol);
    this.probCatch = probCatch;
    initializeMatrix(cells);
    addvonNeumannNeighbour();
  }

  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState=CellState.FireCellState.findState(Integer.parseInt(cells.get(i).get(j)));
        Cell c = new FireCell(point, initialState, initialState, probCatch);
        cellMatrix[i][j] = c;
      }
    }
  }


}
