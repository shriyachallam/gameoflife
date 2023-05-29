package gameoflife.Model.ForagingAnt;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.ForagingAntCellState;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ant {

  private boolean findFood;
  private ForagingAntCell currentCell;
  private ForagingAntCell nextCell;

  private final Random random;
  private double prob;

  protected Ant(double prob, ForagingAntCell currentCell) {
    this.prob = prob;
    this.currentCell = currentCell;
    random = new Random();
  }

  protected void forage() {
    List<ForagingAntCell> forageList = new ArrayList<>();

    for (Cell c : currentCell.getNeighbour()) {
      if (c.getxPosition() <= currentCell.getxPosition()
          && c.getyPosition() >= currentCell.getyPosition()) {
        forageList.add((ForagingAntCell) c);
      }
    }
    int totalPheromone = 0;

    for (ForagingAntCell c : forageList) {
      totalPheromone += c.getPheromone();
    }

    if(forageList.size() == 1){
      findFood = true;
      nextCell = currentCell;
      return;
    }

    double p = forageList.get(0).getPheromone();

    double direction = random.nextDouble();


    if(totalPheromone == 0 || p == 0.0 || p/totalPheromone <= 0.75 && p/totalPheromone >= 0.35){

      if(direction < prob){
        nextCell = forageList.get(0);
      }
      else{
        nextCell = forageList.get(1);
      }
    }
    else{

      if (direction < prob * (p / totalPheromone)) {
        nextCell = forageList.get(0);
      } else {
        nextCell = forageList.get(1);
      }
    }


    nextCell.addnewAnt(this);

    if (nextCell.getCurrentState().equals(ForagingAntCellState.FOOD)) {
      findFood = true;
    }
  }

  protected void move() {
    layPheromone();
    if (findFood) {
      returnNest();
    } else {
      forage();
    }
    currentCell = nextCell;
  }

  protected void layPheromone() {
    currentCell.setPheromone(currentCell.getPheromone() + 1);
  }

  protected void returnNest() {
    List<ForagingAntCell> returnList = new ArrayList<>();

    for (Cell c : currentCell.getNeighbour()) {
      if (c.getxPosition() >= currentCell.getxPosition()
          && c.getyPosition() <= currentCell.getyPosition()) {
        returnList.add((ForagingAntCell) c);
      }
    }

    if(returnList.size() == 0){
      nextCell = currentCell;
      return;
    }

    int totalPheromone = 0;

    for (ForagingAntCell c : returnList) {
      totalPheromone += c.getPheromone();
    }

    double direction = random.nextDouble();
    double p = returnList.get(0).getPheromone();

    if (direction < p / totalPheromone) {
      nextCell = returnList.get(0);
    } else {
      nextCell = returnList.get(1);
    }

    nextCell.addnewAnt(this);
  }
}
