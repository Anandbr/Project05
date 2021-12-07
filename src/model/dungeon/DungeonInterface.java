package model.dungeon;

import model.kruskal.ArrowEnum;
import model.kruskal.DirectionEnum;
import model.kruskal.MonsterInterface;
import model.kruskal.TreasureEnum;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;



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
   * Gets number of rows in the dungeon.
   *
   * @return number of rows in the dungeon.
   */
  int getNumberOfRows();

  /**
   * Gets number of cols in the dungeon.
   *
   * @return number of cols in the dungeon.
   */
  int getNumberOfColumns();

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
   * Get the  player of the game.
   *
   * @return the player object of the dungeon
   */
  PlayerInterface getPlayer();



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
