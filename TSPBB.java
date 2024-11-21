import java.util.Arrays;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    int[][] reducedMatrix;
    int cost;
    int level;
    int city;
    int[] path;

    public Node(int[][] reducedMatrix, int cost, int level, int city, int[] path) {
        this.reducedMatrix = reducedMatrix;
        this.cost = cost;
        this.level = level;
        this.city = city;
        this.path = path;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.cost, other.cost);
    }
}

public class TSPBB {
    static final int INF = Integer.MAX_VALUE;

    // Reduce matrix and calculate cost
    public static int reduceMatrix(int[][] matrix) {
        int cost = 0;
        int n = matrix.length;

        // Row reduction
        for (int i = 0; i < n; i++) {
            int rowMin = INF;
            for (int j = 0; j < n; j++) {
                rowMin = Math.min(rowMin, matrix[i][j]);
            }
            if (rowMin != INF && rowMin != 0) {
                cost += rowMin;
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] != INF)
                        matrix[i][j] -= rowMin;
                }
            }
        }

        // Column reduction
        for (int j = 0; j < n; j++) {
            int colMin = INF;
            for (int i = 0; i < n; i++) {
                colMin = Math.min(colMin, matrix[i][j]);
            }
            if (colMin != INF && colMin != 0) {
                cost += colMin;
                for (int i = 0; i < n; i++) {
                    if (matrix[i][j] != INF)
                        matrix[i][j] -= colMin;
                }
            }
        }
        return cost;
    }

    // Solve TSP using Branch and Bound
    public static int solveTSP(int[][] graph) {
        int n = graph.length;

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Initial reduced matrix and cost
        int[][] initialMatrix = Arrays.stream(graph).map(int[]::clone).toArray(int[][]::new);
        int cost = reduceMatrix(initialMatrix);

        int[] initialPath = new int[n + 1];
        initialPath[0] = 0; // Starting from city 0

        Node root = new Node(initialMatrix, cost, 0, 0, initialPath);
        pq.add(root);

        int bestCost = INF;
        int[] bestPath = null;

        while (!pq.isEmpty()) {
            Node curNode = pq.poll();

            if (curNode.level == n - 1) {
                // Add the cost to return to the start city (city 0)
                int finalCost = curNode.cost + graph[curNode.city][0];
                if (finalCost < bestCost) {
                    bestCost = finalCost;
                    bestPath = curNode.path.clone();
                    bestPath[n] = 0; // Complete the cycle
                }
                continue;
            }

            for (int nextCity = 0; nextCity < n; nextCity++) {
                // Skip if the city has already been visited or no edge exists
                if (isVisited(curNode.path, curNode.level, nextCity) || curNode.reducedMatrix[curNode.city][nextCity] == INF) {
                    continue;
                }

                // Create a new reduced matrix by cloning the current matrix
                int[][] newMatrix = Arrays.stream(curNode.reducedMatrix).map(int[]::clone).toArray(int[][]::new);

                // Remove the row and column of the current city and the next city
                for (int i = 0; i < n; i++) {
                    newMatrix[curNode.city][i] = INF;
                    newMatrix[i][nextCity] = INF;
                }
                for (int i = 0; i <= curNode.level; i++) {
                    newMatrix[nextCity][curNode.path[i]] = INF;
                }

                int edgeCost = curNode.reducedMatrix[curNode.city][nextCity]; // Cost to travel from current city to nextCity
                int reductionCost = reduceMatrix(newMatrix); // Reduction cost for the remaining matrix
                int newCost = curNode.cost + edgeCost + reductionCost; // Total cost

                // Only add the new node to the priority queue if the new cost is less than the best found so far
                int[] newPath = curNode.path.clone();
                newPath[curNode.level + 1] = nextCity;
                if (newCost < bestCost) {
                    pq.add(new Node(newMatrix, newCost, curNode.level + 1, nextCity, newPath));
                }
            }
        }

        // Print the best path and cost found
        System.out.println("Best Path: " + Arrays.toString(bestPath));
        return bestCost;
    }

    // Check if a city is already visited
    static boolean isVisited(int[] path, int level, int city) {
        for (int i = 0; i <= level; i++) {
            if (path[i] == city) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // Example graph
        int[][] graph = {
            { INF, 10, 15, 20 },
            { 5, INF, 9, 10 },
            { 6, 13, INF, 12 },
            { 8, 8, 9, INF }
        };

        // Solve TSP and print the best cost
        int bestCost = solveTSP(graph);
        System.out.println("Best Cost: " + bestCost);
    }
}
