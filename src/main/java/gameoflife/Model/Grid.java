package gameoflife.Model;

import java.util.List;

public abstract class Grid {

  /**
   * Grid Class, essentially a collection of cells, the basic unit of step (moving one state forward),
   * including the two-pass design for models, one pass for recognizing next state, one pass for update.
   */

  protected Cell[][] cellMatrix;
  protected int numRow;
  protected int numCol;

  protected Grid(int numRow, int numCol) {
    this.numRow = numRow;
    this.numCol = numCol;

    cellMatrix = new Cell[numRow][numCol];
  }

  protected abstract void initializeMatrix(List<List<String>> cells);

  protected void step() {
    recognizeNextState();
    updateNextState();
  }

  protected void recognizeNextState() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        cellMatrix[i][j].recognizeNextState();
      }
    }
  }

  /*
  Methods for adding neighbours, two possible kinds of neighbours available.
  vonNeumann neighbour refers to the four cells at E, W, N, S direction.
  moore neighbour refers to eight cells in the 3 * 3 matrix centered at current cell.
   */

  protected void addvonNeumannNeighbour() {
    int[] direction = {-1, 0, 1, 0, -1};

    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {

        for (int k = 0; k < 4; k++) {
          int x = i + direction[k];
          int y = j + direction[k + 1];

          if (xInBound(x) && yInBound(y)) {
            Cell c = cellMatrix[x][y];
            cellMatrix[i][j].addNeighbour(c);
          }
        }

      }
    }
  }

  protected void addMooreNeighbour() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {

        for (int k = i - 1; k <= i + 1; k++) {
          for (int p = j - 1; p <= j + 1; p++) {
            if (xInBound(k) && yInBound(p) && (k != i || p != j)) {
              Cell c = cellMatrix[k][p];
              cellMatrix[i][j].addNeighbour(c);
            }
          }
        }

      }
    }
  }

  protected void updateNextState() {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        cellMatrix[i][j].updateState();
      }
    }
  }

  /*
  Checking whether a number is in bound (for rows and cols)
   */

  protected boolean xInBound(int x) {
    return x >= 0 && x < numRow;
  }

  protected boolean yInBound(int y) {
    return y >= 0 && y < numCol;
  }

  /*
  For external access of cells from the simulator, instead of the cellMatrix as a whole
   */

  public Cell getCell(int row, int col) {
    return cellMatrix[row][col];
  }

}
