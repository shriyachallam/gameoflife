package gameoflife.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.geometry.Point2D;

public abstract class Cell {

  /**
   * The Cell class, which is the backbone of the model. It is the smallest unit for an item,
   * with state, neighbour, and position
   */

  protected Point2D position;
  protected Enum currentState;
  protected Enum nextState;
  protected List<Cell> neighbour;



  protected Cell(Point2D position, Enum currentState, Enum nextState) {
    this.position = position;
    this.currentState = currentState;
    this.nextState = nextState;
    neighbour = new ArrayList<>();
  }

  // Recognizing next state based on current state and neighbour, different between classes

  protected abstract void recognizeNextState();

  protected void updateState() {
    currentState = nextState;
  }

  public void addNeighbour(Cell c) {
    neighbour.add(c);
  }


  /*
  Accessor and Mutator for each parameter, note that neighbour cannot be externally modified
   */

  protected void setPosition(double x, double y) {
    position = new Point2D(x, y);
  }

  public double getxPosition() {
    return position.getX();
  }

  public double getyPosition() {
    return position.getY();
  }

  public Enum getCurrentState() {
    return currentState;
  }

  public void setCurrentState(Enum state) {
    currentState = state;
  }

  public Enum getNextState() {
    return nextState;
  }

  public void setNextState(Enum state) {
    nextState = state;
  }

  public List<Cell> getNeighbour(){
    return Collections.unmodifiableList(neighbour);
  }

  public abstract String toString();

}
