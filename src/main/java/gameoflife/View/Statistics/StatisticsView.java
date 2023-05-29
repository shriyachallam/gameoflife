package gameoflife.View.Statistics;

import gameoflife.Model.Simulator;
import java.util.ResourceBundle;
import javafx.scene.chart.LineChart;

/**
 * StatisticsView class: providing Line chart for the interface. Main methods include updateStat.
 */

public abstract class StatisticsView {

  protected LineChart myLineChart;
  protected Simulator mySimulator;

  public static final String INTERNAL_CONFIGURATION = "cellsociety.text";
  protected final ResourceBundle myResources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);

  /*
  The constructor relies on the kind of simulator to provide statistics.
  and then creating a line chart based on the type of data.
   */

  public StatisticsView(LineChart myLineChart, Simulator mySimulator){
    myLineChart.setTitle(myResources.getString("chartTitle"));
    this.myLineChart = myLineChart;
    this.mySimulator = mySimulator;
  }

  /*
  UpdateStat: adding the current data point into the list of data, the lineChart automatically updates itself.
   */

  public abstract void updateStat();

  public void clear(){
    myLineChart.getData().clear();
  }
  public LineChart getChart(){
    return myLineChart;
  }

}
