package model.dungeon;

import java.util.Map;

/**
 * This interface represents the methods that can be implemented to build a Dungeon
 * object.
 *
 */

public interface DungeonInterface extends ReadOnlyModel {

  int DAIMONDCOUNTPERROOM = 1;
  int SAPPHIRECOUNTPERROOM = 1;
  int RUBYCOUNTPERROOM = 1;




  /**
   * Tells if the dungeon is a wrapped  or not.
   *
   * @return if dungeon is wrapped or not
   */
  boolean isWrapped();

  /**
   * Gets interconnectivity of dungeon.
   *
   * @return interconnectivity.
   */
  int getInterconnectivity();





  /**
   * Get the starting point of the dungeon.
   *
   * @return starting point of the dungeon
   */
  Map.Entry<Integer, Integer> getStartingPoint();

  /**
   * Get the goal location of the dungeon.
   *
   * @return goal location of the dungeon
   */
  Map.Entry<Integer, Integer> getGoalPoint();



  /**
   * Prints the dungeon for debugging .
   *
   * @return String format of adjacency list of dungeon.
   */

  StringBuilder printDungeon();

  /**
   * Gives node info the dungeon for debugging .
   *
   * @return String format of adjacency list of dungeon.
   */
  StringBuilder nodeInfo();



  /**
   * Gives no of monster in dungeon.
   *
   * @return no of monster in dungeon.
   */
  int getNumberOfMonsters();



}
