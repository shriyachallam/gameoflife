package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 * TumorStat Class: Statistics include the number of STEM, Transitory and Total Cells in the pool.
 */


public class TumorStat extends StatisticsView{

  private XYChart.Series stem;
  private XYChart.Series transitory;
  private XYChart.Series all;
  int stepCtr = 0;


  public TumorStat(LineChart myChart, Simulator mySimulator){
    super(myChart, mySimulator);
    stem = new XYChart.Series<Number, Number>();
    stem.setName(myResources.getString("STEM"));
    transitory = new XYChart.Series<Number, Number>();
    transitory.setName(myResources.getString("TRANSITORY"));
    all = new XYChart.Series<Number, Number>();
    all.setName(myResources.getString("ALL"));
    myLineChart.getData().addAll(stem, transitory, all);
  }

  public void updateStat(){
    List<Double> stat = mySimulator.getStat();

    XYChart.Data curStem = new XYChart.Data(stepCtr, stat.get(0));
    stem.getData().add(curStem);
    XYChart.Data curTransitory = new XYChart.Data(stepCtr, stat.get(1));
    transitory.getData().add(curTransitory);
    XYChart.Data curAll = new XYChart.Data(stepCtr, stat.get(2));
    all.getData().add(curAll);
    stepCtr ++;
  }



}
