import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TSPCrossover {

    static Random rand = new Random();
    static int numCities = 4;  // Number of cities in the example distance matrix

    // Example distance matrix (adjacency matrix)
    static int[][] distMatrix = {
        {0, 10, 15, 10},
        {5, 0, 9, 10},
        {6, 13, 0, 12},
        {8, 8, 9, 0}
    };

    // Function to demonstrate single-point crossover between two parent chromosomes
    public static int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[numCities];

        // Step 1: Choose a random crossover point
        int crossoverPoint = rand.nextInt(numCities);  // Random crossover point between 0 and numCities-1

        // Step 2: Copy the first part of parent1 to the child (up to the crossover point)
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
        }

        // Step 3: Copy the second part from parent2 and fix duplicates
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < crossoverPoint; i++) {
            visited.add(child[i]);
        }

        int currentIndex = crossoverPoint;
        for (int i = 0; i < numCities; i++) {
            // Skip cities already added from parent1
            if (!visited.contains(parent2[i])) {
                child[currentIndex] = parent2[i];
                visited.add(parent2[i]);
                currentIndex++;
            }
        }

        return child;
    }

    // Function to print a chromosome (tour)
    public static void printChromosome(int[] chromosome) {
        System.out.println(Arrays.toString(chromosome));
    }

    // Main method to demonstrate the crossover
    public static void main(String[] args) {
        // Example parent chromosomes (tours)
        int[] parent1 = {0, 1, 2, 3};  // Tour 1: City 0 -> City 1 -> City 2 -> City 3
        int[] parent2 = {2, 0, 3, 1};  // Tour 2: City 2 -> City 0 -> City 3 -> City 1

        System.out.println("Parent 1:");
        printChromosome(parent1);

        System.out.println("Parent 2:");
        printChromosome(parent2);

        // Perform crossover
        int[] child = crossover(parent1, parent2);

        System.out.println("Child (after crossover):");
        printChromosome(child);
    }
}
