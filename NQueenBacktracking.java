public class NQueenBacktracking {
    public static void solveNQueen(int n, int[] solution, int row) {
        if (row == n) {
            System.out.println("Solution for " + n + "-Queen is:");
            printSolution(solution);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (isSafe(solution, row, i)) {
                solution[row] = i;
                solveNQueen(n, solution, row + 1);
            }
        }

    }

    public static boolean isSafe(int[] solution, int row, int col) {
        for (int i = 0; i < row; i++) {
            if (solution[i] == col || Math.abs(row - i) == Math.abs(solution[i] - col))
                return false;
        }
        return true;
    }

    public static void printSolution(int[] arr) {
        System.out.print('[');
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println(']');
    }

    public static void main(String[] args) {
        for (int i = 4; i < 9; i++) {
            long startTime = System.nanoTime();
            solveNQueen(i, new int[i], 0);
            long endTime = System.nanoTime();

            System.out.println("time required to solve" + i + "queen: " + (endTime - startTime) + " nanoseconds");
        }
    }
}
