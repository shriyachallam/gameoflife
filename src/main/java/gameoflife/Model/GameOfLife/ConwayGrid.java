package gameoflife.Model.GameOfLife;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.Grid;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * ConwayGrid: collections of conway cells. Initialized with moore neighbour, below introduces
 * a way that users can control the type of neighbour through a string in property/xml files.
 */

public class ConwayGrid extends Grid {

  protected ConwayGrid(int numRow, int numCol, List<List<String>> cells) {
    super(numRow, numCol);
    initializeMatrix(cells);
    addMooreNeighbour();
//    switch(neighbour){
//      case "Moore" -> addMooreNeighbour();
//      case "vonNeumann" -> addvonNeumannNeighbour();
//    }
  }

  @Override
  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState=CellState.ConwayCellState.findState(Integer.parseInt(cells.get(i).get(j)));
        Cell c = new ConwayCell(point, initialState, initialState);
        cellMatrix[i][j] = c;
      }
    }
  }


}
