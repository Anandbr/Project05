package controller;

import model.dungeon.DungeonInterface;
import model.dungeon.PlayerStatus;
import model.kruskal.DirectionEnum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Class controller which handles the control of the game.
 * */


public class GameConsoleController implements GameControllerI {

  private final Appendable out;
  private final Scanner scan;
  private final DungeonInterface dungeonGame;


  /**
   * Constructor for controller.
   *
   * @param in is readable
   * @param out is appendable
   * @param m is model
   * */
  public GameConsoleController(Readable in, Appendable out, DungeonInterface m) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }

    if (m == null) {
      throw new IllegalArgumentException("Model cant be null");
    }

    this.out = out;
    scan = new Scanner(in);
    this.dungeonGame = m;

  }


  @Override
  public void playGame()  {

    try {

      out.append("Welcome to dungeon game !\n");


      out.append(dungeonGame.enterPlayer());

      while (dungeonGame.getPlayer().getPlayerStatus().equals(PlayerStatus.ALIVE)) {
        out.append(dungeonGame.getPlayerDesc());

        out.append("\nDoors lead to : ");
        for (DirectionEnum direction :
            dungeonGame.getPossibleMoves(dungeonGame.getPlayer().getCurrentLocation())) {
          out.append(direction.toString()).append(" ");
        }
        out.append("\n\nShoot, Move or Pickup (S-M-P)?");
        Scanner scanner = this.scan;


        String choice = readInputString(scanner);
        switch (choice) {
          case "S":
          case "Shoot":
          case "SHOOT":
            out.append("\nNo. of caves?");

            int noCave = 0;
            try {

              noCave = scan.nextInt();


            } catch (InputMismatchException e) {

              out.append("Give input correctly : (eg 1,2) \n");

            }


            out.append("\nToward cave?");
            String direction = scan.next();
            try {

              switch (direction) {
                case "N":
                case "North":
                case "NORTH":
                  out.append(dungeonGame.shootArrow(DirectionEnum.NORTH, noCave));
                  break;
                case "S":
                case "South":
                case "SOUTH":
                  out.append(dungeonGame.shootArrow(DirectionEnum.SOUTH, noCave));
                  break;
                case "E":
                case "East":
                case "EAST":
                  out.append(dungeonGame.shootArrow(DirectionEnum.EAST, noCave));
                  break;
                case "W":
                case "West":
                case "WEST":
                  out.append(dungeonGame.shootArrow(DirectionEnum.WEST, noCave));
                  break;
                default: out.append("\nType only N,S,W,E. Try Again!\n");

              }

            } catch (IllegalArgumentException e) {

              this.transmitMessage(getInvalidShootMessage(e));

            }

            break;
          case "M":
          case "Move":
          case "MOVE":
            out.append("\nWhere to?");
            direction = scan.next();
            try {

              switch (direction) {
                case "N":
                case "North":
                case "NORTH":
                  out.append(dungeonGame.makeMove(DirectionEnum.NORTH));
                  break;
                case "S":
                case "South":
                case "SOUTH":
                  out.append(dungeonGame.makeMove(DirectionEnum.SOUTH));
                  break;
                case "E":
                case "East":
                case "EAST":
                  out.append(dungeonGame.makeMove(DirectionEnum.EAST));
                  break;
                case "W":
                case "West":
                case "WEST":
                  out.append(dungeonGame.makeMove(DirectionEnum.WEST));
                  break;
                default: out.append("\nType only N,S,E,W. Try Again!\n");
              }

            } catch (IllegalArgumentException e) {

              this.transmitMessage(getInvalidMoveMessage(e));
            }

            break;

          case "P":
          case "Pick up":
          case "Pickup":
          case "PICKUP" :
            out.append("What to pickup ?");
            String option = scan.next();
            try {

              switch (option) {
                case "D":
                case "DIAMOND":
                case "diamond":
                  out.append(dungeonGame.pickUpTreasure("DIAMOND"));
                  break;
                case "R":
                case "RUBY":
                case "ruby":
                  out.append(dungeonGame.pickUpTreasure("RUBY"));
                  break;
                case "S":
                case "SAPPHIRE":
                case "sapphire":
                  out.append(dungeonGame.pickUpTreasure("SAPPHIRE"));
                  break;
                case "A":
                case "ARROW":
                case "arrow":
                  out.append(dungeonGame.pickUpTreasure("CROOKED_ARROW"));
                  break;
                case "ALL":
                case "all":
                  out.append(dungeonGame.pickUpTreasure("ALL"));
                  break;

                default: out.append("\nChoose wisely. Try Again!\n");
              }

            } catch (IllegalArgumentException e) {

              this.transmitMessage(getInvalidPickupMessage(e));

            }


            break;
          default:
            out.append("\nNot a valid input, Please try again.\n");

        }

      }
    } catch (IOException ioe) {
      throw new IllegalStateException("cannot write to appendable", ioe);
    }
  }

  private void transmitMessage(String message) throws IllegalStateException {
    try {
      out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("cannot write to appendable");
    }
  }

  private String getInvalidInputMessage() {
    return String.format("Not a valid input, Please try again. \n");
  }


  private String getInvalidShootMessage(IllegalArgumentException e ) {
    return e.getMessage() + " \n";
  }

  private String getInvalidMoveMessage(IllegalArgumentException e ) {
    return e.getMessage() + " \n";
  }

  private String getInvalidPickupMessage(IllegalArgumentException e ) {
    return e.getMessage() + " \n";
  }




  private String readInputString(Scanner scanner) throws IllegalArgumentException {

    List<String> validOptions = new ArrayList<>();
    validOptions.add("S");
    validOptions.add("SHOOT");
    validOptions.add("Shoot");
    validOptions.add("P");
    validOptions.add("Pick up");
    validOptions.add("Pickup");
    validOptions.add("PICKUP");
    validOptions.add("M");
    validOptions.add("Move");
    validOptions.add("MOVE");


    while (true) {
      String inputString = getNextInput(scanner);

      try {

        if (!validOptions.contains(inputString)) {
          throw new IllegalArgumentException("Choose Again ! ");
        }
        return inputString;
      } catch (IllegalArgumentException e) {
        this.transmitMessage(getInvalidInputMessage());

      }
    }
  }

  private String getNextInput(Scanner scanner) throws IllegalStateException {
    try {
      return scanner.next();
    } catch (Exception e) {
      throw new IllegalStateException("cannot read from readable");
    }
  }
}
