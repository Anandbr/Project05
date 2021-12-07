package model.dungeon;

import static model.kruskal.TreasureEnum.DIAMOND;
import static model.kruskal.TreasureEnum.RUBY;
import static model.kruskal.TreasureEnum.SAPPHIRE;

import model.kruskal.ArrowEnum;
import model.kruskal.DirectionEnum;
import model.kruskal.DungeonBuilder;
import model.kruskal.DungeonBuilderInterface;
import model.kruskal.LocationGraph;
import model.kruskal.LocationInterface;
import model.kruskal.MonsterInterface;
import model.kruskal.Otyugh;
import model.kruskal.TreasureEnum;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;



/**
 * This class represents an Abstract Dungeon. Dungeons can be of 2 types: wrapped and unwrapped.
 *
 */


public abstract class AbstractDungeon implements DungeonInterface {

  protected final int numberOfRows;
  protected final int numberOfColumns;
  protected final boolean isWrapped;
  protected final int interconnectivity;
  protected final double treasurePercent;
  protected final int noOfMonster;
  protected LocationInterface dungeonGraph;
  protected   Map.Entry<Integer, Integer> startingPoint;
  protected   Map.Entry<Integer, Integer> goalPoint;
  protected List<MonsterInterface> monsterList;
  protected PlayerInterface player;
  protected boolean randomType;
  protected final Random mockRandom = new Random(32);
  protected final Random actualRandom = new Random();

  /**
   * Constructor for dungeon.
   * @param numberOfRows : number of rows in the dungeon.
   * @param numberOfColumns : number of columns in the dungeon
   * @param isWrapped : tells if dungeon is wrapping or not
   * @param interconnectivity : interconnectivity - specified by user.
   * @param treasurePercent :percentage of treasure input by client.
   * @param noOfMonster : no of monster input by client.
   * @param randomType : mock or actual random type
   * @throws IllegalArgumentException : if any arguments are invalid
   */
  protected AbstractDungeon(int numberOfRows, int numberOfColumns, boolean isWrapped,
                            int interconnectivity, double treasurePercent, int noOfMonster,
                            boolean randomType)
          throws IllegalArgumentException {

    if (numberOfRows <= 0 || numberOfColumns <= 0) {

      throw new IllegalArgumentException("Number of rows or column can't be 0 or less than 0");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("Interconnectivity degree can't be less than 0");
    }

    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
    this.isWrapped = isWrapped;
    this.randomType = randomType;
    if ((isWrapped && interconnectivity > calculateInterForWrappedMaze())
            || (!isWrapped && interconnectivity > calculateInterForUnwrappedMaze())) {
      throw new IllegalArgumentException("Interconnectivity degree given is more than possible. "
              + "Please reduce your interconnectivity degree");
    }

    this.treasurePercent = treasurePercent;
    this.noOfMonster = noOfMonster;
    this.interconnectivity = interconnectivity;
    this.dungeonGraph = new LocationGraph(numberOfRows, numberOfColumns, isWrapped);
    createDungeon();
    startCalc(this.randomType);
    goalCalc(this.randomType);
    this.monsterList = createMonsters(this.noOfMonster);
    this.dungeonGraph.assignMonster(monsterList,this.goalPoint,this.startingPoint,this.randomType);
    this.dungeonGraph.assignTreasure(treasurePercent, randomType,
            this.startingPoint, this.goalPoint);
    this.dungeonGraph.assignArrow(treasurePercent, randomType, this.startingPoint, this.goalPoint);
    this.player = new Player("Ninja");

  }

  private void createDungeon() {
    DungeonBuilderInterface modifiedKruskal = new DungeonBuilder();
    modifiedKruskal.createMazeWithKruskal(dungeonGraph, interconnectivity);

  }

  @Override
  public StringBuilder getPlayerDesc() {
    return player.printPlayerDescription();
  }

  private List<MonsterInterface> createMonsters(int noOfMonster) throws IllegalArgumentException {

    int noOfCaves = 0;

    for (var node : this.getAdjacencyList().keySet()) {
      if (!dungeonGraph.isTunnel(node)) {
        noOfCaves++;
      }
    }

    if ( noOfMonster > noOfCaves - 1) {
      throw new IllegalArgumentException("No of Monsters more than no of caves");
    }

    List<MonsterInterface> monsterList = new ArrayList<>();
    for (int i = 0 ; i < noOfMonster; i++) {
      MonsterInterface monster = new Otyugh(i + 1);
      monsterList.add(monster);

    }
    return monsterList;

  }

  private int calculateInterForUnwrappedMaze() {
    int totalEdges = 2 * numberOfRows * numberOfColumns  - numberOfRows
            - numberOfColumns ;
    return totalEdges - (numberOfRows * numberOfColumns)  + 1;
  }

  private int calculateInterForWrappedMaze() {
    int totalEdges = 2 * (numberOfRows + 1) * (numberOfColumns + 1 )  - (numberOfRows + 1)
            - (numberOfColumns + 1 ) ;
    return totalEdges - (numberOfRows * numberOfColumns)  + 1;
  }

  /**
   * Gets number of rows in the dungeon.
   *
   * @return number of rows in the dungeon.
   */
  @Override
  public int getNumberOfRows() {
    return numberOfRows;
  }

  @Override
  public int getNumberOfColumns() {
    return numberOfColumns;
  }

  /**
   * Tells if the dungeon is a wrapped  or not.
   *
   * @return if dungeon is wrapped or not
   */
  @Override
  public boolean isWrapped() {
    return this.isWrapped;
  }

  /**
   * Gets interconnectivity of dungeon.
   *
   * @return interconnectivity.
   */
  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  /**
   * Get the current moves of the player.
   *
   * @return the possible moves of the player from current location
   */

  //TODO check

  @Override
  public EnumSet<DirectionEnum> getPossibleMoves(Map.Entry<Integer, Integer> currLoc) {
    return dungeonGraph.getPossibleMoves(currLoc);
  }

  @Override
  public Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> getAdjacencyList() {
    return dungeonGraph.getLocAdjacencyList();
  }

  /**
   * Get the  player of the game.
   *
   * @return the player object of the dungeon.
   */
  @Override
  public PlayerInterface getPlayer() {
    return player;
  }

  /**
   * Returns the treasure locations along with value of treasure in dungeon.
   *
   * @return a map of location , treasure type and value.
   */
  @Override
  public Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> getTreasureList() {
    return dungeonGraph.getTreasureList();
  }

  @Override
  public Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> getArrowList() {
    return dungeonGraph.getArrowList();
  }


  /**
   * Get the starting point of the dungeon.
   *
   * @return starting point of the dungeon
   */
  @Override
  public Map.Entry<Integer, Integer> getStartingPoint() {
    return startingPoint;
  }

  /**
   * Get the goal location of the dungeon.
   *
   * @return goal location of the dungeon
   */
  @Override
  public Map.Entry<Integer, Integer> getGoalPoint() {
    return goalPoint;
  }

  /**
   * Make a particular specified move for the player.
   *
   * @param direction direction to move to.
   * @return Treasures available in the new location
   * @throws IllegalArgumentException cannot make move in given direction.
   */
  @Override
  public StringBuilder makeMove(DirectionEnum direction) throws IllegalArgumentException {

    StringBuilder moveMessage = new StringBuilder();
    Map.Entry<Integer, Integer> currentLocation = player.getCurrentLocation();


    if (!getPossibleMoves(currentLocation).contains(direction)) {
      String template = "You can't go to %s.";
      String formattedString = String.format(template, direction.toString());
      throw new IllegalArgumentException(formattedString);
    }


    Map.Entry<Integer, Integer> newLocation;

    switch (direction) {
      case EAST:
        newLocation = new AbstractMap.SimpleEntry<>(currentLocation.getKey(),
                (currentLocation.getValue() + 1 + numberOfColumns) % numberOfColumns);
        break;
      case WEST:
        newLocation = new AbstractMap.SimpleEntry<>(currentLocation.getKey(),
                (currentLocation.getValue() - 1 + numberOfColumns) % numberOfColumns);
        break;
      case NORTH:
        newLocation = new AbstractMap.SimpleEntry<>((currentLocation.getKey() - 1 + numberOfRows)
                % numberOfRows, currentLocation.getValue());
        break;
      case SOUTH:
        newLocation = new AbstractMap.SimpleEntry<>((currentLocation.getKey() + 1 + numberOfRows)
                % numberOfRows, currentLocation.getValue());
        break;
      default: throw new IllegalArgumentException("Invalid direction.");
    }



    player.setCurrentLocation(newLocation);
    for(var node :dungeonGraph.getIsVisitedList().entrySet()) {
      if(node.getKey().getKey().equals(newLocation.getKey()) && node.getKey().getValue().equals(newLocation.getValue())) {
        dungeonGraph.getIsVisitedList().replace(node.getKey(),true);
      }
    }

    if (dungeonGraph.getMonsterList().get(newLocation) == null && newLocation.equals(goalPoint)) {
      player.setPlayerStatus(PlayerStatus.WINNER);
      moveMessage.append("Player Wins ! Game Over !\n");
      return moveMessage;


    }


    int rand;
    if (dungeonGraph.getMonsterList().get(newLocation) != null) {
      if (dungeonGraph.getMonsterList().get(newLocation).getMonsterHealth() == 50) {
        if (randomType) {
          rand = actualRandom.nextInt(2);
        }
        else {
          rand = mockRandom.nextInt(2);
        }
        if (rand == 1) {
          player.setPlayerStatus(PlayerStatus.DEAD);
          moveMessage.append("Player Eaten by Otyugh ! Game Over !\n");
          return moveMessage;
        }

        //when player reaches goal, with a half healthy monster
        if (newLocation.equals(goalPoint)) {
          player.setPlayerStatus(PlayerStatus.WINNER);
          moveMessage.append("Player Wins ! Game Over !\n");
          return moveMessage;
        }

      }
      else if (dungeonGraph.getMonsterList().get(newLocation).getMonsterHealth() == 100) {
        player.setPlayerStatus(PlayerStatus.DEAD);
        moveMessage.append( "Player Eaten by Otyugh ! Game Over !\n");
        return moveMessage;
      }
    }


    Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> treasureLoc
            = dungeonGraph.getTreasureList();

    Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> arrowLoc
            = dungeonGraph.getArrowList();

    Map<Map.Entry<Integer, Integer>, MonsterInterface> monsterLoc
            = dungeonGraph.getMonsterList();

    TreeMap<TreasureEnum, Integer> treasure = treasureLoc.get(newLocation);
    TreeMap<ArrowEnum, Integer> arrow = arrowLoc.get(newLocation);

    List<String> treasureList = new ArrayList<>();




    for (var tempTreasure : treasure.entrySet()) {
      if (tempTreasure.getKey().equals(TreasureEnum.DIAMOND) && tempTreasure.getValue() == 1) {
        treasureList.add(DIAMOND.name());
      }
      if (tempTreasure.getKey().equals(SAPPHIRE) && tempTreasure.getValue() == 1) {
        treasureList.add(SAPPHIRE.name());
      }
      if (tempTreasure.getKey().equals(TreasureEnum.RUBY) && tempTreasure.getValue() == 1) {
        treasureList.add(RUBY.name());
      }

    }

    for (var tempTreasure : arrow.entrySet()) {
      if (tempTreasure.getKey().equals(ArrowEnum.CROOKED_ARROW) && tempTreasure.getValue() == 1) {
        treasureList.add(ArrowEnum.CROOKED_ARROW.name());
      }
    }

    String smell = calculateSmellLevel(monsterLoc, newLocation);
    treasureList.add(smell);


    for (var items : treasureList) {
      if (items.equals("DIAMOND")) {
        moveMessage.append("YOU find :1 Daimond\n");
      }
      if (items.equals("RUBY")) {
        moveMessage.append("You find :1 Ruby \n");
      }
      if (items.equals("SAPPHIRE")) {
        moveMessage.append("You find :1 Sapphire \n");
      }
      if (items.equals("CROOKED_ARROW")) {
        moveMessage.append("You find :1 Arrow \n");
      }
      if (items.equals("No Smell")) {
        moveMessage.append("No Smell\n");
      }
      if (items.equals("High Smell")) {
        moveMessage.append("Something Smells TERRIBLE !!\n");
      }
      if (items.equals("Low Smell")) {
        moveMessage.append("Something Smells !!\n");
      }


    }

    return moveMessage;


  }


  private String calculateSmellLevel(Map<Map.Entry<Integer, Integer>, MonsterInterface> monsterLoc,
                                     Map.Entry<Integer, Integer> currLoc) {
    Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> adjList_main =
            dungeonGraph.getLocAdjacencyList();
    Map<Map.Entry<Integer, Integer>,Integer> distance = new HashMap<>();


    for (var node : adjList_main.entrySet()) {
      distance.put(node.getKey() , Integer.MAX_VALUE);
    }

    Set<Map.Entry<Integer, Integer>> visited_vertices = new HashSet<>();
    Queue<Map.Entry<Integer, Integer>> queue = new LinkedList<>();
    queue.add(currLoc);
    visited_vertices.add(currLoc);
    while (!queue.isEmpty()) {

      var vertex = queue.poll();
      if (vertex == currLoc) {
        distance.put(vertex,0);
      }

      for (var node : adjList_main.get(vertex)) {
        if (!(visited_vertices.contains(node))) {
          if (distance.get(node) > (distance.get(vertex) + 1)) {
            distance.put(node,(distance.get(vertex) + 1));
          }

          queue.add(node);
          visited_vertices.add(node);
        }
      }
    }

    Map<Map.Entry<Integer, Integer>,Integer> smellyDist = new HashMap<>();
    for (var dist : distance.entrySet()) {
      if (dist.getValue() <= 2) {
        smellyDist.put(dist.getKey(),dist.getValue());
      }
    }

    int smell = 0;
    for (var node: smellyDist.entrySet()) {
      for (var monst: monsterLoc.entrySet()) {
        if ((monst.getKey().getKey().equals(node.getKey().getKey()))
                && (monst.getKey().getValue().equals(node.getKey().getValue()))
                && monst.getValue() != null) {
          if (node.getValue() == 1  || node.getValue() == 0) {
            smell += 2;
          }
          else if (node.getValue() == 2) {
            smell += 1;
          }
        }
      }
    }
    if (smell == 1) {
      return "Low Smell";
    }
    else if (smell >= 2) {
      return "High Smell";
    }
    else {
      return "No Smell";
    }



  }



  @Override
  public StringBuilder pickUpTreasure(String opt) {
    return treasurePicker(opt);
  }

  private TreeMap<TreasureEnum, Integer> decrementTreasureHelper(String treasure) {
    Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> treasureLoc
            = dungeonGraph.getTreasureList();
    int diamondCount = 0;
    int rubyCount = 0;
    int sapphireCount = 0;
    switch (treasure) {
      case "DIAMOND": diamondCount++;
        break;
      case "RUBY": rubyCount++;
        break;
      case "SAPPHIRE" : sapphireCount++;
        break;
      case "BLANK" : diamondCount = treasureLoc.get(player.getCurrentLocation()).get(DIAMOND);
        rubyCount = treasureLoc.get(player.getCurrentLocation()).get(RUBY);
        sapphireCount = treasureLoc.get(player.getCurrentLocation()).get(SAPPHIRE);
        break;
      default:
        break;
    }

    TreeMap<TreasureEnum, Integer> decrementTreasure = new TreeMap<>();
    decrementTreasure.put(DIAMOND,
            treasureLoc.get(player.getCurrentLocation()).get(DIAMOND) - diamondCount);
    decrementTreasure.put(RUBY,
            treasureLoc.get(player.getCurrentLocation()).get(RUBY) - rubyCount);
    decrementTreasure.put(SAPPHIRE,
            treasureLoc.get(player.getCurrentLocation()).get(SAPPHIRE) - sapphireCount);

    return decrementTreasure;
  }

  private TreeMap<ArrowEnum, Integer> decrementArrowHelper(String arrow) {
    Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> arrowLoc
            = dungeonGraph.getArrowList();
    int arrowCount = 0;

    switch (arrow) {
      case "CROOKED_ARROW": arrowCount++;
        break;

      case "BLANK" : arrowCount =
              arrowLoc.get(player.getCurrentLocation()).get(ArrowEnum.CROOKED_ARROW);
        break;
      default:
        break;
    }

    TreeMap<ArrowEnum, Integer> decrementArrow = new TreeMap<>();
    decrementArrow.put(ArrowEnum.CROOKED_ARROW,
            arrowLoc.get(player.getCurrentLocation()).get(ArrowEnum.CROOKED_ARROW) - arrowCount);


    return decrementArrow;
  }

  private StringBuilder treasurePicker(String opt) {

    StringBuilder pickupMsg = new StringBuilder();
    Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> treasureLoc
            = dungeonGraph.getTreasureList();

    Map<Map.Entry<Integer, Integer>, TreeMap<ArrowEnum, Integer>> arrowLoc
            = dungeonGraph.getArrowList();

    if (!opt.equals("ALL")) {
      if (!opt.equals("CROOKED_ARROW")) {
        if (treasureLoc.get(player.getCurrentLocation()).get(TreasureEnum.valueOf(opt)) <= 0) {
          throw new IllegalArgumentException(
                  opt + " not available in present location");
        }
      }
      else {
        if (arrowLoc.get(player.getCurrentLocation()).get(ArrowEnum.valueOf(opt)) <= 0) {
          throw new IllegalArgumentException( opt + "not available in present location");
        }
      }
    }
    else  {
      if ((treasureLoc.get(player.getCurrentLocation()).get(DIAMOND) <= 0)
              && (treasureLoc.get(player.getCurrentLocation()).get(RUBY) <= 0)
              && (treasureLoc.get(player.getCurrentLocation()).get(SAPPHIRE) <= 0)
              && (arrowLoc.get(player.getCurrentLocation()).get(ArrowEnum.CROOKED_ARROW) <= 0)) {
        throw new IllegalArgumentException("There is nothing in the present location");
      }
    }
    if (opt.equals("ALL")) {
      if (treasureLoc.get(player.getCurrentLocation()).get(DIAMOND) > 0) {
        player.increaseTreasure("DIAMOND");
      }
      if (treasureLoc.get(player.getCurrentLocation()).get(RUBY) > 0) {
        player.increaseTreasure("RUBY");
      }
      if (treasureLoc.get(player.getCurrentLocation()).get(SAPPHIRE) > 0) {
        player.increaseTreasure("SAPPHIRE");
      }
      if (arrowLoc.get(player.getCurrentLocation()).get(ArrowEnum.CROOKED_ARROW) > 0) {
        player.increaseArrow("CROOKED_ARROW");
      }


      treasureLoc.replace(player.getCurrentLocation(), decrementTreasureHelper("BLANK"));
      arrowLoc.replace(player.getCurrentLocation(), decrementArrowHelper("BLANK"));
    }
    else if (opt.equals("CROOKED_ARROW")) {
      player.increaseArrow("CROOKED_ARROW");
      arrowLoc.replace(player.getCurrentLocation(), decrementArrowHelper(opt));
    }
    else {
      player.increaseTreasure(opt);
      treasureLoc.replace(player.getCurrentLocation(), decrementTreasureHelper(opt));
    }

    return pickupMsg.append("You picked up " + opt + "\n");


  }


  private void startCalc(boolean actual) throws IllegalArgumentException {

    List<Map.Entry<Integer, Integer>> start = new ArrayList<>();
    TreeMap<TreasureEnum, Integer> blankTreasure = new TreeMap<>();
    blankTreasure.put(TreasureEnum.DIAMOND, 0);
    blankTreasure.put(TreasureEnum.RUBY, 0);
    blankTreasure.put(SAPPHIRE, 0);
    Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> adjList_main =
            dungeonGraph.getLocAdjacencyList();
    for (var node: adjList_main.entrySet()) {
      if (!dungeonGraph.isTunnel(node.getKey())) {
        start.add(node.getKey());
      }

    }
    if (actual) {
      startingPoint = start.get(actualRandom.nextInt(start.size()));
    }
    else {
      startingPoint = start.get(mockRandom.nextInt(start.size()));
    }
    Map<Map.Entry<Integer, Integer>, TreeMap<TreasureEnum, Integer>> treasureLoc
            = dungeonGraph.getTreasureList();
    treasureLoc.replace(startingPoint, blankTreasure);

  }

  @Override
  public StringBuilder enterPlayer() {

    StringBuilder moveMessage = new StringBuilder();
    player.setCurrentLocation(startingPoint);

    for(var node :dungeonGraph.getIsVisitedList().entrySet()) {
      if(node.getKey().getKey().equals(startingPoint.getKey()) && node.getKey().getValue().equals(startingPoint.getValue())) {
        dungeonGraph.getIsVisitedList().replace(node.getKey(),true);
      }
    }

    String smellLev = calculateSmellLevel(dungeonGraph.getMonsterList(),startingPoint);

    if (smellLev.equals("No Smell")) {
      moveMessage.append("\n");
    }
    if (smellLev.equals("High Smell")) {
      moveMessage.append("Something Smells TERRIBLE !!\n");
    }
    if (smellLev.equals("Low Smell")) {
      moveMessage.append("Something Smells !!\n");
    }
    return moveMessage;


  }

  /**
   * specify the goal location of the maze.
   *
   * @throws IllegalArgumentException if goal point is same as starting point or point is invalid
   */

  private void goalCalc(boolean actual) throws IllegalArgumentException {

    goalPoint = findGoal(actual);


  }

  private Map.Entry<Integer, Integer> findGoal(boolean actual) {
    Map<Map.Entry<Integer, Integer>, List<Map.Entry<Integer, Integer>>> adjList_main =
            dungeonGraph.getLocAdjacencyList();
    Map<Map.Entry<Integer, Integer>,Integer> distance = new HashMap<>();


    for (var node : adjList_main.entrySet()) {
      distance.put(node.getKey() , Integer.MAX_VALUE);
    }

    Set<Map.Entry<Integer, Integer>> visited_vertices = new HashSet<>();
    Queue<Map.Entry<Integer, Integer>> queue = new LinkedList<>();
    queue.add(this.getStartingPoint());
    visited_vertices.add(this.getStartingPoint());
    while (!queue.isEmpty()) {

      var vertex = queue.poll();
      if (vertex == this.getStartingPoint()) {
        distance.put(vertex,0);
      }

      for (var node : adjList_main.get(vertex)) {
        if (!(visited_vertices.contains(node))) {
          if (distance.get(node) > (distance.get(vertex) + 1)) {
            distance.put(node,(distance.get(vertex) + 1));
          }

          queue.add(node);
          visited_vertices.add(node);
        }

      }
    }
    List<Map.Entry<Integer, Integer>> goal1 = new ArrayList<>();
    for (var dist : distance.entrySet()) {

      if ((dist.getValue() >= 5) && (!dungeonGraph.isTunnel(dist.getKey()))) {
        goal1.add(dist.getKey());
      }

    }
    if (goal1.size() == 0) {
      throw new IllegalArgumentException("Unable to generate goal point of min distance 5 "
              + "from starting point for given graph parameters");
    }
    Map.Entry<Integer, Integer> goalPoint1;
    if (actual) {
      goalPoint1 = goal1.get(actualRandom.nextInt(goal1.size()));
    }
    else {
      goalPoint1 = goal1.get(mockRandom.nextInt(goal1.size()));
    }
    return goalPoint1;
  }

  @Override
  public String shootArrow(DirectionEnum direction, int distance) throws IllegalArgumentException {

    if (direction == null) {
      throw new IllegalArgumentException("Direction can't be null");
    }

    if (distance <= 0 || player.getArrowCount() == 0) {
      throw new IllegalArgumentException("Your ability to shoot is not present !");
    }
    if (!getPossibleMoves(player.getCurrentLocation()).contains(direction)) {
      player.decreaseArrowCount();
      return "YOU MISSED!!!\n";
    }
    return shootArrowHelper(direction, distance, player.getCurrentLocation());

  }


  private String shootArrowHelper(DirectionEnum direction, int distance,
                                  Map.Entry<Integer, Integer> arrowCurrLocation) {

    if (!dungeonGraph.getPossibleMoves(arrowCurrLocation).contains(direction) && distance != 0) {
      player.decreaseArrowCount();
      return "YOU MISSED!!!\n";
    }


    if (distance == 0 || !dungeonGraph.getPossibleMoves(arrowCurrLocation).contains(direction)) {
      player.decreaseArrowCount();
      for (var monsterNode : dungeonGraph.getMonsterList().entrySet()) {
        if (monsterNode.getKey().getKey().equals(arrowCurrLocation.getKey())
                && (monsterNode.getKey().getValue().equals(arrowCurrLocation.getValue())
                && monsterNode.getValue() != null)) {
          monsterNode.getValue().setMonsterHealth(monsterNode.getValue().getMonsterHealth() - 50);
          if (monsterNode.getValue().getMonsterHealth() == 0) {
            dungeonGraph.getMonsterList().replace(monsterNode.getKey(),null);
            return "you killed otyugh\n";
          }
          else {
            return "you hit otyugh\n";
          }
        }
      }
      return "YOU MISSED!!!\n";
    }

    Map.Entry<Integer, Integer> newLocation = makeArrowMoveHelper(arrowCurrLocation, direction);
    makeComplementDictionary();
    if (dungeonGraph.getPossibleMoves(newLocation).size() == 2) {
      EnumSet<DirectionEnum> moves =  dungeonGraph.getPossibleMoves(newLocation);
      for (DirectionEnum move : moves) {
        if (!move.equals(directionComplement.get(direction))) {
          return shootArrowHelper(move, distance, newLocation);
        }
      }
    }
    else {
      distance--;
      return shootArrowHelper(direction, distance, newLocation);
    }
    return "";
  }

  private static final Map<DirectionEnum, DirectionEnum> directionComplement = new HashMap<>();

  private static void makeComplementDictionary() {

    directionComplement.put(DirectionEnum.NORTH, DirectionEnum.SOUTH);
    directionComplement.put(DirectionEnum.SOUTH, DirectionEnum.NORTH);
    directionComplement.put(DirectionEnum.EAST, DirectionEnum.WEST);
    directionComplement.put(DirectionEnum.WEST, DirectionEnum.EAST);

  }

  /**
   * Returns the supposed new location of arrow if we assumed that there are only caves, no tunnels.
   * */
  private Map.Entry<Integer,Integer> makeArrowMoveHelper(Map.Entry<Integer,Integer> currentLocation,
                                                         DirectionEnum direction) {
    Map.Entry<Integer, Integer> newLocation;

    switch (direction) {

      case EAST:
        newLocation = new AbstractMap.SimpleEntry<>(currentLocation.getKey(),
                (currentLocation.getValue() + 1 + numberOfColumns) % numberOfColumns);
        break;
      case WEST:
        newLocation = new AbstractMap.SimpleEntry<>(currentLocation.getKey(),
                (currentLocation.getValue() - 1 + numberOfColumns) % numberOfColumns);
        break;
      case NORTH:
        newLocation = new AbstractMap.SimpleEntry<>((currentLocation.getKey() - 1 + numberOfRows)
                % numberOfRows, currentLocation.getValue());
        break;
      case SOUTH:
        newLocation = new AbstractMap.SimpleEntry<>((currentLocation.getKey() + 1 + numberOfRows)
                % numberOfRows, currentLocation.getValue());
        break;
      default: throw new IllegalArgumentException("Invalid action.");
    }
    return newLocation;
  }




  @Override
  public StringBuilder nodeInfo() {
    StringBuilder maze = new StringBuilder();
    maze.append("Current location : \n");
    for (var loc: getTreasureList().keySet()) {
      maze.append(loc.getKey());
      maze.append(",");
      maze.append(loc.getValue());
      maze.append(" contains : ");
      maze.append(getTreasureList().get(loc));
      maze.append(getArrowList().get(loc));
      maze.append("\n");
    }
    return maze;
  }

  @ Override
  public String toString() {
    return "AbstractMaze{"
            + "numberOfRows=" + numberOfRows
            + ", numberOfColumns=" + numberOfColumns
            + ", isWrapped=" + isWrapped
            + ", interconnectivity=" + interconnectivity
            + ", mazeGraph=" + dungeonGraph.getLocAdjacencyList().toString()
            + ", startingPoint=" + startingPoint + ", goalPoint=" + goalPoint + '}';
  }


  @Override
  public StringBuilder printDungeon() {
    StringBuilder s = new StringBuilder();
    var adj_list = dungeonGraph.getLocAdjacencyList();
    for (var node1 : adj_list.entrySet()) {
      s.append("\n");

      for (var connections : node1.getValue()) {
        s.append(node1.getKey().toString() + "------");
        s.append(connections.toString() + "\t");
      }

    }

    return s;

  }

  @Override
  public Map<Map.Entry<Integer, Integer>, MonsterInterface> getMonsterList() {
    return dungeonGraph.getMonsterList();
  }

  @Override
  public Map<Map.Entry<Integer, Integer>, Boolean> getVisitedList() {
    return dungeonGraph.getIsVisitedList();
  }

  @Override
  public int getNumberOfMonsters() {
    return noOfMonster;
  }


}
