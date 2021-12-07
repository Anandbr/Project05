package model.kruskal;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This interface represents the methods that can be implemented for using LocationGraph.
 *
 */

public interface LocationInterface {

  /**
   * Assigns Treasure at % of locations (at random) that the player can pick.
   * @param treasurePercent : % of locations in Dungeon that have treasure
   *                   that the player can pick.
   * */
  void assignTreasure(double treasurePercent,boolean randomType, Map.Entry<Integer, Integer>
          startingPoint, Map.Entry<Integer, Integer> goalPoint);

  /**
   * Assigns arrows at % of locations (at random) that the player can pick.
   * @param treasurePercent : % of locations in Dungeon that have treasure
   *                   that the player can pick.
   * */
  void assignArrow(double treasurePercent,boolean randomType, Map.Entry<Integer, Integer>
          startingPoint, Map.Entry<Integer, Integer> goalPoint);

  /**
   * Assigns monsters of locations (at random) that the player can slay.
   * @param monsterSet : list of monsters
   * @param goal :goal point
   * @param start  :start point
   * @param actual : type of random
   * */

  void assignMonster( List<MonsterInterface> monsterSet, Map.Entry<Integer, Integer> goal,
                      Map.Entry<Integer, Integer> start, boolean actual);

  /**
   * Produce the list of  possible moves of the player (North, South, East or West) from their
   * current location.
   * @param location : location from which possible moves of the player are to be determined
   * @throws IllegalArgumentException : if location is not in dungeon
   * */
  EnumSet<DirectionEnum> getPossibleMoves(Map.Entry<Integer, Integer> location);

  /**
   * Gets the number of nodes in the dungeonGraph.
   * */
  int getNumberOfNodes();

  /**
   * Gets the adjacency list of the LocationGraph.
   * */
  Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> getLocAdjacencyList();

  /**
   * Gets the treasureList of dungeonGraph.
   * */
  Map<Map.Entry<Integer,Integer>, TreeMap<TreasureEnum, Integer>> getTreasureList();

  /**
   * Gets the edgeList of dungeonGraph.
   * */
  List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> getEdges();

  /**
   * Makes the graph.
   *
   * @param edges the edge list for making graph.
   * */
  void createMazeGraphFromEdges(ArrayList<Map.Entry<Map.Entry<Integer, Integer>,
          Map.Entry<Integer, Integer>>> edges);


  /**
   * Check if a node is a tunnel.
   *
   * @param node the code we are checking for.
   * @return true if it is tunnel, else false.
   * */

  boolean isTunnel(Map.Entry<Integer, Integer> node);

  /**
   * gets the locations of arrows.
   *
   *
   * @return location of arrow with type.
   * */
  Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> getArrowList();


  /**
   * gets the locations of monster.
   *
   *
   * @return location of monster object.
   * */
  Map<Map.Entry<Integer, Integer>, MonsterInterface> getMonsterList();


  Map<Map.Entry<Integer, Integer>, Boolean> getIsVisitedList();

}
