package gameoflife.Model.ForagingAnt;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.ForagingAntCellState;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Point2D;

public class ForagingAntCell extends Cell {

  private final List<Ant> myAntList;
  private final List<Ant> newAntList;
  private int pheromone;

  private double prob;

  protected ForagingAntCell(Point2D point, Enum currentState, Enum nextState, double prob) {
    super(point, currentState, nextState);
    this.prob = prob;
    myAntList = new ArrayList<>();
    newAntList = new ArrayList<>();
    pheromone = 0;
  }

  protected void addAnt(Ant a) {
    myAntList.add(a);
  }

  protected void addnewAnt(Ant a) {
    newAntList.add(a);
  }

  protected void recognizeNextState() {
    for (Ant a : myAntList) {
      a.move();
    }

    myAntList.clear();

    myAntList.addAll(newAntList);

    if (currentState.equals(ForagingAntCellState.FOOD) || currentState.equals(
        ForagingAntCellState.NEST)) {
      nextState = currentState;
    } else {
      if (myAntList.size() == 0) {
        nextState = ForagingAntCellState.EMPTY;
      } else {
        nextState = ForagingAntCellState.ANT;
      }
    }
  }

  public int getPheromone() {
    return pheromone;
  }

  public void setPheromone(int p) {
    pheromone = p;
  }

  public String toString() {
    return String.valueOf(((ForagingAntCellState) currentState).getCode());
  }

}
