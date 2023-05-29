package gameoflife.Model.SpreadingOfFire;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.FireCellState;
import gameoflife.Model.CellState.State;
import java.util.Random;
import javafx.geometry.Point2D;

/**
 * Spreading of Fires Model
 */

public class FireCell extends Cell {

  private final double probCatch;
  private final Random random;

  protected FireCell(Point2D position, Enum currentState, Enum nextState, double probCatch) {
    super(position, currentState, nextState);
    this.probCatch = probCatch; // specify the probability of catching fire from neighbour
    random = new Random();
  }

  /*
  The rules are that:
    if one of the neighbour is burning, the current cell has the probCatch probability of catching fire,
    otherwise, it remains the current state.
   */

  protected void recognizeNextState() {
    boolean burning = false;

    if (currentState.equals(FireCellState.TREE)) {
      for (Cell c : neighbour) {
        if (c.getCurrentState().equals(FireCellState.BURNING)) {
          double r = random.nextDouble();
          burning = true;
          if (r <= probCatch) {
            nextState = FireCellState.BURNING;
            break;
          }
        }
      }
      if (!burning) {
        nextState = FireCellState.TREE;
      }
    } else {
      nextState = FireCellState.EMPTY;
    }

  }

  public String toString() {
    return String.valueOf(((State)currentState).getCode());
  }


}
