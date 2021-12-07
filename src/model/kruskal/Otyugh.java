package model.kruskal;

/**
 * This class represents an otyugh, which is a smelly monster.
 *
 */
public class Otyugh implements MonsterInterface {

  private final int id;
  private int health;


  /**
   * Constructor for dungeon.
   * @param id : monster no.
   *
   */
  public Otyugh(int id) throws IllegalArgumentException {
    if (id < 0 ) {
      throw new IllegalArgumentException("Monster id cannot be less than 0");
    }

    this.id = id;
    this.health = 100;
  }

  @Override
  public int getMonsterId() {
    return this.id;
  }


  @Override
  public int getMonsterHealth() {
    return this.health;
  }

  @Override
  public void setMonsterHealth(int newHealth) {
    this.health = newHealth;
  }



}