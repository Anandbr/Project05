package model.kruskal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class represents the Location Graph which will be used by Builder.
 *
 */

public class LocationGraph implements LocationInterface {

  private final int rows;
  private final int columns;
  private final boolean isWrapped;
  private final Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> adjList;
  private final Map<Map.Entry<Integer,Integer>, TreeMap<TreasureEnum, Integer>> treasureList;
  private final Map<Map.Entry<Integer,Integer>, TreeMap<ArrowEnum, Integer>> arrowList;
  private final Map<Map.Entry<Integer,Integer>, MonsterInterface> monsterLocationList;
  private final Map<Map.Entry<Integer,Integer>, Boolean> isVistedList ;

  private final Random mockRandom = new Random(32);
  private final Random actualRandom = new Random();


  /**
   * Creates a graph structure for the dungeon.
   * @param numberOfRows : number of rows in the dungeon.
   * @param numberOfColumns : number of columns in the dungeon.
   * */
  public LocationGraph(int numberOfRows, int numberOfColumns, boolean isWrapped) {
    if (numberOfColumns < 0 || numberOfRows < 0) {
      throw  new IllegalArgumentException("Number of column or row can't be 0 or less than 0");
    }
    this.rows = numberOfRows;
    this.columns = numberOfColumns;
    this.adjList = new HashMap<>();
    this.isWrapped = isWrapped;
    this.treasureList = new HashMap<>();
    this.arrowList = new HashMap<>();
    this.monsterLocationList = new HashMap<>();
    this.isVistedList = new HashMap<>();
    constructGraph();
  }

  private void constructGraph() {

    Map.Entry<Integer, Integer> temp;
    for (int i = 0; i < rows ; i++ ) {

      for (int j = 0; j < columns; j++) {

        List<Map.Entry<Integer, Integer>> nodeList = new ArrayList<>();
        if (isWrapped || i + 1  < rows) {
          temp = new AbstractMap.SimpleEntry<>((i + 1 + rows) % rows , j);
          nodeList.add(temp);
        }

        if (isWrapped || j + 1 < columns) {
          temp = new AbstractMap.SimpleEntry<>(i, (j + 1 + columns) % columns);
          nodeList.add(temp);
        }

        if (isWrapped || i - 1 >= 0) {
          temp = new AbstractMap.SimpleEntry<>((i - 1 + rows ) % rows, j);
          nodeList.add(temp);
        }

        if (isWrapped || j - 1 >= 0) {
          temp = new AbstractMap.SimpleEntry<>(i, (j -  1 + columns ) % columns);
          nodeList.add(temp);
        }

        Map.Entry<Integer, Integer> node = new AbstractMap.SimpleEntry<>(i, j);
        adjList.put(node, nodeList);
        TreeMap<TreasureEnum, Integer> tempTreasure = new TreeMap<>();


        tempTreasure.put(TreasureEnum.DIAMOND, 0);
        tempTreasure.put(TreasureEnum.RUBY, 0);
        tempTreasure.put(TreasureEnum.SAPPHIRE, 0);

        treasureList.put(node, tempTreasure);
        TreeMap<ArrowEnum, Integer> tempArrow = new TreeMap<>();

        tempArrow.put(ArrowEnum.CROOKED_ARROW,0);
        arrowList.put(node,tempArrow);

        monsterLocationList.put(node,null);
        //changed visited
        //TODO test
        isVistedList.put(node,false);


      }
    }
  }

  @Override
  public boolean isTunnel(Map.Entry<Integer, Integer> node) {
    return adjList.get(node).size() == 2;
  }


  /**
   * Assigns Treasure% of locations (at random) that the player can pick.
   * @param treasurePercent : % of locations in Dungeon that have treasure
   *                   that the player can pick.
   * */
  @Override
  public void assignTreasure(double treasurePercent, boolean actual, Map.Entry<Integer, Integer>
          startingPoint, Map.Entry<Integer, Integer> goalPoint) {
    if (treasurePercent < 0.0 || treasurePercent > 100.0) {
      throw  new IllegalArgumentException("treasure percent invalid.");
    }
    Set<String> hash_Set = new HashSet<String>();
    int count = 0;
    for (var tempAdjList: adjList.entrySet()) {
      if (isTunnel(tempAdjList.getKey())) {
        count++;
        Map.Entry<Integer, Integer> tunnel = tempAdjList.getKey();
        int tunnelKey1 = tunnel.getKey();
        int tunnelKey2 = tunnel.getValue();
        hash_Set.add( tunnelKey1 + "." + tunnelKey2);
      }
    }

    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(treasureList.keySet());
    TreeMap<TreasureEnum, Integer> treasureAdd = new TreeMap<>();
    treasureAdd.put(TreasureEnum.DIAMOND, 1);
    treasureAdd.put(TreasureEnum.RUBY, 1);
    treasureAdd.put(TreasureEnum.SAPPHIRE, 1);

    int treasureCount = (int)(Math.ceil((adjList.size() - count - 2) * treasurePercent) / 100);
    Map.Entry<Integer, Integer> temp;
    while (treasureCount != 0) {
      if (actual) {
        temp = list.get(actualRandom.nextInt(list.size()));
      }
      else {
        temp = list.get(mockRandom.nextInt(list.size()));
      }
      String tempKey = temp.getKey() + "." + temp.getValue();
      if ((treasureList.get(temp).get(TreasureEnum.RUBY) == 0)
              && (treasureList.get(temp).get(TreasureEnum.SAPPHIRE) == 0)
              && (treasureList.get(temp).get(TreasureEnum.DIAMOND) == 0)
              && (!(hash_Set.contains(tempKey)))
              && (!(temp.getKey().equals(startingPoint.getKey())
              && temp.getValue().equals(startingPoint.getValue())))
              && (!(temp.getKey().equals(goalPoint.getKey())
              && temp.getValue().equals(goalPoint.getValue())))) {
        treasureList.put(temp, treasureAdd);
        treasureCount--;
      }
    }
  }


  /**
   * Assigns Treasure% of locations (at random) that the player can pick.
   * @param treasurePercent : % of locations in Dungeon that have arrow/treasure
   *                   that the player can pick.
   * */
  @Override
  public void assignArrow(double treasurePercent, boolean actual, Map.Entry<Integer, Integer>
          startingPoint, Map.Entry<Integer, Integer> goalPoint) {
    if (treasurePercent < 0.0 || treasurePercent > 100.0) {
      throw  new IllegalArgumentException("Arrow percent invalid.");
    }

    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(arrowList.keySet());
    TreeMap<ArrowEnum, Integer> arrowAdd = new TreeMap<>();
    arrowAdd.put(ArrowEnum.CROOKED_ARROW, 1);

    int arrowCount = (int)(Math.ceil(((adjList.size()) - 2) * treasurePercent) / 100);
    Map.Entry<Integer, Integer> temp;
    while (arrowCount != 0) {
      if (actual) {
        temp = list.get(actualRandom.nextInt(list.size()));
      }
      else {
        temp = list.get(mockRandom.nextInt(list.size()));
      }
      if ((arrowList.get(temp).get(ArrowEnum.CROOKED_ARROW) == 0)
              && (!(temp.getKey().equals(startingPoint.getKey())
              && temp.getValue().equals(startingPoint.getValue())))
              && (!(temp.getKey().equals(goalPoint.getKey())
              && temp.getValue().equals(goalPoint.getValue()))) ) {
        arrowList.put(temp, arrowAdd);
        arrowCount--;
      }
    }
  }

  /**
   * Assigns Treasure% of locations (at random) that the player can pick.
   * @param monsterList monsterlocations
   * @param goal goal point
   * @param actual randome or not
   *
   * */
  @Override
  public void assignMonster(List<MonsterInterface> monsterList, Map.Entry<Integer,Integer> goal,
                            Map.Entry<Integer,Integer> start, boolean actual) {
    if (monsterList.size() < 1) {
      throw  new IllegalArgumentException("No of Monstors invalid.");
    }


    Set<String> hash_Set = new HashSet<String>();
    int count = 0;
    for (var tempAdjList: adjList.entrySet()) {
      if (isTunnel(tempAdjList.getKey())) {
        count++;
        Map.Entry<Integer, Integer> tunnel = tempAdjList.getKey();
        int tunnelKey1 = tunnel.getKey();
        int tunnelKey2 = tunnel.getValue();
        hash_Set.add( tunnelKey1 + "." + tunnelKey2);
      }
    }

    List<Map.Entry<Integer, Integer>> list = new ArrayList<>(monsterLocationList.keySet());

    //placing first monster in goal location and removing the scope of start.
    monsterLocationList.put(goal,monsterList.get(0));
    monsterList.remove(monsterList.get(0));
    list.remove(goal);
    list.remove(start);
    int monsterCount = monsterList.size();

    Map.Entry<Integer, Integer> temp;
    while (monsterCount != 0) {
      if (actual) {
        temp = list.get(actualRandom.nextInt(list.size()));
      }
      else {
        temp = list.get(mockRandom.nextInt(list.size()));
      }
      String tempKey = temp.getKey() + "." + temp.getValue();
      if ((monsterLocationList.get(temp) == null)
              && (!hash_Set.contains(tempKey))) {
        monsterLocationList.put(temp, monsterList.get(monsterCount - 1));
        monsterList.remove(monsterList.get(monsterCount - 1));
        monsterCount--;
      }


    }
  }



  /**
   * Produce the list of  possible moves of the player (North, South, East or West) from their
   * current location.
   * @param location : location from which possible moves of the player are to be determined
   * @throws IllegalArgumentException : if location is not in dungeon
   * */
  @Override
  public EnumSet<DirectionEnum> getPossibleMoves(Map.Entry<Integer, Integer> location) {
    if (location.getKey() >= rows || location.getKey() < 0
            || location.getValue() >= columns || location.getValue() < 0 ) {
      throw new IllegalArgumentException("Invalid location.");
    }
    List<Map.Entry<Integer, Integer>> list = adjList.get(location);
    EnumSet<DirectionEnum> directions = EnumSet.noneOf(DirectionEnum.class);
    Map.Entry<Integer, Integer> node;

    //North
    if (isWrapped) {
      node = new AbstractMap.SimpleEntry<>((location.getKey() - 1 + rows) % rows,
              location.getValue());
    }
    else {
      node = new AbstractMap.SimpleEntry<>((location.getKey() - 1 ), location.getValue());
    }
    if (list.contains(node)) {
      directions.add(DirectionEnum.NORTH);
    }

    //South
    if (isWrapped) {
      node = new AbstractMap.SimpleEntry<>((location.getKey() + 1 + rows) % rows,
              location.getValue());
    }
    else {
      node = new AbstractMap.SimpleEntry<>((location.getKey() + 1), location.getValue());
    }
    if (list.contains(node)) {
      directions.add(DirectionEnum.SOUTH);
    }

    //East
    if (isWrapped) {
      node = new AbstractMap.SimpleEntry<>(location.getKey(),
              (location.getValue() + 1 + columns) % columns);
    }
    else {
      node = new AbstractMap.SimpleEntry<>(location.getKey(), (location.getValue() + 1));
    }
    if (list.contains(node)) {
      directions.add(DirectionEnum.EAST);
    }

    //West
    if (isWrapped) {
      node = new AbstractMap.SimpleEntry<>(location.getKey(),
              (location.getValue() - 1 + columns) % columns);
    }
    else {
      node = new AbstractMap.SimpleEntry<>(location.getKey(), (location.getValue() - 1) );
    }
    if (list.contains(node)) {
      directions.add(DirectionEnum.WEST);
    }
    return directions;
  }

  /**
   * Gets the number of nodes in the dungeonGraph.
   * */
  @Override
  public int getNumberOfNodes() {
    return adjList.size();
  }

  @Override
  public void createMazeGraphFromEdges(ArrayList<Map.Entry<Map.Entry<Integer, Integer>,
          Map.Entry<Integer, Integer>>> edges) {

    adjList.replaceAll((p, v) -> new ArrayList<>());
    for (Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> pair : edges) {
      List<Map.Entry<Integer, Integer>> temp = adjList.get(pair.getKey());
      temp.add(pair.getValue());
      adjList.put(pair.getKey(), temp);
      List<Map.Entry<Integer, Integer>> temp2 = adjList.get(pair.getValue());
      temp2.add(pair.getKey());
      adjList.put(pair.getValue(), temp2);
    }
  }

  @Override
  public List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> getEdges() {
    List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> edges =
            new ArrayList<>();
    HashSet<Map.Entry<Integer, Integer>> visited = new HashSet<>();
    for (Map.Entry<Integer, Integer> pair : adjList.keySet() ) {
      visited.add(pair);
      for (Map.Entry<Integer, Integer> pair1 : adjList.get(pair)) {
        if (!visited.contains(pair1)) {
          edges.add(new AbstractMap.SimpleEntry<>(pair, pair1));
        }
      }
    }
    return edges;
  }

  /**
   * Gets the adjacency list of the DungeonGraph.
   * */
  @Override
  public Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer,
          Integer>>> getLocAdjacencyList() {
    return adjList;
  }

  /**
   * Gets the treasure list of the graph.
   * */
  @Override
  public Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> getTreasureList() {
    return treasureList;
  }

  /**
   * Gets the arrow list of the graph.
   * */
  @Override
  public Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> getArrowList() {
    return arrowList;
  }

  @Override
  public Map<Map.Entry<Integer, Integer>, MonsterInterface> getMonsterList() {
    return monsterLocationList;
  }

  @Override
  public Map<Map.Entry<Integer, Integer>, Boolean> getIsVisitedList() {
    return isVistedList;
  }



  @Override
  public String toString() {
    return "LocationGraph{" + "rows=" + rows + ", columns=" + columns
            + ", isWrapped=" + isWrapped + ", adjList=" + adjList.toString() + '}';
  }


}

