package model.kruskal;

/**
 * This interface represents a monster, which can be of a smelly type.
 *
 */

public interface MonsterInterface {


  /**
   * gets the id of the monster.
   *
   *
   * @return monsterId.
   * */
  int getMonsterId();

  /**
   * gets the health of monster.
   *
   *
   * @return health of monster.
   * */
  int getMonsterHealth();

  /**
   * sets the locations of monster.
   *
   *
   * @param newHealth new health of monster.
   * */
  void setMonsterHealth(int newHealth);
}