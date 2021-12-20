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
 * Read only model which will be passed to view.
 */
public interface ReadOnlyModel {


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
   * Get the  player of the game.
   *
   * @return the player object of the dungeon
   */
  PlayerInterface getPlayer();

  /**
   * Gets player description.
   *
   * @return number of rows in the dungeon.
   */
  StringBuilder getPlayerDesc();

  /**
   * Get the current moves of the player.
   * @param currLoc current location of player
   * @return the possible moves of the player from current location
   */
  EnumSet<DirectionEnum> getPossibleMoves(Map.Entry<Integer, Integer> currLoc);

  /**
   * Returns the treasure locations along with value of treasure in dungeon.
   *
   * @return a map of location , treasure type and value.
   */
  Map<Map.Entry<Integer,Integer>, TreeMap<TreasureEnum, Integer>> getTreasureList();

  /**
   * Returns the arrow locations along with value of arrow in dungeon.
   *
   * @return a map of location , arrow type and value.
   */
  Map<Map.Entry<Integer,Integer>, TreeMap<ArrowEnum, Integer>> getArrowList();

  /**
   * Make a particular specified move for the player.
   *
   * @param direction direction to move to.
   * @return Treasures/Arrow/smell level available in the new location
   * @throws IllegalArgumentException cannot make move in given direction.
   */
  StringBuilder makeMove(DirectionEnum direction);


  /**
   * Makes player enter the dungeon.
   *
   * @return smell level if present in start location.
   */
  StringBuilder enterPlayer();

  /**
   * Makes player pick up treasure/arrow.
   *
   * @param option of treasure.
   *
   */
  StringBuilder pickUpTreasure(String option);

  /**
   * Method to shoot arrow.
   * @param direction the direction it is supposed to go in
   * @return whether monster was hit/killed/missed.
   */
  String shootArrow(DirectionEnum direction, int dist);

  /**
   * Gives monster list of dungeon .
   *
   * @return location with monster.
   */
  Map<Map.Entry<Integer, Integer>, MonsterInterface> getMonsterList();

  /**
   * gets whether player has visited the location or not .
   *
   * @return location with monster.
   */
  Map<Map.Entry<Integer, Integer>, Boolean> getVisitedList();


  /**
   * Gets adjacency list.
   *
   * @return adjacency list of dungeon.
   */
  Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> getAdjacencyList();


}
