package gameoflife.Model.Tumor;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.TumorCellState;
import java.util.Random;
import javafx.geometry.Point2D;

/**
 * Tumor Model
 */

public class TumorCell extends Cell {

  private final Random random;
  private int ageThreshold;
  private double SymmetricProb;
  private double transitoryProb;
  private int age;

  private double root;

  /*
  A tumor cell is actually a mutated stem cell, which is able to do two operations: symmetric mitosis and
  metastasis. For symmetric mitosis, it will born a new transitory cell around itself and the transitory cell
  also has the ability to replicate itself. For metastasis, a stem cell will simply replicate itself and send to else where
   */

  protected TumorCell(Point2D point, Enum currentState, Enum nextState, int ageThreshold,
      double SymmetricProb, double transitoryProb) {
    super(point, currentState, nextState);
    this.ageThreshold = ageThreshold;
    this.SymmetricProb = SymmetricProb;
    this.transitoryProb = transitoryProb;
    age = 0;
    random = new Random();
  }

  /*
  The rules are:
    1. STEM cells never change
    2. Transitory cells die when the age is beyond threshold
    3. Transitory cells have chances to do transitory mitosis.
    4. STEM cell may either produce transitory cell (high probability), replicate itself (extremely low probability), or do nothing
    5. Dead cells/tissues will be removed by the "body"
   */

  protected void recognizeNextState() {
    if (!nextState.equals(currentState)) {
      return;
    }

    root = random.nextGaussian();
    if (currentState.equals(TumorCellState.STEM)) {
      nextState = TumorCellState.STEM;

      double symmetric = random.nextDouble(1);

      if (symmetric <= SymmetricProb) {
        symmetricMitosis();
      } else {
        metastasis(root);
      }
    } else if (currentState.equals(TumorCellState.MOVINGSTEM)) {
      metastasis(root);
      if(! nextState.equals(TumorCellState.STEM)){
        nextState = TumorCellState.EMPTY;
      }
    } else if (currentState.equals(TumorCellState.TRANSITORY)) {
      if (age >= ageThreshold) {
        nextState = TumorCellState.DEAD;
      } else {
        nextState = TumorCellState.TRANSITORY;

        double divide = random.nextDouble(1);
        if (divide < transitoryProb) {
          transitoryMitosis();
        }
      }
    } else {
      nextState = TumorCellState.EMPTY;
    }

    age++;

  }

  /*
  metastasis: carry a stem cell further into the body.
   */

  protected void metastasis(double root) {
    double stay = random.nextGaussian();
    if (stay <= root && currentState != TumorCellState.STEM) {
      nextState = TumorCellState.STEM;
    } else {
      moveFurther();
    }
  }

  private void moveFurther() {
    int r = random.nextInt(neighbour.size());
    Cell c = neighbour.get(r);
    if(! c.getCurrentState().equals(TumorCellState.STEM)){
      c.setNextState(TumorCellState.MOVINGSTEM);
    }
  }

  /*
  symmetric mitosis: if a random surrounding cell is not stem cell, make it transitory.
   */

  protected void symmetricMitosis() {
    int direction = random.nextInt(neighbour.size());
    if(neighbour.get(direction).getCurrentState() != TumorCellState.STEM){
      neighbour.get(direction).setNextState(TumorCellState.TRANSITORY);
    }
  }

  /*
  transitory mitosis: if a random surrounding cell is not stem cell, make it transitory.
  essentially the same as symmetricMitosis, but biologically different, so I made seperate methods
  */

  protected void transitoryMitosis() {
    int direction = random.nextInt(neighbour.size());
    if(neighbour.get(direction).getCurrentState() != TumorCellState.STEM){
      neighbour.get(direction).setNextState(TumorCellState.TRANSITORY);
    }
  }

  @Override
  public String toString() {
    return String.valueOf(((TumorCellState) currentState).getCode());
  }

}
