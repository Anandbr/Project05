import static org.junit.Assert.assertEquals;

import model.dungeon.DungeonInterface;
import model.dungeon.PlayerInterface;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import model.kruskal.DirectionEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Map;

/**
 * Class to test Player.
 *
 */

public class PlayerInterfaceTest {

  DungeonInterface unwrappedDungeon;
  DungeonInterface wrappedDungeon;
  DungeonInterface smallWrappedDungeon;
  DungeonInterface smallUnwrapDungeon;
  PlayerInterface player1;
  PlayerInterface player2;
  PlayerInterface player3;
  PlayerInterface player4;

  Map.Entry<Integer, Integer> loc;

  @Before
  public void setup() {
    unwrappedDungeon = new UnWrappedDungeon(5, 5, 3, 50,2, false);
    wrappedDungeon = new WrappedDungeon(5,5, 2, 55,2, false);
    smallWrappedDungeon = new WrappedDungeon(2, 4, 0, 100,2, false);
    smallUnwrapDungeon = new UnWrappedDungeon(2, 4, 0, 100, 2,false);

    smallWrappedDungeon.enterPlayer();
    unwrappedDungeon.enterPlayer();
    wrappedDungeon.enterPlayer();
    smallUnwrapDungeon.enterPlayer();

    player1 = unwrappedDungeon.getPlayer();
    player2 = smallUnwrapDungeon.getPlayer();
    player3 = wrappedDungeon.getPlayer();
    player4 = smallWrappedDungeon.getPlayer();

  }

  @Test
  public void testArrowCountBegin() {
    assertEquals(3, smallWrappedDungeon.getPlayer().getArrowCount());
  }

  @Test
  public void getCurrentLocation() {
    assertEquals("3.3", player1.getCurrentLocation().getKey()
        + "." + player1.getCurrentLocation().getValue());
    assertEquals("1.2", player4.getCurrentLocation().getKey()
        + "." + player4.getCurrentLocation().getValue());
  }


  @Test
  public void getDiamondCount() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("DIAMOND");
    assertEquals(player4.getDiamondCount(), 1, 0);
  }


  @Test
  public void getSapphireCount() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("SAPPHIRE");
    assertEquals(player4.getSapphireCount(), 1, 0);
  }

  @Test
  public void getRubyCount() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    smallWrappedDungeon.makeMove(DirectionEnum.NORTH);
    smallWrappedDungeon.pickUpTreasure("RUBY");
    assertEquals(player4.getRubyCount(), 1, 0);
  }

  @Test
  public void getRubyCountZero() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals(player4.getRubyCount(), 0, 0);
  }

  @Test
  public void getSapphireCountZero() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals(player4.getSapphireCount(), 0, 0);
  }

  @Test
  public void getDiamondCountZero() {
    smallWrappedDungeon.makeMove(DirectionEnum.WEST);
    assertEquals(player4.getDiamondCount(), 0, 0);
  }

  @Test
  public void setValidCurrentLocation() {
    loc = new AbstractMap.SimpleEntry<>(1,2);
    player1.setCurrentLocation(loc);
    assertEquals("1.2", player1.getCurrentLocation().getKey() + "."
        + player1.getCurrentLocation().getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setInValidCurrentLocation() {
    loc = new AbstractMap.SimpleEntry<>(-1, 3);
    player1.setCurrentLocation(loc);
    assertEquals("1.2", player1.getCurrentLocation().getKey() + "."
        + player1.getCurrentLocation().getValue());
  }

  @Test
  public void increaseTreasureCount() {
    player1.increaseTreasure("DIAMOND");
    assertEquals(1, player1.getDiamondCount(), 0);
  }

}