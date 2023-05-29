package gameoflife.Model.WaTor;

public class Fish {

  /**
   * Fish Class, one of the two kinds of creatures in WaTor model. Mainly able to reproduce and provide
   * energy to the shark (be eaten, emmmm...)
   */

  protected final int fertilityThreshold;
  protected int fertilityCount;

  public Fish(int fertilityThreshold) {
    this.fertilityThreshold = fertilityThreshold;
    this.fertilityCount = fertilityThreshold;
  }

  /*
  With each move, the fish becomes more likely to reproduce.
   */

  public void updateVitals() {
    fertilityCount--;
  }

  public boolean canReproduce() {
    if (fertilityCount <= 0) {
      fertilityCount = fertilityThreshold;
      return true;
    } else {
      return false;
    }
  }

  public Fish makeChild() {
    return new Fish(fertilityThreshold);
  }
}
