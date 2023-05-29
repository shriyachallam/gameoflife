package gameoflife.Model.SpreadingOfFire;

import gameoflife.Model.CellState.FireCellState;
import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Fire Simulator, update empty, tree, and burning cells each time.
 */

public class FireSimulator extends Simulator {
  private final double DEFAULT_PROB_CATCH = 0.5;
  public FireSimulator(int numRow, int numCol, List<List<String>> cells, Properties paramProps, String cellShape) {
    super(numRow, numCol, paramProps, cellShape);
    double probCatch;

    if (paramProps.contains("catchPercent")) {
      probCatch = Double.parseDouble(paramProps.getProperty("catchPercent"));
    }
    else {
      probCatch = DEFAULT_PROB_CATCH;
    }

    myGrid = new FireGrid(numRow, numCol, cells,probCatch);
  }

  @Override
  public List<Double> getStat() {
    List<Double> stat = new ArrayList<>();
    double empty = 0;
    double tree = 0;
    double burning = 0;

    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        switch((FireCellState) myGrid.getCell(i, j).getCurrentState()){
          case BURNING -> burning ++;
          case TREE -> tree ++;
          case EMPTY -> empty ++;
        }
      }
    }

    stat.add(empty);
    stat.add(tree);
    stat.add(burning);

    return stat;
  }
}
