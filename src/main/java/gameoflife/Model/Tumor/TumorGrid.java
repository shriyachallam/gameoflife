package gameoflife.Model.Tumor;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.CellState.TumorCellState;
import gameoflife.Model.Grid;
import javafx.geometry.Point2D;

import java.util.List;


/**
 * TumorGrid: collections of tumor cells, initialized by von-Neumann neighbour.
 */


public class TumorGrid extends Grid{

  private int ageThreshold;
  private double SymmetricProb;
  private double transitoryProb;
  private Enum initialState = TumorCellState.EMPTY;
  protected TumorGrid(int numRow, int numCol, List<List<String>> cells, int ageThreshold,  double SymmetricProb, double transitoryProb){
    super(numRow, numCol);
    this.ageThreshold = ageThreshold;
    this.SymmetricProb = SymmetricProb;
    this.transitoryProb = transitoryProb;
    initializeMatrix(cells);
    addvonNeumannNeighbour();
  }

  @Override
  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState= CellState.TumorCellState.findState(Integer.parseInt(cells.get(i).get(j)));
        Cell c = new TumorCell(point, initialState, initialState, ageThreshold, SymmetricProb, transitoryProb);
        cellMatrix[i][j] = c;
      }
    }
  }
}
