package gameoflife.Model.SchellingSegregation;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.SegregateCellState;
import gameoflife.Model.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Point2D;

/**
 * SegregateGrid: collection of segregate cells. Initialized with moore neighbour.
 */

public class SegregateGrid extends Grid {
  private final double satisfaction;

  protected SegregateGrid(int numRow, int numCol, List<List<String>> cells, double satisfaction) {
    super(numRow, numCol);
    this.satisfaction = satisfaction;
    initializeMatrix(cells);
    addMooreNeighbour();
  }
  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState=SegregateCellState.findState(Integer.parseInt(cells.get(i).get(j)));
        Cell c = new SegregateCell(point, initialState, initialState, satisfaction);
        cellMatrix[i][j] = c;
      }
    }
  }

  @Override
  protected void step() {
    recognizeNextState();
    move();
    updateNextState();
  }

  protected void move() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (!((SegregateCell) cellMatrix[i][j]).getSatisfied() && !cellMatrix[i][j].getCurrentState()
            .equals(SegregateCellState.EMPTY)) {
          int[] nearest = findRandom(i, j, 0);
          int row = nearest[0];
          int col = nearest[1];
          Enum current = cellMatrix[i][j].getCurrentState();
          if (row == -1 && col == -1) {
            cellMatrix[i][j].setNextState(current);
          } else {
            cellMatrix[row][col].setNextState(current);
            cellMatrix[i][j].setNextState(SegregateCellState.EMPTY);
          }

        }
      }
    }
  }

  /**
   * Alternative Algorithm for find the nearest empty cell.
   */

  protected int[] findNearest(int row, int col) {
    List<int[]> emptyCell = new ArrayList<>();

    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (cellMatrix[i][j].getCurrentState().equals(SegregateCellState.EMPTY) &&
            cellMatrix[i][j].getNextState().equals(SegregateCellState.EMPTY)) {
          int[] point = {i, j};
          emptyCell.add(point);
        }
      }
    }

    if (emptyCell.size() == 0) {
      return new int[]{-1, -1};
    }

    double minDistance = Integer.MAX_VALUE;
    int[] point = {row, col};
    int[] nearest = new int[2];

    for (int[] empty : emptyCell) {
        if (distance(point, empty) < minDistance) {
            nearest = empty;
        }
    }

    return nearest;
  }

  private double distance(int[] point1, int[] point2) {
    return Math.sqrt(Math.pow(point1[0] - point2[0], 2) + Math.pow(0, 2));
  }

  /**
   *  Algorithm for randomly looking for an empty spot. The agent recursively looks for a potentially satisfying, empty cell
   *  until the counter reaches emptyCell().size, signifying there might not be a suitable cell.
   */
  protected int[] findRandom(int row, int col, int counter) {
    List<int[]> emptyCell = new ArrayList<>();
    Random random = new Random();

    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (cellMatrix[i][j].getCurrentState().equals(SegregateCellState.EMPTY) &&
            cellMatrix[i][j].getNextState().equals(SegregateCellState.EMPTY)) {
          int[] point = {i, j};
          emptyCell.add(point);
        }
      }
    }

    if (emptyCell.size() == 0) {
      return new int[]{-1, -1};
    }

    int r = random.nextInt(emptyCell.size());

    if(willSatisfy(emptyCell.get(r)[0], emptyCell.get(r)[1])){
      return emptyCell.get(r);
    }

    if(counter > emptyCell.size()) return new int[]{-1, -1};

    return findRandom(row, col, counter + 1);

  }

  private boolean willSatisfy(int row, int col){
      double sameNeighbour = 0.0;
      double totalNeighbour = 0.0;
      for (Cell c : cellMatrix[row][col].getNeighbour()) {
        if (!c.getCurrentState().equals(SegregateCellState.EMPTY)) {
          totalNeighbour++;
        }
        if (c.getCurrentState().equals(cellMatrix[row][col].getCurrentState())) {
          sameNeighbour++;
        }

      }

      return !(sameNeighbour / totalNeighbour < satisfaction);
  }


}
