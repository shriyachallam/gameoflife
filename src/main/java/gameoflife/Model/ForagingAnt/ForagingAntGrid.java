package gameoflife.Model.ForagingAnt;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.CellState.ForagingAntCellState;
import gameoflife.Model.Grid;
import java.util.List;
import javafx.geometry.Point2D;

public class ForagingAntGrid extends Grid {

  private final int numAnt;
  private double prob;

  protected ForagingAntGrid(int numRow, int numCol, int numAnt, double prob, List<List<String>> cells) {
    super(numRow, numCol);
    this.numAnt = numAnt;
    initializeMatrix(cells);
    addvonNeumannNeighbour();
  }

  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState = CellState.ForagingAntCellState.findState(
            Integer.parseInt(cells.get(i).get(j)));
        Cell c = new ForagingAntCell(point, initialState, initialState, prob);
        cellMatrix[i][j] = c;

        if(initialState.equals(ForagingAntCellState.NEST)){
          for(int k=0; k<numAnt; k++){
            ((ForagingAntCell) c).addAnt(new Ant(prob, (ForagingAntCell) c));
          }
        }
      }
    }
  }
}
