package gameoflife.Model.Tumor;

import gameoflife.Model.CellState.TumorCellState;
import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * TumorSimulator: keep track of different kinds of cells.
 * Also, may support kill transitory cells/stem cells in the future to help users understand curing tumor more directly
 */

public class TumorSimulator extends Simulator {
  private final int DEFAULT_AGE_THRESHOLD = 5;
  private final double DEFAULT_SYMMETRIC_PROB = 0.5;
  private final double DEFAULT_TRANSITORY_PROB = 0.1;
  public TumorSimulator(int numRow, int numCol, List<List<String>> cells,Properties paramProps, String cellShape ){
    super(numRow, numCol, paramProps, cellShape);
    int ageThreshold;
    double SymmetricProb;
    double transitoryProb;

    if (paramProps.contains("ageThreshold")) {
      ageThreshold = Integer.parseInt(paramProps.getProperty("ageThreshold"));
    }
    else {
      ageThreshold = DEFAULT_AGE_THRESHOLD;
    }

    if (paramProps.contains("SymmetricPercent")) {
      SymmetricProb = Double.parseDouble(paramProps.getProperty("SymmetricPercent"));
    }
    else {
      SymmetricProb = DEFAULT_SYMMETRIC_PROB;
    }

    if (paramProps.contains("transitoryPercent")) {
      transitoryProb = Double.parseDouble(paramProps.getProperty("transitoryPercent"));
    }
    else {
      transitoryProb = DEFAULT_TRANSITORY_PROB;
    }
    myGrid = new TumorGrid(numRow, numCol, cells, ageThreshold, SymmetricProb, transitoryProb);
  }



  public void killTransitory(){
    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        if(myGrid.getCell(i, j).getCurrentState().equals(TumorCellState.TRANSITORY)){
          myGrid.getCell(i, j).setCurrentState(TumorCellState.DEAD);
          myGrid.getCell(i, j).setNextState(TumorCellState.DEAD);
        }
      }
    }
  }

  public void killSTEM(){
    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        if(myGrid.getCell(i, j).getCurrentState().equals(TumorCellState.STEM)){
          myGrid.getCell(i, j).setCurrentState(TumorCellState.DEAD);
          myGrid.getCell(i, j).setNextState(TumorCellState.DEAD);
        }
      }
    }
  }

  @Override
  public List<Double> getStat() {
    List<Double> stat = new ArrayList<>();
    double stem = 0;
    double transitory = 0;
    double all = 0;

    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        switch((TumorCellState) myGrid.getCell(i, j).getCurrentState()){
          case STEM, MOVINGSTEM:
            stem ++;
            all ++;
            break;
          case TRANSITORY:
            transitory++;
            all++;
            break;
        }
      }
    }

    stat.add(stem);
    stat.add(transitory);
    stat.add(all);

    return stat;
  }


}
