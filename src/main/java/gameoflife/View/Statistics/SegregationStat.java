package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * SegregationStat Class: Statistics include the satisfaction percentage of the population
 */

public class SegregationStat extends StatisticsView{

  private XYChart.Series satisfaction;

  int stepCtr = 0;

  public SegregationStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);
    myLineChart.setTitle(myResources.getString("satisfactionPercentage"));
    satisfaction = new XYChart.Series<Number, Number>();
    satisfaction.setName(myResources.getString("satisfied"));
    myLineChart.getData().add(satisfaction);
  }

  @Override
  public void updateStat() {
    List<Double> stat = mySimulator.getStat();
    satisfaction.getData().add(new XYChart.Data(stepCtr, stat.get(0)));
    stepCtr ++;
  }
}
