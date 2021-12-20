import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.kruskal.ArrowEnum;
import model.kruskal.DungeonGraph;
import model.kruskal.TreasureEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;



/**
 * Class to test DungeonGraph.
 *
 */

public class GraphInterfaceTest {
  DungeonGraph locUnwrapped;
  DungeonGraph locWrapped;
  DungeonGraph locUnWrappedSmall;
  DungeonGraph locWrappedSmall;
  DungeonGraph newlocUnwrappedSmall;

  Map.Entry<Integer, Integer> startingPoint;
  Map.Entry<Integer, Integer> goalPoint;

  @Before
  public void setup() {
    locUnwrapped = new DungeonGraph(4,4, false);
    locWrapped = new DungeonGraph(4,4, true);
    locUnWrappedSmall = new DungeonGraph(3,3, false);
    locWrappedSmall = new DungeonGraph(2,3,true);
    newlocUnwrappedSmall = new DungeonGraph(1,5, false);

    startingPoint = Map.entry(0, 0);
    goalPoint = Map.entry(1, 1);
  }

  @Test
  public void assignTreasureUnWrappedCorrect() {
    int count = 0;
    int treasureCount = 0;
    int treasurePercent = 50;
    for (var adjList : locUnwrapped.getLocAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        count++;
      }
    }

    int cave = locUnwrapped.getLocAdjacencyList().size() - 2 - count;
    int treasureCave = (cave * treasurePercent) / 100;
    locUnwrapped.assignTreasure(treasurePercent, false, startingPoint,
        goalPoint);

    for (var treasureList : locUnwrapped.getTreasureList().entrySet()) {
      if (treasureList.getValue().get(TreasureEnum.DIAMOND) == 1) {
        treasureCount++;
      }
    }
    assertEquals( treasureCave, treasureCount);
  }

  @Test
  public void assignTreasureWrappedCorrect() {
    int count = 0;
    int treasureCount = 0;
    int treasurePercent = 50;
    for (var adjList : locWrapped.getLocAdjacencyList().entrySet()) {
      if (adjList.getValue().size() == 2) {
        count++;
      }
    }

    int cave = locWrapped.getLocAdjacencyList().size() - 2 - count;
    int treasureCave = (cave * treasurePercent) / 100;
    locWrapped.assignTreasure(treasurePercent, false, startingPoint,
        goalPoint);
    for (var treasureList : locWrapped.getTreasureList().entrySet()) {
      if (treasureList.getValue().get(TreasureEnum.DIAMOND) == 1) {
        treasureCount++;
      }
    }
    assertEquals(treasureCave, treasureCount);
  }

  @Test
  public void assignTreasureUnwrappedZero() {
    int treasureCave = 0;
    int treasureCount = 0;
    locUnwrapped.assignTreasure(0 , false, startingPoint,
        goalPoint);
    for (var treasureList : locUnwrapped.getTreasureList().entrySet()) {
      if (treasureList.getValue().get(TreasureEnum.DIAMOND) == 1) {
        treasureCount++;
      }
    }
    assertEquals(treasureCave, treasureCount);
  }

  @Test
  public void assignTreasureWrappedZero() {
    int treasureCave = 0;
    int treasureCount = 0;
    locWrapped.assignTreasure(0 , false, startingPoint,
        goalPoint);
    for (var treasureList : locWrapped.getTreasureList().entrySet()) {
      if (treasureList.getValue().get(TreasureEnum.DIAMOND) == 1) {
        treasureCount++;
      }
    }
    assertEquals(treasureCave, treasureCount);
  }


  //TODO HOW TO CHECK THERE IS EXACTLY 1 PATH BETWEEN 2 NODES WHEN INTER =0

  @Test(expected = IllegalArgumentException.class)
  public void assignTreasureUnWrappedNegative() {
    locUnwrapped.assignTreasure(-50, false, startingPoint,
        goalPoint);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assignTreasureWrappedNegative() {
    locWrapped.assignTreasure(-50, false, startingPoint,
        goalPoint);
  }

  @Test
  public void assignArrowUnWrappedCorrect() {
    int arrowCount = 0;
    int treasurePercent = 50;

    int nodes = locUnwrapped.getLocAdjacencyList().size() - 2;
    int arrowNodes = (nodes * treasurePercent) / 100;
    locUnwrapped.assignArrow(treasurePercent, false, startingPoint,
        goalPoint);

    for (var arrowList : locUnwrapped.getArrowList().entrySet()) {
      if (arrowList.getValue().get(ArrowEnum.CROOKED_ARROW) == 1) {
        arrowCount++;
      }
    }
    assertEquals( arrowNodes, arrowCount);
  }

  @Test
  public void assignArrowWrappedCorrect() {
    int arrowCount = 0;
    int treasurePercent = 50;

    int nodes = locWrapped.getLocAdjacencyList().size() - 2;
    int arrowNodes = (nodes * treasurePercent) / 100;
    locWrapped.assignArrow(treasurePercent, false, startingPoint,
        goalPoint);

    for (var arrowList : locWrapped.getArrowList().entrySet()) {
      if (arrowList.getValue().get(ArrowEnum.CROOKED_ARROW) == 1) {
        arrowCount++;
      }
    }
    assertEquals( arrowNodes, arrowCount);
  }

  @Test
  public void assignArrowUnwrappedZero() {
    int arrow = 0;
    int arrowCount = 0;
    locUnwrapped.assignArrow(0 , false, startingPoint,
        goalPoint);
    for (var arrowList : locUnwrapped.getArrowList().entrySet()) {
      if (arrowList.getValue().get(ArrowEnum.CROOKED_ARROW) == 1) {
        arrowCount++;
      }
    }
    assertEquals(arrow, arrowCount);
  }

  @Test
  public void assignArrowWrappedZero() {
    int arrow = 0;
    int arrowCount = 0;
    locWrapped.assignArrow(0 , false, startingPoint,
        goalPoint);
    for (var arrowList : locWrapped.getArrowList().entrySet()) {
      if (arrowList.getValue().get(ArrowEnum.CROOKED_ARROW) == 1) {
        arrowCount++;
      }
    }
    assertEquals(arrow, arrowCount);
  }


  //TODO HOW TO CHECK THERE IS EXACTLY 1 PATH BETWEEN 2 NODES WHEN INTER =0

  @Test(expected = IllegalArgumentException.class)
  public void assignArrowUnWrappedNegative() {
    locUnwrapped.assignArrow(-50, false, startingPoint,
        goalPoint);
  }

  @Test(expected = IllegalArgumentException.class)
  public void assignArrowWrappedNegative() {
    locWrapped.assignArrow(-50, false, startingPoint,
        goalPoint);
  }

  @Test
  public void getPossibleMoves() {
    Entry<Integer, Integer> loc = new AbstractMap.SimpleEntry<>(0, 0);
    assertEquals("[NORTH, SOUTH, EAST, WEST]",
        locWrapped.getPossibleMoves(loc).toString());
  }

  @Test
  public void getNumberOfNodesUnwrapped() {
    assertEquals(16, locUnwrapped.getNumberOfNodes());
  }

  @Test
  public void getNumberOfNodesWrapped() {
    assertEquals(16, locWrapped.getNumberOfNodes());
  }

  @Test
  public void getDungeonAdjacencyUnwrappedList() {
    assertEquals("{0=0=[1=0, 0=1], 1=1=[2=1, 1=2, 0=1, 1=0], "
            + "2=2=[1=2, 2=1], 0=1=[1=1, 0=2, 0=0], 1=0=[2=0, 1=1, 0=0], "
            + "0=2=[1=2, 0=1], 2=0=[2=1, 1=0], 1=2=[2=2, 0=2, 1=1], "
            + "2=1=[2=2, 1=1, 2=0]}",
        locUnWrappedSmall.getLocAdjacencyList().toString());
  }

  @Test
  public void getDungeonAdjacencyWrappedList() {
    assertEquals("{0=0=[1=0, 0=1, 1=0, 0=2], " +
        "1=1=[0=1, 1=2, 0=1, 1=0], 0=1=[1=1, 0=2, 1=1, 0=0], " +
        "1=0=[0=0, 1=1, 0=0, 1=2], 0=2=[1=2, 0=0, 1=2, 0=1], " +
        "1=2=[0=2, 1=0, 0=2, 1=1]}", locWrappedSmall.getLocAdjacencyList().toString());
  }

  @Test
  public void getTreasureUnwrappedList() {
    locUnWrappedSmall.assignTreasure(100, false, startingPoint,
        goalPoint);
    assertEquals("{0=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, "
            + "1=1={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 2=2={DIAMOND=0, SAPPHIRE=0, RUBY=0}, "
            + "0=1={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 1=0={DIAMOND=1, SAPPHIRE=1, RUBY=1}, "
            + "0=2={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 2=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, "
            + "1=2={DIAMOND=1, SAPPHIRE=1, RUBY=1}, 2=1={DIAMOND=1, SAPPHIRE=1, RUBY=1}}",
        locUnWrappedSmall.getTreasureList().toString());
  }

  @Test
  public void getTreasureWrappedList() {
    locWrappedSmall.assignTreasure(50, false, startingPoint,
        goalPoint);
    assertEquals("{0=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, "
            + "1=1={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 0=1={DIAMOND=1, SAPPHIRE=1, RUBY=1}, "
            + "1=0={DIAMOND=0, SAPPHIRE=0, RUBY=0}, 0=2={DIAMOND=0, SAPPHIRE=0, RUBY=0}, "
            + "1=2={DIAMOND=1, SAPPHIRE=1, RUBY=1}}",
        locWrappedSmall.getTreasureList().toString());
  }

  @Test
  public void testArrowAtTunnel() {
    Map.Entry<Integer, Integer> loc;
    Map.Entry<Integer, Integer> start;
    Map.Entry<Integer, Integer> goal;
    loc = new SimpleEntry<>(0,0);
    start = new AbstractMap.SimpleEntry<>(1, 2);
    goal = new AbstractMap.SimpleEntry<>(1, 0);
    assertTrue(locUnWrappedSmall.isTunnel(loc));
    locUnWrappedSmall.assignArrow(100, false, start, goal);
    assertEquals("1",
        locUnWrappedSmall.getArrowList().get(loc).get(ArrowEnum.CROOKED_ARROW).toString());
  }

  @Test
  public void getUnwrappedEdges() {
    assertEquals("[0=0=1=0, 0=0=0=1, 1=1=2=1, 1=1=1=2, 1=1=0=1, 1=1=1=0, "
            + "2=2=1=2, 2=2=2=1, 0=1=0=2, 1=0=2=0, 0=2=1=2, 2=0=2=1]",
        locUnWrappedSmall.getEdges().toString());
  }

  @Test
  public void getWrappedEdges() {
    assertEquals("[0=0=1=0, 0=0=0=1, 0=0=1=0, 0=0=0=2, 1=1=0=1, 1=1=1=2, 1=1=0=1, " +
            "1=1=1=0, 0=1=0=2, 1=0=1=2, 0=2=1=2, 0=2=1=2]",
        locWrappedSmall.getEdges().toString());
  }

  @Test
  public void testUnWrappedMakeMaze() {
    assertEquals("DungeonGraph{rows=3, columns=3, isWrapped=false, "
        + "adjList={0=0=[1=0, 0=1], 1=1=[2=1, 1=2, 0=1, 1=0], 2=2=[1=2, 2=1], "
        + "0=1=[1=1, 0=2, 0=0], 1=0=[2=0, 1=1, 0=0], 0=2=[1=2, 0=1], 2=0=[2=1, 1=0], "
        + "1=2=[2=2, 0=2, 1=1], 2=1=[2=2, 1=1, 2=0]}}", locUnWrappedSmall.toString());

  }

  @Test
  public void testWrappedMakeMaze() {
    assertEquals("DungeonGraph{rows=2, columns=3, isWrapped=true, " +
            "adjList={0=0=[1=0, 0=1, 1=0, 0=2], 1=1=[0=1, 1=2, 0=1, 1=0], " +
            "0=1=[1=1, 0=2, 1=1, 0=0], 1=0=[0=0, 1=1, 0=0, 1=2]," +
            " 0=2=[1=2, 0=0, 1=2, 0=1], " +
            "1=2=[0=2, 1=0, 0=2, 1=1]}}"
        , locWrappedSmall.toString());

  }

  @Test
  public void isTunnelTrue() {
    Entry<Integer, Integer> node = new AbstractMap.SimpleEntry<>(0,1);
    newlocUnwrappedSmall.assignTreasure(100, false, startingPoint,
        goalPoint);
    assertTrue(newlocUnwrappedSmall.isTunnel(node));
  }

  @Test
  public void isTunnelFalse() {
    Entry<Integer, Integer> node = new AbstractMap.SimpleEntry<>(0,0);
    newlocUnwrappedSmall.assignTreasure(100, false, startingPoint,
        goalPoint);
    assertFalse(newlocUnwrappedSmall.isTunnel(node));
  }

}
