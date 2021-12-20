import static org.junit.Assert.assertEquals;

import model.dungeon.DungeonInterface;
import model.dungeon.UnWrappedDungeon;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for monster interface.
 */
public class MonsterInterfaceTest {

  DungeonInterface smallUnwrapDungeon;

  @Before
  public void setup() {
    smallUnwrapDungeon = new UnWrappedDungeon(2, 4, 0,
        100,2, false);
  }

  @Test
  public void getMonsterId() {
    assertEquals(1,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint()).getMonsterId());
  }

  @Test
  public void getMonsterHealth() {
    assertEquals(100,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
  }

  @Test
  public void setMonsterHealth() {
    smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint()).setMonsterHealth(50);
    assertEquals(50,
        smallUnwrapDungeon.getMonsterList().get(smallUnwrapDungeon.getGoalPoint())
            .getMonsterHealth());
  }
}