package gameoflife.Model.WaTor;

public class Shark extends Fish {

  /**
   * Shark Class, one of the two kinds of creatures in WaTor model. Mainly able to eat fish
   */

  private final int energyThreshold;
  private final int energyBonus;
  private int energy;

  public Shark(int fertilityThreshold, int energyThreshold, int energyBonus) {
    super(fertilityThreshold);
    this.energyThreshold = energyThreshold;
    this.energy = energyThreshold;
    this.energyBonus = energyBonus;
  }

  public void incrementEnergy() {
    energy += energyBonus;
  }

  public boolean isAlive() {
    return energy != 0;
  }

  /*
  for each move, the shark becomes less vital and more hungry.
   */
  @Override
  public void updateVitals() {
    super.updateVitals();
    energy--;
  }

  @Override
  public Fish makeChild() {
    return new Shark(this.fertilityThreshold, this.energyThreshold, this.energyBonus);
  }
}
