package model.kruskal;

/**
 * This interface represents the methods that can be implemented to use Kruskal's algorithm for
 * dungeon building.
 *
 */

public interface DungeonBuilderInterface {


  /**
   * Modified Kruskal Algo to create a Dungeon from a DungeonGraph.
   * @param graph : a graph which you want to convert into a dungeon.
   * @param interconnectivity : the interconnectivity of dungeon.
   * @throws IllegalArgumentException : if number of interconnectivity not possible
   *                      with the given graph
   * */
  void createMazeWithKruskal(GraphInterface graph, int interconnectivity);

}
