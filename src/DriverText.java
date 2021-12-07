import controller.GameControllerI;
import java.io.InputStreamReader;
import java.util.Scanner;

import controller.GameConsoleController;
import model.dungeon.DungeonInterface;
import model.dungeon.WrappedDungeon;
import model.dungeon.UnWrappedDungeon;

/**
 * Driver class to run the program.
 * */
public class DriverText {

  /**
   * Main class which creates the configuration and hands
   * over control to controller after initialization.
   * */

//    static Scanner scanner = new Scanner(System.in);

  /**
   * Driver program to run the maze.
   * @param args : 1 -  for Maze to be configured from file  2 -  for interactive maze creation
   * */

  public static void main(String[] args) {

    DriverText driver = new DriverText();


//        String[] input = {"1", "2", "4" ,"0", "90", "2"};
//        if (args.length != 0) {
//            StringBuilder sb = new StringBuilder();
//            for (String arg : args) {
//                sb.append(arg);
//            }
//            String str = sb.toString();
//            Stream<String> in = Arrays.stream(args);
//                InputStream in = Stream.builder();
//                assert in != null;
//                scanner = new Scanner(str);

//        }
    try {
      int mazeType = 1;
      int rows = 5;
      int cols = 5;
      int inter = 1;
      int treasurePercent = 100;
      int monsterNo = 1;
      driver.run(mazeType,rows,cols,inter,treasurePercent,monsterNo);
    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {

      System.out.println("Enter 6 valid arguments");

    }


  }

  void run(int type, int rows,int cols, int remainingWalls, int treasurePercent, int monster) {

//        System.out.println("Hello!");
//        System.out.println("Enter type of Dungeon - \n 1 - Unwrapped Maze \n 2 - Wrapped Maze");
//        //final int type = scanner.nextInt();
//        System.out.println("Enter rows: ");
//        //int rows = scanner.nextInt();
//        System.out.println("Enter columns: ");
//        //int cols = scanner.nextInt();
//        System.out.println("Enter interconnectivity for maze: ");
//        //int remainingWalls = scanner.nextInt();
//
//        System.out.println("Enter percentage of treasure/arrow in the maze: ");
//        int treasurePercent = scanner.nextInt();
//
//        System.out.println("Enter number of Monsters in maze: ");
//        int monster = scanner.nextInt();



    DungeonInterface maze;

    switch (type) {
      case 1:
        try {
          maze = new UnWrappedDungeon(rows, cols, remainingWalls, treasurePercent, monster, false);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }
        break;

      case 2:
        try {
          maze = new WrappedDungeon(rows, cols, remainingWalls, treasurePercent, monster , false);
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
          return;
        }
        break;
      default:
        System.out.println("Invalid maze type.");
        return;
    }

    GameControllerI control = new GameConsoleController(new InputStreamReader(System.in), System.out,maze);
    control.playGame();


  }
}
