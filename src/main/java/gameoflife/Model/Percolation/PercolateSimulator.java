package gameoflife.Model.Percolation;

import gameoflife.Model.CellState.PercolateCellState;
import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PercolateSimulator extends Simulator {

  private boolean percolate;

  public PercolateSimulator(int numRow, int numCol, List<List<String>> cells, String cellShape) {
    super(numRow, numCol, new Properties(), cellShape);
    myGrid = new PercolateGrid(numRow, numCol, cells);
  }

  public void dfs(int row, int col, boolean[][] visited, int[][] open) {
      if (!myGrid.getCell(row, col).getCurrentState().equals(PercolateCellState.OPEN)) {
          return;
      }
      if (visited[row][col]) {
          return;
      }

    visited[row][col] = true;
    open[row][col] = 1;

    int[] direction = {-1, 0, 1, 0, -1};

    for (int i = 0; i < 4; i++) {
      int x = row + direction[i];
      int y = col + direction[i + 1];

      if (x >= 0 && x < numRow && y >= 0 && y < numCol &&
          myGrid.getCell(x, y).getCurrentState().equals(PercolateCellState.OPEN)) {
        dfs(x, y, visited, open);
      }
    }
  }

  @Override
  public boolean getFinished() {
    for (int i = 0; i < numRow; i++) {
      if (myGrid.getCell(i, 0).getCurrentState().equals(PercolateCellState.OPEN)) {
        int[][] open = new int[numRow][numCol];
        dfs(i, 0, new boolean[numRow][numCol], open);

        for (int j = 0; j < numRow; j++) {
          if (open[j][numCol - 1] == 1) {
            percolate = true;
            break;
          }
        }
      }
    }

    for (int i = 0; i < numCol; i++) {
      if (myGrid.getCell(0, i).getCurrentState().equals(PercolateCellState.OPEN)) {
        int[][] open = new int[numRow][numCol];
        dfs(0, i, new boolean[numRow][numCol], open);

        for (int j = 0; j < numCol; j++) {
          if (open[numRow - 1][j] == 1) {
            percolate = true;
            break;
          }
        }
      }
    }

    return percolate;
  }

  @Override
  public List<Double> getStat() {

    List<Double> stat = new ArrayList<>();
    double percolate = 0;
    double block = 0;
    double open = 0;

    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        switch((PercolateCellState) myGrid.getCell(i, j).getCurrentState()){
          case PERCOLATE -> percolate ++;
          case BLOCK -> block ++;
          case OPEN -> open ++;
        }
      }
    }

    stat.add(block);
    stat.add(open);
    stat.add(percolate);

    return stat;
  }


}
