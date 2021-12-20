import controller.GameConsoleController;
import controller.GameControllerGui;
import controller.GameControllerI;
import model.dungeon.DungeonInterface;
import model.dungeon.UnWrappedDungeon;
import model.dungeon.WrappedDungeon;
import view.DungeonGameView;
import view.IView;

import java.io.InputStreamReader;



/**
 * Driver class to run dungeon game in GUI mode and Text mode.
 */
public class Driver {

  /**
   * Main method to run dungeon game.
   * @param args takes in argument for differentiating GUI mode and Text mode.
   */
  public static void main(String[] args) {
    Driver driver = new Driver();
    if (args.length == 0) {
      DungeonInterface dungeonModel = new UnWrappedDungeon(4, 4, 0, 100, 2, true);
      IView view = new DungeonGameView("Dungeon Game!", dungeonModel);

      GameControllerI c = new GameControllerGui(dungeonModel, view);
      c.playGame();
    }
    else {
      try {
        int mazeType = Integer.parseInt(args[0]);
        int rows = Integer.parseInt(args[1]);
        int cols = Integer.parseInt(args[2]);
        int inter = Integer.parseInt(args[3]);
        int treasurePercent = Integer.parseInt(args[4]);
        int monsterNo = Integer.parseInt(args[5]);
        driver.run(mazeType,rows,cols,inter,treasurePercent,monsterNo);
      } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
        System.out.println("Enter 6 valid arguments");
      }
    }
  }

  void run(int type, int rows, int cols, int inter, int treasurePercent, int monsterNo) {
    DungeonInterface maze;
    switch (type) {
      case 1:
        try {
          maze = new UnWrappedDungeon(rows, cols, inter, treasurePercent, monsterNo,
          true);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }
        break;
      case 2:
        try {
          maze = new WrappedDungeon(rows, cols, inter, treasurePercent, monsterNo ,
          true);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }
        break;
      default:
        System.out.println("Invalid maze type.");
        return;
    }

    GameControllerI c =
        new GameConsoleController(new InputStreamReader(System.in), System.out, maze);
    c.playGame();
  }
}
