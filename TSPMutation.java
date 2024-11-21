import java.util.Random;

public class TSPMutation {

    // Function to perform mutation on a chromosome representing TSP solution
    public static int[] tspMutation(int[] chromosome) {
        // Create a copy of the chromosome to avoid modifying the original one
        int[] mutatedChromosome = chromosome.clone();
        
        // Create a random object to generate random numbers
        Random rand = new Random();
        
        // Select two random indices for mutation (swap mutation)
        int idx1 = rand.nextInt(chromosome.length);
        int idx2 = rand.nextInt(chromosome.length);
        
        // Ensure idx1 and idx2 are different
        while (idx1 == idx2) {
            idx2 = rand.nextInt(chromosome.length);
        }
        
        // Swap the two cities
        int temp = mutatedChromosome[idx1];
        mutatedChromosome[idx1] = mutatedChromosome[idx2];
        mutatedChromosome[idx2] = temp;
        
        return mutatedChromosome;
    }

    // Function to print the chromosome
    public static void printChromosome(int[] chromosome) {
        for (int city : chromosome) {
            System.out.print(city + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example chromosome representing a TSP solution (order of cities)
        int[] chromosome = {0, 1, 2, 3}; // Initial path: 0 -> 1 -> 2 -> 3
        
        System.out.println("Original Chromosome:");
        printChromosome(chromosome);
        
        // Perform mutation
        int[] mutatedChromosome = tspMutation(chromosome);
        
        System.out.println("Mutated Chromosome:");
        printChromosome(mutatedChromosome);
    }
}

