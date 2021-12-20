import static org.junit.Assert.assertEquals;

import controller.GameConsoleController;
import controller.GameControllerGui;
import controller.GameControllerI;
import model.dungeon.DungeonInterface;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import org.junit.Test;
import view.IView;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Test for controller interface.
 */
public class GameControllerITest {

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {

    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,90,2,false);
    StringReader input = new StringReader("N W W W");
    Appendable gameLog = new FailingAppendable();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();

  }

  @Test
  public void testViewController() {
    List<String> log = new ArrayList<>();
    IView v = new MockViewTest(log);
    DungeonInterface m = new UnWrappedDungeon(2,4,0,100,2,false);
    GameControllerI c = new GameControllerGui(m,v);
    c.playGame();
    assertEquals(Arrays.asList("set listner was called was called","generateGameView was called",
        "generateGameView was called", "generateGameView was called", "Distance input was " +
            "called"), log);
  }

  @Test
  public void testPlayerWinsbyShootingatGoal() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W S 1 W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());


  }

  @Test
  public void testPlayerWinsbyShootingAllMonster() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 S P ARROW S 1 S S 1 W S 1 W M W M "
        + "S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?You picked up CROOKED_ARROW"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());


  }

  @Test
  public void testPlayerLoses() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }


  @Test
  public void testMonster50health() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testValidShoot() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W Shoot 1 WEST M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());


  }

  @Test
  public void testShootArrowWaste() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W Shoot 1 NORTH Shoot 1 N M W "
        + "M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 "
        + "Arrows: 3; is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 0; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testShootMissesMonster() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 2 W Shoot 1 NORTH Shoot 3 N M W " +
        "M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 0; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testShootWorksAfterInvalidInt() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W Shoot three N M W "
        + "M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 S"
        + "apphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?Give input correctly : (eg 1,2) \n"
        + "\n"
        + "Toward cave?\n"
        + "Type only N,S,W,E. Try Again!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?Not a valid input, Please try again. \n"
        + "\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }


  @Test
  public void testShootWorksAfterInvalidInputDirection() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W S 1 W Shoot 3 UP M W " +
        "M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?\n"
        + "Type only N,S,W,E. Try Again!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }


  @Test
  public void testWrongDirectionMove() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M E M W M W S 1 W Shoot 3 W M W M S ");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; i"
        + "s at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?You can't go to EAST. \n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 1; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testValidArrowPickup() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W M W P A M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?You picked up CROOKED_ARROWPlayer: "
        + "Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 4; "
        + "is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testValidAllPickup() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W P ALL M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?You picked up ALL"
        + "Player: Ninja with treasure, Diamond: 1.0 Ruby: 1.0 Sapphire: 1.0 Arrows: 4; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 1.0 Ruby: 1.0 "
        + "Sapphire: 1.0 Arrows: 4; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testValidTreasurePickup() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W P D M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?You picked up DIAMOND"
        + "Player: Ninja with treasure, Diamond: 1.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 1.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testInValidTreasurePickup() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M N M W M W P Q P D M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?\n"
        + "Choose wisely. Try Again!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?You picked up DIAMOND"
        + "Player: Ninja with treasure, Diamond: 1.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 1.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testTreasurePickupFromBlank() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,50,2,false);
    StringReader input = new StringReader("M N M W M W P D M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?What to pickup ?DIAMOND not available in "
        + "present location \n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testValidMove() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,50,2,false);
    StringReader input = new StringReader("M N Move West M W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testMoveWithoutDoor() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,50,2,false);
    StringReader input = new StringReader("M West M North Move West M W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?You can't go to WEST. \n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testMoveInvalidInput() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,50,2,false);
    StringReader input = new StringReader("M UP M North Move West M W M W M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?\n"
        + "Type only N,S,E,W. Try Again!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Eaten by Otyugh ! Game Over !",gameLog.toString());


  }

  @Test
  public void testShootWithoutArrow() {

    DungeonInterface unwrappedD = new UnWrappedDungeon(2,4,
        0,50,2,false);
    StringReader input = new StringReader("M North Move West S 2 W S 2 W S 2 W S 2 W M W M W "
        + "M S");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,3\n"
        + "Doors lead to : NORTH \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,3\n"
        + "Doors lead to : SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?Your ability to shoot is not present ! \n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 0,2\n"
        + "Doors lead to : EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "Something Smells !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 0; is at Location 0,1\n"
        + "Doors lead to : SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 0; is at Location 0,0\n"
        + "Doors lead to : SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());


  }

  @Test
  public void testMoveWrapped() {

    DungeonInterface unwrappedD = new WrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M W MOVE S M WEST M NORTH SHOOT 1 WEST S 1 W M WEST");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,2\n"
        + "Doors lead to : WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,1\n"
        + "Doors lead to : NORTH SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,1\n"
        + "Doors lead to : NORTH SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 0,0\n"
        + "Doors lead to : NORTH SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 3; is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());



  }

  @Test
  public void testShootWrapped() {

    DungeonInterface unwrappedD = new WrappedDungeon(2,4,
        0,100,2,false);
    StringReader input = new StringReader("M W S 1 WEST MOVE S M WEST M NORTH SHOOT 1 WEST S 1 "
        + "W M WEST");
    Appendable gameLog = new StringBuilder();
    GameControllerI c = new GameConsoleController(input, gameLog, unwrappedD);
    c.playGame();
    assertEquals("Welcome to dungeon game !\n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,2\n"
        + "Doors lead to : WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "No Smell Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 3; "
        + "is at Location 1,1\n"
        + "Doors lead to : NORTH SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?YOU MISSED!!!\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 2; "
        + "is at Location 1,1\n"
        + "Doors lead to : NORTH SOUTH EAST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 0,1\n"
        + "Doors lead to : NORTH SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Daimond\n"
        + "1 Sapphire \n"
        + "1 Ruby \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 0,0\n"
        + "Doors lead to : NORTH SOUTH EAST WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?This location has : \n"
        + "1 Arrow \n"
        + "Something Smells TERRIBLE !!Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 "
        + "Sapphire: 0.0 Arrows: 2; is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you hit otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 1; "
        + "is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "No. of caves?\n"
        + "Toward cave?you killed otyugh\n"
        + "Player: Ninja with treasure, Diamond: 0.0 Ruby: 0.0 Sapphire: 0.0 Arrows: 0; "
        + "is at Location 1,0\n"
        + "Doors lead to : NORTH SOUTH WEST \n"
        + "\n"
        + "Shoot, Move or Pickup (S-M-P)?\n"
        + "Where to?Player Wins ! Game Over !",gameLog.toString());

  }
}
