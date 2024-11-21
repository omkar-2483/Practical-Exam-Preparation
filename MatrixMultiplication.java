public class MatrixMultiplication {

    // Method for sequential matrix multiplication
    public static void sequentialMatrixMultiplication(int[][] A, int[][] B, int[][] C) {
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = 0;
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
    }

    // Method for multithreaded matrix multiplication
    public static void multithreadedMatrixMultiplication(int[][] A, int[][] B, int[][] C) {
        int n = A.length;
        Thread[] threads = new Thread[n]; // One thread for each row of the result matrix

        for (int i = 0; i < n; i++) {
            final int row = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < n; j++) {
                    C[row][j] = 0;
                    for (int k = 0; k < n; k++) {
                        C[row][j] += A[row][k] * B[k][j];
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        try {
            for (int i = 0; i < n; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to print the matrix
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Method to generate random matrix
    public static int[][] generateMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = (int) (Math.random() * 10);
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int n = 1000; // Size of the matrix
        int[][] A = generateMatrix(n);
        int[][] B = generateMatrix(n);
        int[][] CSequential = new int[n][n];
        int[][] CMultithreaded = new int[n][n];

        // Measure time for sequential multiplication
        long startTime = System.nanoTime();
        sequentialMatrixMultiplication(A, B, CSequential);
        long endTime = System.nanoTime();
        long sequentialTime = endTime - startTime;

        // Measure time for multithreaded multiplication
        startTime = System.nanoTime();
        multithreadedMatrixMultiplication(A, B, CMultithreaded);
        endTime = System.nanoTime();
        long multithreadedTime = endTime - startTime;

        // Print the time taken
        System.out.println("Sequential multiplication time: " + sequentialTime / 1_000_000 + " ms");
        System.out.println("Multithreaded multiplication time: " + multithreadedTime / 1_000_000 + " ms");

        // Uncomment the following lines to print the resulting matrices (not recommended for large matrices)
        // System.out.println("Result from Sequential Multiplication:");
        // printMatrix(CSequential);

        // System.out.println("Result from Multithreaded Multiplication:");
        // printMatrix(CMultithreaded);
    }
}
