import java.util.Arrays;
import java.util.Random;

public class TSPGeneticAlgorithm {

    static Random rand = new Random();
    static int numCities = 4;  // Number of cities
    static int[][] distMatrix = {
        {0, 10, 15, 10},
        {5, 0, 9, 10},
        {6, 13, 0, 12},
        {8, 8, 9, 0}
    };

    // Function to calculate the total distance (fitness function)
    public static int calculateTourLength(int[] tour) {
        int totalDistance = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            totalDistance += distMatrix[tour[i]][tour[i + 1]];
        }
        totalDistance += distMatrix[tour[tour.length - 1]][tour[0]];  // Return to starting point
        return totalDistance;
    }

    // Function to perform single-point crossover between two parent chromosomes
    public static int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[numCities];
        int crossoverPoint = rand.nextInt(numCities);  // Random crossover point

        boolean[] visited = new boolean[numCities];
        // Copy first part from parent1
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
            visited[child[i]] = true;
        }

        int currentIndex = crossoverPoint;
        for (int i = 0; i < numCities; i++) {
            if (!visited[parent2[i]]) {
                child[currentIndex++] = parent2[i];
            }
        }
        return child;
    }

    // Function to perform mutation (swap two random cities)
    public static void mutate(int[] tour) {
        int i = rand.nextInt(numCities);
        int j = rand.nextInt(numCities);
        while (i == j) {
            j = rand.nextInt(numCities);  // Ensure i and j are not the same
        }

        // Swap the cities
        int temp = tour[i];
        tour[i] = tour[j];
        tour[j] = temp;
    }

    // Function to print a chromosome (tour)
    public static void printChromosome(int[] chromosome) {
        System.out.println(Arrays.toString(chromosome));
    }

    // Function to generate a random chromosome (tour)
    public static int[] generateRandomChromosome() {
        int[] chromosome = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            chromosome[i] = i;
        }
        // Shuffle the cities randomly to generate a random tour
        for (int i = 0; i < numCities; i++) {
            int j = rand.nextInt(numCities);
            int temp = chromosome[i];
            chromosome[i] = chromosome[j];
            chromosome[j] = temp;
        }
        return chromosome;
    }

    // Function to run the genetic algorithm for TSP
    public static int[] runTSPGA(int generations, int populationSize) {
        // Generate the initial population
        int[][] population = new int[populationSize][numCities];
        for (int i = 0; i < populationSize; i++) {
            population[i] = generateRandomChromosome();
        }

        // Evolve the population over multiple generations
        for (int gen = 0; gen < generations; gen++) {
            // Evaluate fitness (shortest path is better)
            int[] fitness = new int[populationSize];
            for (int i = 0; i < populationSize; i++) {
                fitness[i] = calculateTourLength(population[i]);
            }

            // Select the two best parents (tournament selection or simple selection)
            int[] parent1 = population[0];
            int[] parent2 = population[1];
            int bestFitness = Integer.MAX_VALUE;

            for (int i = 0; i < populationSize; i++) {
                if (fitness[i] < bestFitness) {
                    parent1 = population[i];
                    bestFitness = fitness[i];
                }
            }

            bestFitness = Integer.MAX_VALUE;
            for (int i = 0; i < populationSize; i++) {
                if (fitness[i] < bestFitness && population[i] != parent1) {
                    parent2 = population[i];
                    bestFitness = fitness[i];
                }
            }

            // Create a new population with crossover and mutation
            int[][] newPopulation = new int[populationSize][numCities];
            for (int i = 0; i < populationSize; i++) {
                int[] child = crossover(parent1, parent2);
                mutate(child);  // Mutate the child chromosome
                newPopulation[i] = child;
            }

            // Replace the old population with the new population
            population = newPopulation;
        }

        // Return the best solution found
        int[] bestTour = population[0];
        int bestTourLength = calculateTourLength(bestTour);
        for (int i = 1; i < populationSize; i++) {
            int tourLength = calculateTourLength(population[i]);
            if (tourLength < bestTourLength) {
                bestTour = population[i];
                bestTourLength = tourLength;
            }
        }
        return bestTour;
    }

    // Main method to run the genetic algorithm and solve TSP
    public static void main(String[] args) {
        int generations = 1000;  // Number of generations
        int populationSize = 50; // Population size

        // Solve TSP using genetic algorithm
        int[] bestTour = runTSPGA(generations, populationSize);

        // Output the best tour and its length
        System.out.println("Best Tour: ");
        printChromosome(bestTour);
        System.out.println("Total Distance: " + calculateTourLength(bestTour));
    }
}
