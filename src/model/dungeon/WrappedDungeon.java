package model.dungeon;

/**
 * This class represents a wrapped dungeon.
 *
 */

public class WrappedDungeon extends AbstractDungeon {

  /**
   * Constructor for wrapped dungeon.
   * @param numberOfRows : number of rows in the maze
   * @param numberOfColumns : number of columns in the maze
   * @param interconnectivity : number of walls that should be left after maze creation
   * @param treasurePercent :percentage of treasure input by client.
   * @param randomType : mock or actual random type
   * @throws IllegalArgumentException if any parameter is negative
   * */
  public WrappedDungeon(int numberOfRows, int numberOfColumns,
                        int interconnectivity, double treasurePercent, int otyughNumber,
                        boolean randomType) {
    super(numberOfRows, numberOfColumns, true, interconnectivity,treasurePercent,
            otyughNumber, randomType);
  }


}
