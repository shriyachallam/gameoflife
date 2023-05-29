package gameoflife.Model.Percolation;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.Grid;
import javafx.geometry.Point2D;

import java.util.List;

public class PercolateGrid extends Grid {

  protected PercolateGrid(int numRow, int numCol, List<List<String>> cells) {
    super(numRow, numCol);
    initializeMatrix(cells);
    addMooreNeighbour();
  }

  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState=CellState.PercolateCellState.findState(Integer.parseInt(cells.get(i).get(j)));
        Cell c = new PercolateCell(point, initialState, initialState);
        cellMatrix[i][j] = c;
      }
    }
  }


}
