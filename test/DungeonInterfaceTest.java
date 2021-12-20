import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import model.dungeon.DungeonInterface;
import model.dungeon.PlayerInterface;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import model.kruskal.DirectionEnum;
import model.kruskal.TreasureEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

/**
 * Class to test Dungeon.
 *
 */

public class DungeonInterfaceTest {

  DungeonInterface unwrappedDungeon;
  DungeonInterface wrappedDungeon;
  DungeonInterface smallWrappedDungeon;
  DungeonInterface smallUnwrapDungeon;

  @Before
  public void setup() {
    unwrappedDungeon = new UnWrappedDungeon(5, 5, 3, 50, 2, false);
    wrappedDungeon = new WrappedDungeon(5,5, 2, 55,2, false);
    smallWrappedDungeon = new WrappedDungeon(2, 4, 0, 100,2, false);
    smallUnwrapDungeon = new UnWrappedDungeon(2, 4, 0, 100,2, false);

    smallWrappedDungeon.enterPlayer();
    unwrappedDungeon.enterPlayer();
    wrappedDungeon.enterPlayer();
    smallUnwrapDungeon.enterPlayer();

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    new UnWrappedDungeon(5,5,-1,50,1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRowInputUnwrapped() {
    new UnWrappedDungeon(0,4, 0, 50, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRowInputWrapped() {
    new WrappedDungeon(0,4,0,50,1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidColInputUnwrapped() {
    new UnWrappedDungeon(2,0, 0,50,1,false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidColInputWrapped() {
    new WrappedDungeon(2,0, 0,50,1,false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercentUnwrap() {
    new UnWrappedDungeon(2,4,0,1000, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasurePercentWrap() {
    new UnWrappedDungeon(2,4,0,-1, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroMonsterInputUnwrap() {
    new UnWrappedDungeon(2, 4, 0, 100, 0, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterInputWrap() {
    new WrappedDungeon(2, 4, 0, 100, 8, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMonsterInputUnwrap() {
    new UnWrappedDungeon(2, 4, 0, 100, 8, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroMonsterInputWrap() {
    new WrappedDungeon(2, 4, 0, 100, 0, false);
  }

  @Test
  public void getNumberOfRowsUnwrap() {
    assertEquals(5,unwrappedDungeon.getNumberOfRows());
  }

  @Test
  public void getNumberOfRowWrap() {
    assertEquals(5,wrappedDungeon.getNumberOfRows());
  }

  @Test
  public void getNumberOfColumnsUnwrap() {
    assertEquals(5,unwrappedDungeon.getNumberOfColumns());
  }

  @Test
  public void getNumberOfColumnsWrap() {
    assertEquals(5,wrappedDungeon.getNumberOfColumns());
  }

  @Test
  public void isWrapped() {
    assertTrue(wrappedDungeon.isWrapped());
  }

  @Test
  public void getInterconnectivityWrap() {
    assertEquals(2, wrappedDungeon.getInterconnectivity());
  }

  @Test
  public void getInterconnectivityUnWrap() {
    assertEquals(3, unwrappedDungeon.getInterconnectivity());
  }

  @Test
  public void getPossibleMovesWrapped() {
    assertEquals("[NORTH]",
        wrappedDungeon.getPossibleMoves(wrappedDungeon.getStartingPoint()).toString());
    assertEquals("[WEST]",
        smallWrappedDungeon.getPossibleMoves(smallWrappedDungeon.getStartingPoint()).toString());

  }

  @Test
  public void getPossibleMovesUnWrapped() {
    assertEquals("[NORTH, SOUTH, EAST, WEST]",
        unwrappedDungeon.getPossibleMoves(unwrappedDungeon.getStartingPoint()).toString());
    assertEquals("[NORTH]",
        smallUnwrapDungeon.getPossibleMoves(smallUnwrapDungeon.getStartingPoint()).toString());
  }

  @Test
  public void getPlayer() {
    assertEquals("Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0. "
        + "At Location 4.2", wrappedDungeon.getPlayer().toString());
  }

  @Test
  public void getTreasureListWrap() {
    assertEquals("{0=0={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 1=1={DIAMOND=0, SAPPHIRE=0, RUBY=0}," +
            " 0=1={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 1=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}," +
            " 0=2={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 1=3={DIAMOND=0, SAPPHIRE=0, RUBY=0}, " +
            "0=3={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 1=2={DIAMOND=0, SAPPHIRE=0, RUBY=0}}",
        smallWrappedDungeon.getTreasureList().toString());
  }

  @Test
  public void getTreasureListUnWrap() {
    assertEquals("{0=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 1=1={DIAMOND=0, SAPPHIRE=0, RUBY=0}," +
            " 0=1={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 1=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, " +
            "0=2={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 1=3={DIAMOND=0, SAPPHIRE=0, RUBY=0}, " +
            "0=3={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 1=2={DIAMOND=1, SAPPHIRE=1, RUBY=1}}",
        smallUnwrapDungeon.getTreasureList().toString());
  }

  @Test
  public void getArrowListWrap() {
    assertEquals("{0=0={CROOKED_ARROW=1}, 1=1={CROOKED_ARROW=1}, "
            + "0=1={CROOKED_ARROW=1}, 1=0={CROOKED_ARROW=1}, 0=2={CROOKED_ARROW=1}, "
            + "1=3={CROOKED_ARROW=0}, 0=3={CROOKED_ARROW=1}, 1=2={CROOKED_ARROW=0}}",
        smallWrappedDungeon.getArrowList().toString());
  }

  @Test
  public void getArrowListUnWrap() {
    assertEquals("{0=0={CROOKED_ARROW=1}, 1=1={CROOKED_ARROW=1}, "
            + "0=1={CROOKED_ARROW=1}, 1=0={CROOKED_ARROW=0}, 0=2={CROOKED_ARROW=1}, "
            + "1=3={CROOKED_ARROW=0}, 0=3={CROOKED_ARROW=1}, 1=2={CROOKED_ARROW=1}}",
        smallUnwrapDungeon.getArrowList().toString());
  }

  @Test
  public void getStartingPointWrapped() {
    assertEquals("4.2",
        wrappedDungeon.getStartingPoint().getKey()
            + "." + wrappedDungeon.getStartingPoint().getValue());
  }

  @Test
  public void testArrowCurves() {
    int startRow = smallUnwrapDungeon.getStartingPoint().getKey();
    int startCol = smallUnwrapDungeon.getStartingPoint().getValue();
    assertEquals("you hit otyugh\n",
        smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 2));
    assertEquals(50,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
    int otyughRow = smallUnwrapDungeon.getGoalPoint().getKey();
    int otyughCol = smallUnwrapDungeon.getGoalPoint().getValue();

    assertNotEquals(otyughCol, startCol);
  }

  @Test
  public void testShootDistCountsOnlyCaves() {
    Map.Entry<Integer, Integer> loc;
    loc = new SimpleEntry<>(1, 0);
    Map.Entry<Integer,Integer> start = smallUnwrapDungeon.getStartingPoint();

    var adjacencyL = smallUnwrapDungeon.getAdjacencyList();
    Map<Map.Entry<Integer, Integer>,Integer> distance = new HashMap<>();

    for (var node : adjacencyL.entrySet()) {
      distance.put(node.getKey() , Integer.MAX_VALUE);
    }

    Set<Entry<Integer, Integer>> visited_vertices = new HashSet<>();
    Queue<Entry<Integer, Integer>> queue = new LinkedList<>();
    queue.add(start);
    visited_vertices.add(start);
    while (!queue.isEmpty()) {

      var vertex = queue.poll();
      if (vertex == start) {
        distance.put(vertex,0);
      }

      for (var node : adjacencyL.get(vertex)) {
        if (!(visited_vertices.contains(node))) {
          if (distance.get(node) > (distance.get(vertex) + 1)) {
            distance.put(node,(distance.get(vertex) + 1));
          }

          queue.add(node);
          visited_vertices.add(node);
        }

      }
    }

    int distBetweenGS = distance.get(loc);
    assertEquals("you hit otyugh\n", smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 2));
    assertEquals(50, smallUnwrapDungeon.getMonsterList().get(loc).getMonsterHealth());
    assertNotEquals(distBetweenGS, 2);
  }

  @Test
  public void testOtyughNotAtTunnel() {
    int count = 0;
    for (var nodes: smallUnwrapDungeon.getAdjacencyList().entrySet()) {
      if (nodes.getValue().size() == 2) {
        if (smallUnwrapDungeon.getMonsterList().get(nodes) != null) {
          count++;
        }
      }
    }
    assertEquals(0, count);
  }

  @Test
  public void testShootAndHitOtyugh() {
    assertEquals(100,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
    assertEquals("you hit otyugh\n" ,
        smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 2));
    assertEquals(50,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
  }

  @Test
  public void testShootAndKillOtyugh() {
    assertEquals(100,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
    assertEquals("you hit otyugh\n",
        smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 2));
    assertEquals("you killed otyugh\n",
        smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 2));
    assertNull(smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint()));
  }

  @Test
  public void testShootAndMissDistMore() {
    assertEquals(100,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
    assertEquals("YOU MISSED!!!\n",
        smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 3));
    assertEquals(100,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
  }

  @Test
  public void testShootAndMissWrongDirection() {
    assertEquals("YOU MISSED!!!\n",
        smallUnwrapDungeon.shootArrow(DirectionEnum.SOUTH, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootToInvalidDirection() {
    smallUnwrapDungeon.shootArrow(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootWithZeroArrows() {
    int count = smallUnwrapDungeon.getPlayer().getArrowCount();
    while (count != 0) {
      smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 1);
      count--;
    }
    smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShootInvalidDist() {
    smallUnwrapDungeon.shootArrow(DirectionEnum.NORTH, -1);
  }

  @Test
  public void testStartingPointNotTunnelUnwrap() {
    List<Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    for (var adjList : smallUnwrapDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }
    assertFalse(tunnelList.contains(smallUnwrapDungeon.getStartingPoint()));
  }

  @Test
  public void testStartingPointNotTunnelWrap() {
    List<Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    for (var adjList : smallWrappedDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }

    assertFalse(tunnelList.contains(smallWrappedDungeon.getStartingPoint()));
  }

  @Test
  public void testGoalPointNotTunnelUnwrap() {
    List<Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    for (var adjList : smallUnwrapDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }

    assertFalse(tunnelList.contains(smallUnwrapDungeon.getGoalPoint()));
  }

  @Test
  public void testGoalPointNotTunnelWrap() {
    List<Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    for (var adjList : smallWrappedDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }
    assertFalse(tunnelList.contains(smallWrappedDungeon.getGoalPoint()));
  }

  @Test
  public void testOtyughAtStartUnwrap() {
    assertNull(unwrappedDungeon.getMonsterList().get(unwrappedDungeon.getStartingPoint()));
  }

  @Test
  public void testOtyughAtStartWrap() {
    assertNull(wrappedDungeon.getMonsterList().get(wrappedDungeon.getStartingPoint()));
  }

  @Test
  public void testOtyughAtGoalUnwrap() {
    assertNotNull(unwrappedDungeon.getMonsterList().get(unwrappedDungeon.getGoalPoint()));
  }

  @Test
  public void testOtyughAtGoalWrap() {
    assertNotNull(wrappedDungeon.getMonsterList().get(wrappedDungeon.getGoalPoint()));
  }

  @Test
  public void getStartingPointUnWrappedSmall() {
    assertEquals("1.3",
        smallUnwrapDungeon.getStartingPoint().getKey()
            + "." + smallUnwrapDungeon.getStartingPoint().getValue());
  }

  @Test
  public void getStartingPointUnWrapped() {
    assertEquals("3.3",
        unwrappedDungeon.getStartingPoint().getKey()
            + "." + unwrappedDungeon.getStartingPoint().getValue());
  }

  @Test
  public void getStartingPointWrappedSmall() {
    assertEquals("1.2",
        smallWrappedDungeon.getStartingPoint().getKey()
            + "." + smallWrappedDungeon.getStartingPoint().getValue());
  }


  @Test
  public void getGoalPointWrapped() {
    assertEquals("1.4", wrappedDungeon.getGoalPoint().getKey()
        + "." + wrappedDungeon.getGoalPoint().getValue());
  }

  @Test
  public void getGoalPointUnWrapped() {
    assertEquals("1.0", unwrappedDungeon.getGoalPoint().getKey()
        + "." + unwrappedDungeon.getGoalPoint().getValue());
  }

  @Test
  public void testTreasureAtOtyughLoc() {
    Map.Entry<Integer, Integer> loc;
    loc = new AbstractMap.SimpleEntry<>(1, 2);

    assertEquals("1",
        smallUnwrapDungeon.getTreasureList().get(loc).get(TreasureEnum.DIAMOND).toString());
    assertNotNull(smallUnwrapDungeon.getMonsterList().get(loc));
  }

  @Test
  public void getGoalPointWrappedSmall() {
    assertEquals("1.3", smallWrappedDungeon.getGoalPoint().getKey()
        + "." + smallWrappedDungeon.getGoalPoint().getValue());
  }


  @Test
  public void makeMoveAllDirection() {
    unwrappedDungeon.makeMove(DirectionEnum.NORTH);
    unwrappedDungeon.makeMove(DirectionEnum.SOUTH);
    unwrappedDungeon.makeMove(DirectionEnum.EAST);
    unwrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("3.3", unwrappedDungeon.getPlayer().getCurrentLocation().getKey()
        + "." + unwrappedDungeon.getPlayer().getCurrentLocation().getValue());
  }

  @Test
  public void makeMoveToDungeonWithHealthyOtyugh() {
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("Player Eaten by Otyugh ! Game Over !",
        smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH).toString());
  }

  @Test
  public void makeMoveToDungeonWithHitOtyugh() {
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.shootArrow(DirectionEnum.WEST, 1);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("Player Eaten by Otyugh ! Game Over !",
        smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH).toString());
  }

  @Test
  public void makeMoveToGoalAfterKillingOtyugh() {
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.shootArrow(DirectionEnum.WEST, 1);
    smallUnwrapDungeon.shootArrow(DirectionEnum.WEST, 1);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("Player Wins ! Game Over !",
        smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH).toString());
  }

  @Test
  public void makeMoveValidWrapped() {
    wrappedDungeon.makeMove(DirectionEnum.NORTH);
    assertEquals("3.2", wrappedDungeon.getPlayer().getCurrentLocation().getKey()
        + "." + wrappedDungeon.getPlayer().getCurrentLocation().getValue());
  }

  @Test
  public void makeMoveValidUnWrapped() {
    unwrappedDungeon.makeMove(DirectionEnum.NORTH);
    assertEquals("2.3", unwrappedDungeon.getPlayer().getCurrentLocation().getKey()
        + "." + unwrappedDungeon.getPlayer().getCurrentLocation().getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void makeMoveInValidWrapped() {
    wrappedDungeon.makeMove(DirectionEnum.SOUTH);
    wrappedDungeon.makeMove(DirectionEnum.EAST);
  }

  @Test(expected = IllegalArgumentException.class)
  public void makeMoveInValidUnWrapped() {
    unwrappedDungeon.makeMove(DirectionEnum.NORTH);
    unwrappedDungeon.makeMove(DirectionEnum.EAST);
    unwrappedDungeon.makeMove(DirectionEnum.SOUTH);
  }


  @Test
  public void pickupTreasureDiamondWrappedSmall() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("DIAMOND");
    assertEquals(smallWrappedDungeon.getPlayer().getDiamondCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getRubyCount(), 0, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getSapphireCount(), 0, 0);
  }

  @Test
  public void pickupTreasureSapphireWrappedSmall() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("SAPPHIRE");
    assertEquals(smallWrappedDungeon.getPlayer().getSapphireCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getDiamondCount(), 0, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getRubyCount(), 0, 0);
  }

  @Test
  public void pickupTreasureRubyWrappedSmall() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("RUBY");
    assertEquals(smallWrappedDungeon.getPlayer().getRubyCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getSapphireCount(), 0, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getDiamondCount(), 0, 0);
  }

  @Test
  public void pickupArrowUnwrapSmall() {
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    smallUnwrapDungeon.pickUpTreasure("CROOKED_ARROW");
    assertEquals(4, smallUnwrapDungeon.getPlayer().getArrowCount(), 0);
  }

  @Test
  public void pickupTreasureAllWrappedSmall() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("ALL");
    assertEquals(smallWrappedDungeon.getPlayer().getRubyCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getSapphireCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getDiamondCount(), 1, 0);
    assertEquals(smallWrappedDungeon.getPlayer().getArrowCount(), 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickupTreasureInvalid() {
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    smallWrappedDungeon.pickUpTreasure("ALL");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickupDiamondInvalid() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    smallWrappedDungeon.pickUpTreasure("DIAMOND");
    smallWrappedDungeon.pickUpTreasure("DIAMOND");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickupArrowInvalid() {
    smallUnwrapDungeon.pickUpTreasure("CROOKED_ARROW");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickupArrowInvalidEntry() {
    smallUnwrapDungeon.pickUpTreasure("ARROW");
  }


  @Test(expected = IllegalArgumentException.class)
  public void pickupRubyInvalid() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    smallWrappedDungeon.pickUpTreasure("RUBY");
    smallWrappedDungeon.pickUpTreasure("RUBY");
  }

  @Test(expected = IllegalArgumentException.class)
  public void pickupSapphireInvalid() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    smallWrappedDungeon.pickUpTreasure("SAPPHIRE");
    smallWrappedDungeon.pickUpTreasure("SAPPHIRE");
  }

  @Test
  public void treasureNotInTunnelUnWrapped() {
    List<Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    boolean check = true;
    for (var adjList : smallUnwrapDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }
    for (var treasure : smallUnwrapDungeon.getTreasureList().entrySet()) {
      if (tunnelList.contains(treasure.getKey())) {
        if ((treasure.getValue().get(TreasureEnum.DIAMOND) > 0)
            || (treasure.getValue().get(TreasureEnum.RUBY) > 0)
            || (treasure.getValue().get(TreasureEnum.SAPPHIRE) > 0)) {
          check = false;
          break;
        }
      }
    }
    assertTrue(check);
  }

  @Test
  public void treasureNotInTunnelWrapped() {
    List<Map.Entry<Integer, Integer>> tunnelList = new ArrayList<>();
    boolean check = true;
    for (var adjList : smallWrappedDungeon.getAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        tunnelList.add(adjList.getKey());
      }
    }
    for (var treasure : smallWrappedDungeon.getTreasureList().entrySet()) {
      if (tunnelList.contains(treasure.getKey())) {
        if ((treasure.getValue().get(TreasureEnum.DIAMOND) > 0)
            || (treasure.getValue().get(TreasureEnum.RUBY) > 0)
            || (treasure.getValue().get(TreasureEnum.SAPPHIRE) > 0)) {
          check = false;
          break;
        }
      }
    }
    assertTrue(check);
  }

  @Test
  public void enterPlayer() {
    PlayerInterface player1 = unwrappedDungeon.getPlayer();
    assertEquals(player1.getCurrentLocation().toString(),
        unwrappedDungeon.getStartingPoint().toString());
  }

  @Test
  public void testDistanceBetweenStartGoal() {
    Map.Entry<Integer,Integer> start = smallUnwrapDungeon.getStartingPoint();

    var adjacencyL
        = smallUnwrapDungeon.getAdjacencyList();
    Map<Map.Entry<Integer, Integer>,Integer> distance = new HashMap<>();

    for (var node : adjacencyL.entrySet()) {
      distance.put(node.getKey() , Integer.MAX_VALUE);
    }

    Set<Entry<Integer, Integer>> visited_vertices = new HashSet<>();
    Queue<Entry<Integer, Integer>> queue = new LinkedList<>();
    queue.add(start);
    visited_vertices.add(start);
    while (!queue.isEmpty()) {

      var vertex = queue.poll();
      if (vertex == start) {
        distance.put(vertex,0);
      }

      for (var node : adjacencyL.get(vertex)) {
        if (!(visited_vertices.contains(node))) {
          if (distance.get(node) > (distance.get(vertex) + 1)) {
            distance.put(node,(distance.get(vertex) + 1));
          }

          queue.add(node);
          visited_vertices.add(node);
        }

      }
    }

    Map.Entry<Integer,Integer> goal = smallUnwrapDungeon.getGoalPoint();
    int distBetweenGS = distance.get(goal);
    boolean dist = (distBetweenGS >= 5);
    assertTrue(dist);

  }

  @Test
  public void canReachGoalTest() {
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH);
    assertEquals(smallUnwrapDungeon.getPlayer().getCurrentLocation().toString(),
        smallUnwrapDungeon.getGoalPoint().toString());
  }


  @Test
  public void canReachAllCaveWrappedSmall() {
    assertEquals(8, (smallWrappedDungeon.getNumberOfColumns()
        * smallWrappedDungeon.getNumberOfRows()));
    assertEquals("1.2",
        smallWrappedDungeon.getPlayer().getCurrentLocation().getKey() + "."
            + smallWrappedDungeon.getPlayer().getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("1.1",
        smallWrappedDungeon.getPlayer().getCurrentLocation().getKey() + "."
            + smallWrappedDungeon.getPlayer().getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.SOUTH);
    PlayerInterface player4 = smallWrappedDungeon.getPlayer();
    assertEquals("0.1",
        smallWrappedDungeon.getPlayer().getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("0.0",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.SOUTH);
    assertEquals("1.0",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("1.3",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("1.0",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    assertEquals("0.0",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("0.3",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("0.0",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("0.1",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
    smallWrappedDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("0.2",
        player4.getCurrentLocation().getKey() + "."
            + player4.getCurrentLocation().getValue());
  }


  @Test
  public void canReachAllCaveUnWrappedSmall() {

    PlayerInterface player2 = smallUnwrapDungeon.getPlayer();

    assertEquals(8, (smallUnwrapDungeon.getNumberOfColumns()
        * smallUnwrapDungeon.getNumberOfRows()));
    assertEquals("1.3",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    assertEquals("0.3",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("0.2",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("0.1",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.WEST);
    assertEquals("0.0",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH);
    assertEquals("1.0",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.NORTH);
    assertEquals("0.0",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("0.1",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.SOUTH);
    assertEquals("1.1",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
    smallUnwrapDungeon.makeMove(DirectionEnum.EAST);
    assertEquals("1.2",
        player2.getCurrentLocation().getKey() + "."
            + player2.getCurrentLocation().getValue());
  }


  @Test
  public void assignMonsterUnwrappedCorrect() {
    int count = 0;
    for (var monst : unwrappedDungeon.getMonsterList().entrySet()) {
      if (monst.getValue() != null) {
        count++;
      }
    }
    assertEquals(count, unwrappedDungeon.getNumberOfMonsters());
  }

  @Test
  public void assignMonsterWrappedCorrect() {
    int count = 0;
    for (var monst : wrappedDungeon.getMonsterList().entrySet()) {
      if (monst.getValue() != null) {
        count++;
      }
    }
    assertEquals(count, wrappedDungeon.getNumberOfMonsters());
  }
}