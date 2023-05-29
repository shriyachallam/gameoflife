package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * ConwayState Class: Statistics include the number of occupied and empty in Game Of Life.
 */

public class ConwayStat extends StatisticsView {
  private final XYChart.Series occupied;
  private final XYChart.Series empty;
  int stepCtr = 0;

  public ConwayStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);

    occupied = new XYChart.Series<Number, Number>();
    occupied.setName(myResources.getString("OCCUPIED"));

    empty = new XYChart.Series<Number, Number>();
    empty.setName(myResources.getString("EMPTY"));

    myLineChart.getData().addAll(occupied, empty);
  }

  public void updateStat(){
    List<Double> stat = mySimulator.getStat();

    XYChart.Data curOccupied = new XYChart.Data(stepCtr, stat.get(0));
    occupied.getData().add(curOccupied);
    XYChart.Data curEmpty = new XYChart.Data(stepCtr, stat.get(1));
    empty.getData().add(curEmpty);
    stepCtr ++;
  }
}
