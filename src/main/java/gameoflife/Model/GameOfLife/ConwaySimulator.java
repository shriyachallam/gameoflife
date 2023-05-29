package gameoflife.Model.GameOfLife;

import gameoflife.Model.CellState.ConwayCellState;
import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * ConwaySimulator: update empty cells and occupied cells each time.
 */

public class ConwaySimulator extends Simulator {

  public ConwaySimulator(int numRow, int numCol, List<List<String>> cells, String cellShape) {
    super(numRow, numCol, new Properties(), cellShape);
    myGrid = new ConwayGrid(numRow, numCol, cells);
  }

  public List<Double> getStat(){
    List<Double> stat = new ArrayList<>();
    double occupied = 0;
    double empty = 0;

    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        if(myGrid.getCell(i, j).getCurrentState().equals(ConwayCellState.OCCUPIED)){
          occupied ++;
        }
        else{
          empty ++;
        }
      }
    }

    stat.add(occupied);
    stat.add(empty);
    return stat;
  }

}
