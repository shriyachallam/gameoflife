package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * WaTorStat Class: Statistics include the number of sharks and fish in the pool.
 */

public class WaTorStat extends StatisticsView{

  private XYChart.Series fish;
  private XYChart.Series shark;
  int stepCtr = 0;


  public WaTorStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);
    fish = new XYChart.Series<Number, Number>();
    fish.setName(myResources.getString("FISH"));
    shark = new XYChart.Series<Number, Number>();
    shark.setName(myResources.getString("SHARK"));
    myLineChart.getData().addAll(fish, shark);
  }

  public void updateStat(){
    List<Double> stat = mySimulator.getStat();

    XYChart.Data curOccupied = new XYChart.Data(stepCtr, stat.get(0));
    fish.getData().add(curOccupied);
    XYChart.Data curEmpty = new XYChart.Data(stepCtr, stat.get(1));
    shark.getData().add(curEmpty);
    stepCtr ++;
  }
}
