package gameoflife.Model.SchellingSegregation;

import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * SegregateSimulator: only update satisfaction percentage each time.
 */

public class SegregateSimulator extends Simulator {
  private final double DEFAULT_SATISFACTION = 0.5;
  private final double satisfaction;
  private double satisfiedPercentage;

  public SegregateSimulator(int numRow, int numCol,List<List<String>> cells, Properties paramProps, String cellShape) {
    super(numRow, numCol, paramProps, cellShape);
    if (paramProps.contains("satisfactionPercent")) {
      this.satisfaction = Double.parseDouble(paramProps.getProperty("satisfactionPercent"));
    }
    else {
      this.satisfaction = DEFAULT_SATISFACTION;
    }

    myGrid = new SegregateGrid(numRow, numCol, cells, satisfaction);
  }

  public void updatePercentage() {
    double satisfiedCount = 0;
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (((SegregateCell) myGrid.getCell(i, j)).getSatisfied()) {
          satisfiedCount++;
        }
      }
    }
    satisfiedPercentage = satisfiedCount / (numRow * numCol);
  }


  protected double getPercentage() {
    return satisfiedPercentage;
  }

  protected boolean finished() {
    return satisfiedPercentage == 1;
  }

  @Override
  public List<Double> getStat() {
    updatePercentage();
    return new ArrayList<>(){{add(satisfiedPercentage * 100);}};
  }
}
