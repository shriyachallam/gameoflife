package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * Percolate Class: Statistics include the number of percolate, blocked, and open cells.
 */

public class PercolationStat extends StatisticsView{
  private XYChart.Series percolate;
  private XYChart.Series block;
  private XYChart.Series open;
  int stepCtr = 0;

  public PercolationStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);

    percolate = new XYChart.Series<Number, Number>();
    percolate.setName(myResources.getString("PERCOLATE"));

    open = new XYChart.Series<Number, Number>();
    open.setName(myResources.getString("OPEN"));

    block = new XYChart.Series<Number, Number>();
    block.setName(myResources.getString("BLOCK"));

    myLineChart.getData().addAll(percolate, open, block);
  }

  @Override
  public void updateStat() {
    List<Double> stat = mySimulator.getStat();

    block.getData().add(new XYChart.Data(stepCtr, stat.get(0)));
    open.getData().add(new XYChart.Data(stepCtr, stat.get(1)));
    percolate.getData().add(new XYChart.Data(stepCtr, stat.get(2)));
    stepCtr ++;
  }
}
