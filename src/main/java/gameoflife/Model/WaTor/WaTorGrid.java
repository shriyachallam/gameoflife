package gameoflife.Model.WaTor;

import gameoflife.Model.Cell;
import gameoflife.Model.CellState;
import gameoflife.Model.Grid;
import java.util.List;
import javafx.geometry.Point2D;

public class WaTorGrid extends Grid {

  private final int fishFertility;
  private final int sharkFertility;
  private final int sharkEnergy;
  private final int sharkEnergyBonus;

  protected WaTorGrid(int numRow, int numCol, List<List<String>> cells, int fishFertility,
      int sharkFertility,
      int sharkEnergy, int sharkEnergyBonus) {
    super(numRow, numCol);
    this.fishFertility = fishFertility;
    this.sharkFertility = sharkFertility;
    this.sharkEnergy = sharkEnergy;
    this.sharkEnergyBonus = sharkEnergyBonus;
    initializeMatrix(cells);
    addMooreNeighbour();
  }

  protected void initializeMatrix(List<List<String>> cells) {
    for (int i = 0; i < numRow; i++) {
      for (int j = 0; j < numCol; j++) {
        Point2D point = new Point2D(i, j);
        Enum initialState = CellState.WaTorCellState.findState(
            Integer.parseInt(cells.get(i).get(j)));
        Fish creature = generateCreature(initialState);
        Cell c = new WaTorCell(point, initialState, initialState, creature);
        cellMatrix[i][j] = c;
      }
    }
  }

  public void updateCreature(Cell cell) {
    Enum initialState = cell.getCurrentState();
    Fish creature = generateCreature(initialState);
    ((WaTorCell)cell).setCreature(creature);
  }

  private Fish generateCreature(Enum initialState) {
    Fish creature = null;
    if (initialState == CellState.WaTorCellState.FISH) {
      creature = new Fish(fishFertility);
    } else if (initialState == CellState.WaTorCellState.SHARK) {
      creature = new Shark(sharkFertility, sharkEnergy, sharkEnergyBonus);
    }
    return creature;
  }
}
