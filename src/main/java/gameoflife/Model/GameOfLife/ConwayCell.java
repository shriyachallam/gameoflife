package gameoflife.Model.GameOfLife;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.ConwayCellState;
import javafx.geometry.Point2D;

/**
 * Game of Life Model
 */

public class ConwayCell extends Cell {

  protected ConwayCell(Point2D point, Enum currentState, Enum nextState) {
    super(point, currentState, nextState);
  }

  /*
  The rules are:
    if and only if two of the surrounding cells are live, the state is stable
    if and only if two of the surrounding cells are three, a new cell is born.
    otherwise, the cell either died from over population or less population
   */
  protected void recognizeNextState() {
    int liveNeighbour = 0;

    for (Cell c : neighbour) {
      if (c.getCurrentState().equals(ConwayCellState.OCCUPIED)) {
        liveNeighbour++;
      }
    }
    switch (liveNeighbour) {
      case 0, 1, 4, 5, 6, 7, 8:
        nextState = ConwayCellState.EMPTY;
        break;
      case 2:
        nextState = currentState;
        break;
      case 3:
        nextState = ConwayCellState.OCCUPIED;
        break;
    }
  }

  public String toString() {
    return String.valueOf(((ConwayCellState) currentState).getCode());
  }

}
