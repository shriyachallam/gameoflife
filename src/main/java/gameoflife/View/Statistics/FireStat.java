package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;

/**
 * FireStat Class: Statistics include the number of empty, tree, and burning cells in the forest.
 */


public class FireStat extends StatisticsView{

  private final XYChart.Series Empty;
  private final XYChart.Series Tree;
  private final XYChart.Series Burning;
  int stepCtr = 0;


  public FireStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);

    Empty = new XYChart.Series<Number, Number>();
    Empty.setName(myResources.getString("EMPTY"));

    Tree = new XYChart.Series<Number, Number>();
    Tree.setName(myResources.getString("TREE"));

    Burning = new XYChart.Series<Number, Number>();
    Burning.setName(myResources.getString("BURNING"));

    myLineChart.getData().addAll(Empty, Tree, Burning);
  }

  @Override
  public void updateStat() {
    List<Double> stat = mySimulator.getStat();

    Empty.getData().add(new XYChart.Data(stepCtr, stat.get(0)));
    Tree.getData().add(new XYChart.Data(stepCtr, stat.get(1)));
    Burning.getData().add(new XYChart.Data(stepCtr, stat.get(2)));

    stepCtr ++;
  }
}
