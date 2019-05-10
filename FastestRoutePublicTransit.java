/**
 * Public Transit
 * Author: Adam vanWestrienen and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

  /**
   * The algorithm that could solve for shortest travel time from a station S
   * to a station T given various tables of information about each edge (u,v)
   *
   * @param S the s th vertex/station in the transit map, start From
   * @param T the t th vertex/station in the transit map, end at
   * @param startTime the start time in terms of number of minutes from 5:30am
   * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
   * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
   * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
   * @return shortest travel time between S and T
   */
  public int myShortestTravelTime(
          int S,
          int T,
          int startTime,
          int[][] lengths,
          int[][] first,
          int[][] freq
  ) {

    int numVertices = lengths[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[S] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;


      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        int travelTime = 0;

        // Do we have a valid link between u and v?
        if (!processed[v] && lengths[u][v] != 0 && times[u] != Integer.MAX_VALUE) {
          // Get the time we arrive at u.
          int current = times[u] + startTime;

          // Calculate the total time it will take for this route.  This takes into consideration
          // the various timing constraints associated with the route.

          travelTime = calculateTime(current, lengths[u][v], freq[u][v], first[u][v]);

          // Update only if we have found a shorter path to v.
          if (times[u] + travelTime < times[v]) {
            times[v] = times[u] + travelTime;
          }
        }
      }
    }
    return times[T];
  }


  /**
   * Calculate the time it will take to travel from one station to the next.
   * @param currentTime The time one arrives at the station.  An integer representing the number of minutes from 0530.
   * @param travelTime An integer representing the time (minutes) spent on the train.
   * @param frequency An integer representing the frequency (minutes) between trains.
   * @param startTime The time (minutes) when the first train runs.
   * @return The total time (minutes) it will take to travel the route.
   */
  private int calculateTime(int currentTime, int travelTime, int frequency, int startTime) {
    int totalTime = 0;
    if ( travelTime != 0 && frequency != 0) {
      // If we arrive at the station before the first train, then add in the time we must wait.
      totalTime = currentTime < startTime ? startTime - currentTime : 0;

      // If the trains are already running, then we only have to wait for the next
      // scheduled train.
      if (totalTime == 0 && currentTime != startTime) {
        totalTime = (int) Math.abs((Math.ceil(currentTime / frequency) * frequency) - currentTime);
      }


      // Add in the time it takes to travel the route.
      totalTime += travelTime;
    }

    return totalTime;
  }

  /**
   * Convert a station number into a letter.
   * @param station The integer value of the station.
   * @return The cooresponding letter as a string.
   */
  public static String toAlpha(int station) {
    switch (station) {
      case 0:
        return "A";
      case 1:
        return "B";
      case 2:
        return "C";
      case 3:
        return "D";
      case 4:
        return "E";
      case 5:
        return "F";
      case 6:
        return "G";
      case 7:
        return "H";
      case 8:
        return "I";
      case 9:
        return "J";
      case 10:
        return "K";
      case 11:
        return "L";
      case 12:
        return "M";
      case 13:
        return "N";
      case 14:
        return "O";
      case 15:
        return "P";
      case 16:
        return "Q";
      default:
        return "?";
    }
  }


  /**
   * Finds the vertex with the minimum time from the source that has not been
   * processed yet.
   * @param times The shortest times from the source
   * @param processed boolean array tells you which vertices have been fully processed
   * @return the index of the vertex that is next vertex to process
   */
  public int findNextToProcess(int[] times, Boolean[] processed) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;

    for (int i = 0; i < times.length; i++) {
      if (processed[i] == false && times[i] <= min) {
        min = times[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public void printShortestTimes(int times[]) {
    System.out.println("Vertex Distances (time) from Source");
    for (int i = 0; i < times.length; i++)
      System.out.println(i + ": " + times[i] + " minutes");
  }

  /**
   * Given an adjacency matrix of a graph, implements
   * @param graph The connected, directed graph in an adjacency matrix where
   *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
   * @param source The starting vertex
   */
  public void shortestTime(int graph[][], int source) {
    int numVertices = graph[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[source] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
          times[v] = times[u] + graph[u][v];
        }
      }
    }

    printShortestTimes(times);
  }

  public static void main (String[] args) {
    /* length(e) */
    int lengthTimeGraph[][] = new int[][]{
            { 0,  4,  0,  0,  0,  0,  0,  8,  0},
            { 4,  0,  8,  0,  0,  0,  0, 11,  0},
            { 0,  8,  0,  7,  0,  4,  0,  0,  2},
            { 0,  0,  7,  0,  9, 14,  0,  0,  0},
            { 0,  0,  0,  9,  0, 10,  0,  0,  0},
            { 0,  0,  4, 14, 10,  0,  2,  0,  0},
            { 0,  0,  0,  0,  0,  2,  0,  1,  6},
            { 8, 11,  0,  0,  0,  0,  1,  0,  7},
            { 0,  0,  2,  0,  0,  0,  6,  7,  0}
    };
    FastestRoutePublicTransit t = new FastestRoutePublicTransit();
    t.shortestTime(lengthTimeGraph, 0);

    // You can create a test case for your implemented method for extra credit below
    int startTimeGraph[][] = new int[][]{
            { 0,  1,  0,  0,  0,  0,  0,  1,  0},
            { 1,  0,  1,  0,  0,  0,  0,  1,  0},
            { 0,  1,  0,  1,  0,  1,  0,  0,  1},
            { 0,  0,  1,  0,  1,  1,  0,  0,  0},
            { 0,  0,  0,  1,  0,  1,  0,  0,  0},
            { 0,  0,  1,  1,  1,  0,  1,  0,  0},
            { 0,  0,  0,  0,  0,  1,  0,  1,  1},
            { 1,  1,  0,  0,  0,  0,  1,  0,  1},
            { 0,  0,  1,  0,  0,  0,  1,  1,  0}
    };

    int freqTimeGraph[][] = new int[][]{
            { 0,  5,  0,  0,  0,  0,  0,  5,  0},
            { 5,  0,  5,  0,  0,  0,  0,  5,  0},
            { 0,  5,  0,  5,  0,  5,  0,  0,  5},
            { 0,  0,  5,  0,  5,  5,  0,  0,  0},
            { 0,  0,  0,  5,  0,  5,  0,  0,  0},
            { 0,  0,  5,  5,  5,  0,  5,  0,  0},
            { 0,  0,  0,  0,  0,  5,  0,  5,  5},
            { 5,  5,  0,  0,  0,  0,  5,  0,  5},
            { 0,  0,  5,  0,  0,  0,  5,  5,  0}
    };

    System.out.println("\nRun tests using time considerations with Dijkstra's algorithm.");
    runTest(t,0, 3, 5, lengthTimeGraph, startTimeGraph, freqTimeGraph);
    runTest(t, 1, 4, 0, lengthTimeGraph, startTimeGraph, freqTimeGraph);


  }

  /**
   * Run the myShortestTravelTime method.
   * @param t An instance of the FastestRoutePublicTransit class.
   * @param startStation The station to start at.
   * @param endStation The station to terminate at.
   * @param startTime The time to start the journey (0 = 0530)
   * @param lengthTimeGraph An array of the times the trains take per route.
   * @param startTimeGraph An array of when the trains start service for a route.
   * @param freqTimeGraph An array of the frequency of train service on the route.
   */
  public static void runTest(FastestRoutePublicTransit t, int startStation, int endStation, int startTime, int[][] lengthTimeGraph, int[][] startTimeGraph, int[][] freqTimeGraph ) {

    System.out.println("\nCalculate time from station " + toAlpha(startStation) + " to station " + toAlpha(endStation));
    int totalTime = t.myShortestTravelTime(startStation, endStation, startTime, lengthTimeGraph, startTimeGraph, freqTimeGraph);
    System.out.println("Start: " + toAlpha(startStation) + " End: " + toAlpha(endStation) + " Start time: " + startTime + " Total time: " + totalTime);

  }
}