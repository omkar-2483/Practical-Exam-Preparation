public class MergeSortAssignment {

    public static void mergeSort(int[] arr, int start, int end){
        if(start >= end) return;

        int mid = (start+end)/2;
        mergeSort(arr, start, mid);
        mergeSort(arr, mid+1, end);
        merge(arr, start, mid, end);
    }
    private static void merge(int[] arr, int start, int mid, int end){
        int[] left = new int[mid-start+1];
        int[] right = new int[end-mid];

        for(int i=start, k=0; i<=mid; i++, k++){
            left[k] = arr[i];
        }
        for(int i=mid+1, k=0; i<=end; i++, k++){
            right[k] = arr[i];
        }

        int i=0;
        int j=0;
        int k=start;

        while(i<left.length && j<right.length){
            if(left[i]<=right[j]){
                arr[k]=left[i];
                i++;
            }else{
                arr[k]=right[j];
                j++;
            }
            k++;
        }
        while(i<left.length){
            arr[k]=left[i];
            i++;
            k++;
        }
        while(j<right.length){
            arr[k]=right[j];
            j++;
            k++;
        }
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
        mergeSort(arr, 0, arr.length - 1);
        long endTime = System.nanoTime();

        System.out.println();
        System.out.println("Sorted array: ");
        printArray(arr);

        System.out.println("Time taken to sort 500 values using MergeSort: " + (endTime - startTime) + " nanoseconds");
        
        startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        endTime = System.nanoTime();

        System.out.println("Time taken to sort sorted array: " + (endTime - startTime) + " nanoseconds");
    }
}
