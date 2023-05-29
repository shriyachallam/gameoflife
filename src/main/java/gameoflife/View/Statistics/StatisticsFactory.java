package gameoflife.View.Statistics;
import gameoflife.Model.Simulator;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
/**
 * This class is responsible for creating the correct StatisticsView object
 */
public class StatisticsFactory {
  /**
   * Creates a new StatisticsView object
   * @param simulation the simulation to be displayed
   * @return the StatisticsView object
   */
  public static StatisticsView createStatisticsView(Simulator simulation){
    NumberAxis xAxis = new NumberAxis();
    xAxis.setAutoRanging(true);
    NumberAxis yAxis = new NumberAxis(0, simulation.getCols()*simulation.getRows(), 50);
    LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
    return switch (simulation.getSimType()) {
      case "Conway" -> new ConwayStat(chart, simulation);
      case "Segregate" -> {
        yAxis=new NumberAxis(0, 100, 10);
        chart = new LineChart<>(xAxis, yAxis);
        yield new SegregationStat(chart, simulation);
      }
      case "Percolate" -> new PercolationStat(chart, simulation);
      case "WaTor" -> new WaTorStat(chart, simulation);
      case "Fire" -> new FireStat(chart, simulation);
      case "Tumor" -> {
        yAxis=new NumberAxis(0, 100, 10);
        chart = new LineChart<>(xAxis, yAxis);
        yield new TumorStat(chart, simulation);
      }
      default -> null;
    };
  }

}
