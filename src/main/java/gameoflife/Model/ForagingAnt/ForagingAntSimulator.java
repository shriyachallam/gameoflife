package gameoflife.Model.ForagingAnt;

import gameoflife.Model.Simulator;
import java.util.List;
import java.util.Properties;

public class ForagingAntSimulator extends Simulator {
  private final int DEFAULT_NUM_ANT = 5;
  private final double DEFAULT_PROB = 0.5;
  private final int numAnt;
  private double prob;

  public ForagingAntSimulator(int numRow, int numCol, List<List<String>> cells,
      Properties paramProps, String cellShape) {
    super(numRow, numCol, paramProps, cellShape);

    if (paramProps.contains("numAnt")) {
      this.numAnt = Integer.parseInt(paramProps.getProperty("numAnt"));
    }
    else {
      this.numAnt = DEFAULT_NUM_ANT;
    }

    if (paramProps.contains("probPercent")) {
      this.prob = Double.parseDouble(paramProps.getProperty("probPercent"));
    }
    else {
      this.prob = DEFAULT_PROB;
    }

    myGrid = new ForagingAntGrid(numRow, numCol, numAnt, prob, cells);
  }

  @Override
  public List<Double> getStat() {
    return null;
  }
}
