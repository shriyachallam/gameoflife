package gameoflife.Model;

import gameoflife.Model.CellState.State;
import java.util.List;
import java.util.Properties;

public abstract class Simulator {

  /**
   * Simulator Class, the link between model and interface, each simulator simulates a different model,
   * with different initial parameters. Important methods include simulate, getStat, and toString.
   */

  protected int numRow;
  protected int numCol;
  protected Grid myGrid;
  private String title;
  private String author;
  private String description;
  private String simType;
  private Properties myParamProps;
  private String cellShape;

  public Simulator(int numRow, int numCol, Properties paramProps, String cellShape) {
    this.numRow = numRow;
    this.numCol = numCol;
    this.myParamProps=paramProps;
    this.cellShape = cellShape;
  }

  public void setInfo(String title, String author, String description, String simType, String cellShape) {
    this.title = title;
    this.author = author;
    this.description = description;
    this.simType = simType;
  }

  /*
  Simulate method: move one step further
   */

  public void simulate() {
    myGrid.step();
  }

  /*
  Accessor and Mutator
   */

  public Cell getCell(int row, int col) {
    return myGrid.getCell(row, col);
  }

  public int getRows() {
    return numRow;
  }

  public int getCols() {
    return numCol;
  }

  public String getCellShape() {
    return cellShape;
  }
  public String getSimType() {
    return simType;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getDescription() {
    return description;
  }

  public boolean getFinished() {
    boolean finished = true;

    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        if (!myGrid.getCell(i, j).getNextState().equals(myGrid.getCell(i, j).getCurrentState())) {
          finished = false;
        }
      }
    }

    return finished;
  }

  public void rotateCell(int row, int col){
    Cell c=getCell(row, col);
    c.setCurrentState((Enum) ((State)c.getCurrentState()).rotateState());
  }

  /*
  print the current state of cell matrix in the matrix form, with each integer representing the state.
   */

  public String toString() {
    String output = "";
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        output += myGrid.getCell(i, j).toString() + " ";

        if (j == numCol - 1) {
          output += "\n";
        }
      }
    }

    return output;
  }

  /*
  get the statistics for charts drawing, statistics type specified by model.
   */

  public abstract List<Double> getStat();

  public Properties getParams(){
    return myParamProps;
  }
}
