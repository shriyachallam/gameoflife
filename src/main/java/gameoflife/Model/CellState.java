package gameoflife.Model;

/**
 * Cell state, including all the possible states in each model. The interface contains two method
 * used in every enum class: getCode returns the integer representation of state, and rotateState update state
 * correspondingly.
 */

public class CellState {

  public interface State {
    int getCode();
    State rotateState();

    static void CellStateError(int code, int max_code) {
      if (code > max_code || code < 0) {
        throw new IllegalArgumentException("Cell state out of bounds for simulation type.");
      }
    }
  }

  public enum FireCellState implements State {

    EMPTY(0),
    TREE(1),
    BURNING(2);

    private final int code;

    FireCellState(int code) {
      this.code = code;
    }
    @Override
    public int getCode() {
      return code;
    }

    @Override
    public State rotateState() {
      return (State) findState(this.code+1);
    }

    public static Enum findState(int code) {
      State.CellStateError(code, 2);
      return switch (code) {
        default -> EMPTY;
        case 1 -> TREE;
        case 2 -> BURNING;
      };
    }
  }

  public enum TumorCellState implements State {
    EMPTY(0),
    STEM(1),
    TRANSITORY(2),
    DEAD(3),
    MOVINGSTEM(4);

    private final int code;

    TumorCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return (State) findState(this.code+1);
    }

    public static Enum findState(int code) {
      State.CellStateError(code, 4);

      return switch (code) {
        default -> EMPTY;
        case 1 -> STEM;
        case 2 -> TRANSITORY;
        case 3 -> DEAD;
        case 4 -> MOVINGSTEM;
      };
    }

  }

  public enum ConwayCellState implements State {
    OCCUPIED(0),
    EMPTY(1);

    private final int code;

    ConwayCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return (State) findState(this.code+1);
    }
    public static Enum findState(int code) {
      State.CellStateError(code, 1);
      return switch (code) {
        default -> OCCUPIED;
        case 1 -> EMPTY;
      };
    }
  }

  public enum SegregateCellState implements State {
    X(2),
    O(1),
    EMPTY(0);

    private final int code;

    SegregateCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return findState(this.code+1);
    }

    public static SegregateCellState findState(int code) {
      State.CellStateError(code, 2);
      switch (code) {
        case 1:
          return O;
        case 2:
          return X;
        default:
          return EMPTY;
      }
    }
  }

  public enum WaTorCellState implements State {
    FISH(1),
    SHARK(2),
    EMPTY(0);

    private final int code;

    WaTorCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return findState(this.code+1);
    }

    public static WaTorCellState findState(int code) {
      State.CellStateError(code, 2);

      switch (code) {
        case 1:
          return FISH;
        case 2:
          return SHARK;
        default:
          return EMPTY;
      }
    }
  }

  public enum PercolateCellState implements State {
    BLOCK(0),
    OPEN(1),
    PERCOLATE(2);

    private final int code;

    PercolateCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return findState(this.code+1);
    }

    public static PercolateCellState findState(int code) {
      State.CellStateError(code, 2);

      switch (code) {
        case 1:
          return OPEN;
        case 2:
          return PERCOLATE;
        default:
          return BLOCK;
      }
    }

  }

  public enum ForagingAntCellState implements State {

    EMPTY(0),
    FOOD(1),
    NEST(2),
    ANT(3);

    private final int code;

    ForagingAntCellState(int code) {
      this.code = code;
    }

    @Override
    public int getCode() {
      return code;
    }

    public State rotateState() {
      return findState(this.code+1);
    }

    public static ForagingAntCellState findState(int code) {
      State.CellStateError(code, 3);

      switch (code) {
        default:
          return EMPTY;
        case 1:
          return FOOD;
        case 2:
          return NEST;
        case 3:
          return ANT;
      }
    }

  }
}
