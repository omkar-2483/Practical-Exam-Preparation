public class QuickSortAssignment {
    public static void QuickSort(int[] arr, int start, int end) {
        if (start >= end)
            return;

        int pivotIdx = partition(arr, start, end);
        QuickSort(arr, start, pivotIdx - 1);
        QuickSort(arr, pivotIdx + 1, end);
    }

    private static int partition(int[] arr, int start, int end) {
        int pivotIdx = end;
        int idx=start-1;
        for(int i=start; i<end; i++){
            if(arr[i]<= arr[pivotIdx]){
                idx++;
                int temp= arr[i];
                arr[i]=arr[idx];
                arr[idx]=temp;
            }
        }
        idx++;
        int temp=arr[idx];
        arr[idx]=arr[pivotIdx];
        arr[pivotIdx]=temp;

        return idx;
    }

    public static void printArray(int[] arr){
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]+" ");
        }
    }

    public static void main(String[] args) {
        int[] arr= new int[500];
        for(int i=0; i<500; i++){
            arr[i]=(int)(Math.random()*1000)+1;
        }
        System.out.println("original Array:");
        printArray(arr);

        long startTime = System.nanoTime();
        QuickSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();

        System.out.println();
        System.out.println("Sorted array: ");
        printArray(arr);

        System.out.println("Time taken to sort 500 values using Quicksort: " + (endTime - startTime) + " nanoseconds");
        
        startTime = System.nanoTime();
        QuickSort(arr, 0, arr.length - 1);
        endTime = System.nanoTime();

        System.out.println("Time taken to sort already sorted array: " + (endTime - startTime) + " nanoseconds");
    }
}