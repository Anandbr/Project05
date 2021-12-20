package model.kruskal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class represents builder for building dungeon graph using kruskal's algorithm.
 *
 */

public class DungeonBuilder implements DungeonBuilderInterface {


  private final HashMap<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> parent =
          new HashMap<>();
  private final HashMap<Map.Entry<Integer, Integer>, Integer> rank = new HashMap<>();

  private Map.Entry<Integer, Integer> find( Map.Entry<Integer, Integer> i) {
    if (parent.get(i) == i) {
      return i;
    }
    return find( parent.get(i));
  }

  private void union( Map.Entry<Integer, Integer> x, Map.Entry<Integer, Integer> y) {
    Map.Entry<Integer, Integer> xroot = find( x);
    Map.Entry<Integer, Integer> yroot = find(y);

    if (rank.get(xroot) < rank.get(yroot) ) {
      parent.put(xroot, yroot);
    }
    else if (rank.get(xroot) > rank.get(yroot)) {
      parent.put(yroot, xroot);
    }
    else {
      parent.put(yroot, xroot);
      rank.put(xroot, rank.get(xroot) + 1);
    }
  }


  /**
   * Modified Kruskal Algo to create a Dungeon from a DungeonGraph.
   * @param graph : a graph which you want to convert into a dungeon.
   * @param interconnectivity : the interconnectivity of dungeon.
   * @throws IllegalArgumentException : if number of interconnectivity not possible
   *                      with the given graph
   * */
  @Override
  public void createMazeWithKruskal(GraphInterface graph, int interconnectivity)
          throws IllegalArgumentException {

    ArrayList<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> result =
            new ArrayList<>();
    List<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> adjList =
            graph.getEdges();

    for (Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> temp : adjList) {
      parent.put(temp.getKey(), temp.getKey());
      rank.put(temp.getKey(), 0);
      parent.put(temp.getValue(), temp.getValue());
      rank.put(temp.getValue(), 0);
    }

    int e = 0;
    int j = 0;
    while ( e < graph.getNumberOfNodes() - 1) {
      Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> temp = adjList.get(j++);

      Map.Entry<Integer, Integer> u = temp.getKey();
      Map.Entry<Integer, Integer> v = temp.getValue();

      Map.Entry<Integer, Integer> x = find(u);
      Map.Entry<Integer, Integer> y = find(v);

      if (x != y) {
        e += 1;
        result.add(temp);
        union( x, y);
      }
    }

    ArrayList<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> savedEdges =
            new ArrayList<>();
    for (Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>> pair : adjList) {
      if (!result.contains(pair)) {
        savedEdges.add(pair);
      }
    }

    if (savedEdges.size() < interconnectivity) {
      throw new IllegalArgumentException("Interconnectivity not supported.");
    }

    Iterator<Map.Entry<Map.Entry<Integer, Integer>, Map.Entry<Integer, Integer>>> iterator =
            savedEdges.iterator();
    while ( interconnectivity != 0 && iterator.hasNext()) {
      result.add(iterator.next());
      interconnectivity--;
    }
    graph.createMazeGraphFromEdges(result);
  }


}
