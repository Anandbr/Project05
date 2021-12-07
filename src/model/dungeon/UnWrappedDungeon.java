package model.dungeon;

/**
 * This class represents an unwrapped dungeon.
 *
 */

public class UnWrappedDungeon extends AbstractDungeon {

  /**
   * Constructor for Unwrapped dungeon.
   * @param numberOfRows : number of rows in the maze
   * @param numberOfColumns : number of columns in the maze
   * @param interconnectivity : number of walls that should be left after maze creation
   * @param treasurePercent :percentage of treasure input by client.
   * @param randomType : mock or actual random type
   * @throws IllegalArgumentException if any parameter is negative
   * */
  public UnWrappedDungeon(int numberOfRows, int numberOfColumns, int interconnectivity,
                          double treasurePercent, int otyughNumber, boolean randomType) {
    super(numberOfRows, numberOfColumns, false, interconnectivity,treasurePercent,
            otyughNumber, randomType);
  }


}