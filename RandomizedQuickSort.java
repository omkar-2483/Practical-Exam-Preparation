public class RandomizedQuickSort {
    public static void randomizedQuickSort(int[] arr, int start, int end) {
        if (start >= end)
            return;

        int pivotIdx = randomizedPartition(arr, start, end);
        randomizedQuickSort(arr, start, pivotIdx - 1);
        randomizedQuickSort(arr, pivotIdx + 1, end);
    }

    private static int randomizedPartition(int[] arr, int start, int end) {
        // Randomly select a pivot index
        int pivotIdx = (int) Math.random()*(end-start+1) + start;

        // Swap the randomly chosen pivot with the last element
        int temp = arr[pivotIdx];
        arr[pivotIdx] = arr[end];
        arr[end] = temp;

        // Proceed with regular partition logic
        return partition(arr, start, end);
    }

    private static int partition(int[] arr, int start, int end) {
        int pivotIdx = end;
        int idx = start - 1;
        for (int i = start; i < end; i++) {
            if (arr[i] <= arr[pivotIdx]) {
                idx++;
                int temp = arr[i];
                arr[i] = arr[idx];
                arr[idx] = temp;
            }
        }
        idx++;
        int temp = arr[idx];
        arr[idx] = arr[pivotIdx];
        arr[pivotIdx] = temp;

        return idx;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[500];
        for (int i = 0; i < 500; i++) {
            arr[i] = (int) (Math.random() * 1000) + 1;
        }
        System.out.println("Original Array:");
        printArray(arr);

        long startTime = System.nanoTime();
        randomizedQuickSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();

        System.out.println();
        System.out.println("Sorted Array:");
        printArray(arr);

        System.out.println("\nTime taken to sort 500 values using Randomized Quicksort: " + (endTime - startTime) + " nanoseconds");

        startTime = System.nanoTime();
        randomizedQuickSort(arr, 0, arr.length - 1);
        endTime = System.nanoTime();

        System.out.println("\nTime taken to sortSorted Array: " + (endTime - startTime) + " nanoseconds");
    }
}

