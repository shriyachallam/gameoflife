package gameoflife.Model.Percolation;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.PercolateCellState;
import javafx.geometry.Point2D;

/**
 * Percolation Model
 */

public class PercolateCell extends Cell {

  protected PercolateCell(Point2D point, Enum currentState, Enum nextState) {
    super(point, currentState, nextState);
  }

  protected void recognizeNextState() {
    if (currentState.equals(PercolateCellState.BLOCK) || currentState.equals(
        PercolateCellState.PERCOLATE)) {
      nextState = currentState;
    } else {
      boolean percolate = false;
      for (Cell c : neighbour) {
        if (c.getCurrentState().equals(PercolateCellState.PERCOLATE)) {
          nextState = PercolateCellState.PERCOLATE;
          percolate = true;
        }
      }

      if(! percolate){
        nextState = PercolateCellState.OPEN;
      }
    }

  }

  public String toString() {
    return String.valueOf(((PercolateCellState) currentState).getCode());
  }
}
