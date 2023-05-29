package gameoflife.Model.WaTor;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState.State;
import gameoflife.Model.CellState.WaTorCellState;
import gameoflife.Model.Simulator;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class WaTorSimulator extends Simulator {
  private final int DEFAULT_FISH_FERTILITY = 5;
  private final int DEFAULT_SHARK_FERTILITY = 2;
  private final int DEFAULT_SHARK_ENERGY = 5;
  private final int DEFAULT_SHARK_ENERGY_BONUS = 4;

  public WaTorSimulator(int numRow, int numCol, List<List<String>> cells, Properties paramProps, String cellShape) {
    super(numRow, numCol, paramProps, cellShape);
    int fishFertility;
    int sharkFertility;
    int sharkEnergy;
    int sharkEnergyBonus;

    if (paramProps.contains("fishFertility")) {
      fishFertility = Integer.parseInt(paramProps.getProperty("fishFertility"));
    }
    else {
      fishFertility = DEFAULT_FISH_FERTILITY;
    }

    if (paramProps.contains("sharkFertility")) {
      sharkFertility = Integer.parseInt(paramProps.getProperty("sharkFertility"));
    }
    else {
      sharkFertility = DEFAULT_SHARK_FERTILITY;
    }
    if (paramProps.contains("sharkEnergy")) {
      sharkEnergy = Integer.parseInt(paramProps.getProperty("sharkEnergy"));
    }
    else {
      sharkEnergy = DEFAULT_SHARK_ENERGY;
    }

    if (paramProps.contains("sharkEnergyBonus")) {
      sharkEnergyBonus = Integer.parseInt(paramProps.getProperty("sharkEnergyBonus"));
    }
    else {
      sharkEnergyBonus = DEFAULT_SHARK_ENERGY_BONUS;
    }
    myGrid = new WaTorGrid(numRow, numCol, cells, fishFertility, sharkFertility, sharkEnergy,
        sharkEnergyBonus);
  }

  @Override
  public void rotateCell(int row, int col){
    Cell c=getCell(row, col);
    c.setCurrentState((Enum) ((State)c.getCurrentState()).rotateState());
    ((WaTorGrid)myGrid).updateCreature(c);
  }

  @Override
  public List<Double> getStat() {

    List<Double> stat = new ArrayList<>();
    double fish = 0;
    double shark = 0;

    for(int i=0; i<numRow; i++){
      for(int j=0; j<numCol; j++){
        switch((WaTorCellState) myGrid.getCell(i, j).getCurrentState()){
          case SHARK -> shark ++;
          case FISH -> fish ++;
        }
      }
    }

    stat.add(fish);
    stat.add(shark);

    return stat;
  }
}

