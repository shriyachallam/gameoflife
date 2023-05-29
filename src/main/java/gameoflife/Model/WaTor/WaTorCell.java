package gameoflife.Model.WaTor;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.CellState.WaTorCellState;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Point2D;

public class WaTorCell extends Cell {
  private final Random random;
  private Fish creature;
  private Cell target;
  private boolean set;

  protected WaTorCell(Point2D position, Enum currentState, Enum nextState, Fish creature) {
    super(position, currentState, nextState);
    random = new Random();
    this.creature = creature;
  }

  @Override
  public void recognizeNextState() {
    set = false;
    if (currentState == CellState.WaTorCellState.SHARK) {
      target = findSharkTarget();
    } else if (currentState == CellState.WaTorCellState.FISH) {
      target = findFishTarget();
    } else {
      target = null;
    }
  }

  private Cell findSharkTarget() {
    ArrayList<Cell> fishCells = new ArrayList<>();
    ArrayList<Cell> emptyCells = new ArrayList<>();
    for (Cell c : neighbour) {
      if (c.getCurrentState() == CellState.WaTorCellState.FISH) {
        fishCells.add(c);
      }
      if (c.getCurrentState() == CellState.WaTorCellState.EMPTY) {
        emptyCells.add(c);
      }
    }
    if (!fishCells.isEmpty()) {
      return fishCells.get(random.nextInt(fishCells.size()));

    } else if (!emptyCells.isEmpty()) {
      return emptyCells.get(random.nextInt(emptyCells.size()));
    } else {
      return null;

    }
  }

  private Cell findFishTarget() {
    ArrayList<Cell> emptyCells = new ArrayList<>();
    for (Cell c : neighbour) {
      if (c.getCurrentState() == CellState.WaTorCellState.EMPTY) {
        emptyCells.add(c);
      }
    }
    if (!emptyCells.isEmpty()) {
      return emptyCells.get(random.nextInt(emptyCells.size()));
    } else {
      return null;
    }
  }

  @Override
  protected void updateState() {
    if (currentState != WaTorCellState.EMPTY) {
      creature.updateVitals();
    }
    handleMovement();
    if (currentState == WaTorCellState.SHARK) {
      if (!((Shark) creature).isAlive()) {
        this.setCreature(null);
      }
    }
  }

  private void handleMovement() {
    if (target != null & !set) {
      if (currentState == WaTorCellState.FISH
          & target.getCurrentState() == WaTorCellState.EMPTY) {
        handleFishMove();
      }
      if (currentState == CellState.WaTorCellState.SHARK
          & target.getCurrentState() == CellState.WaTorCellState.FISH) {
        handleSharkEatFish();
      }
      if (currentState == CellState.WaTorCellState.SHARK
          & target.getCurrentState() == CellState.WaTorCellState.EMPTY) {
        handleSharkMove();
      }
    }
  }

  private void handleFishMove() {
    ((WaTorCell) target).setCreature(creature);
    handleReproduction();
  }

  private void handleSharkEatFish() {
    ((Shark) creature).incrementEnergy();
    ((WaTorCell) target).setCreature(creature);
    handleReproduction();
  }

  private void handleSharkMove() {
    if (((Shark) creature).isAlive()) {
      ((WaTorCell) target).setCreature(creature);
    }
    handleReproduction();
  }

  private void handleReproduction() {
    if (creature.canReproduce()) {
      this.setCreature(creature.makeChild());
    } else {
      this.setCreature(null);
    }
  }

  //TODO: make it so this isn't a switch statement
  protected void setCreature(Fish creature) {
    this.creature = creature;
    if (this.creature == null) {
      this.currentState = CellState.WaTorCellState.EMPTY;
    } else if (this.creature instanceof Shark) {
      this.currentState = CellState.WaTorCellState.SHARK;
      this.set = true;
    } else {
      this.currentState = CellState.WaTorCellState.FISH;
      this.set = true;
    }
  }

  public String toString() {
    return String.valueOf(((CellState.WaTorCellState) currentState).getCode());
  }

}
