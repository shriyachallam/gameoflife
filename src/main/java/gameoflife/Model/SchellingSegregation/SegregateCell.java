package gameoflife.Model.SchellingSegregation;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.SegregateCellState;
import javafx.geometry.Point2D;

/**
 * Segregation Model
 */

public class SegregateCell extends Cell {

  private final double satisfaction;
  private boolean satisfied;

  protected SegregateCell(Point2D point, Enum currentState, Enum nextState, double satisfaction) {
    super(point, currentState, nextState);
    this.satisfaction = satisfaction; //specify the segregation threshold one can endure
  }

  /*
  The rules are:
    if the percentage of same neighbours out of non-empty neighbours < satisfaction rate, the agent move
    otherwise, it stays at the original position.
   */

  @Override
  protected void recognizeNextState() {
    if (!currentState.equals(SegregateCellState.EMPTY)) {
      double sameNeighbour = 0.0;
      double totalNeighbour = 0.0;
      for (Cell c : neighbour) {
        if (!c.getCurrentState().equals(SegregateCellState.EMPTY)) {
          totalNeighbour++;
        }
        if (c.getCurrentState().equals(currentState)) {
          sameNeighbour++;
        }

      }

      if(totalNeighbour == 0){
        satisfied = false;
      }
      else{
        satisfied = !(sameNeighbour / totalNeighbour < satisfaction);
      }

      if (satisfied) {
        nextState = currentState;
      } else {
        nextState = SegregateCellState.EMPTY;
      }
    } else {
      satisfied = true;
    }

  }

  public boolean getSatisfied() {
    return satisfied;
  }

  public String toString() {
    return String.valueOf(((SegregateCellState) currentState).getCode());
  }


}
