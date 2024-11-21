public class KnapSackDP {
    public static void knapSack(int[][] objects, int W) {
        int N = objects.length;

        // initialize (n+1)*(W+1) array
        int[][] KS = new int[N + 1][W + 1];

        // Use DP to find KS(n,w)
        for (int n = 0; n <= N; n++) {
            for (int w = 0; w <= W; w++) {
                if (n == 0 || w == 0) {
                    KS[n][w] = 0;
                } else {
                    int profit=objects[n-1][0];  //object n is stored at index n-1 in objects
                    int weight= objects[n-1][1];
                    if(weight>w){
                        KS[n][w]=KS[n-1][w];
                    }else{
                        KS[n][w]= Math.max(KS[n-1][w], KS[n-1][w-weight]+profit);
                    }
                }
            }
        }

        //print DP tables
        System.out.println("Dynamic Programming Table: ");
        printTable(KS);

        //Maximim profit gain = KS[N][W];
        System.out.println("Maximum profot gained: "+ KS[N][W]);

        //Find Objects Selected;
        int[] slectedObjects = new int[N];
        int j=W;
        int maxProfit=KS[N][W];
        for(int i=N; i>=1; i--){
            if(KS[i][j]==KS[i-1][j]) slectedObjects[i-1]=0;
            else{
                slectedObjects[i-1]=1;
                maxProfit-=objects[i-1][0];  //object i is stored at index i-1 in objects
                while(KS[i-1][j]!=maxProfit) j--;
            }
        }

        System.out.println("Selected Objects:");
        for(int i: slectedObjects){
            System.out.print(i+", ");
        }
    }

    public static void printTable(int[][] arr){
        for(int i=0; i<arr.length; i++){
            for(int j=0; j<arr[0].length; j++){
                System.out.print(arr[i][j]+", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] objects = { { 1, 1 }, { 4, 3 }, { 5, 4 }, { 7, 5 } }; // index 0 store profit, index 1 stores weight;
        int capacity = 7;

        System.out.println("Objects' weight and their Profit: ");
        System.out.print("Profits: ");
        for(int i=0; i<objects.length; i++) System.out.print(objects[i][0]+", ");
        System.err.println();
        System.out.print("Weights: ");
        for(int i=0; i<objects.length; i++) System.out.print(objects[i][1]+", ");
        System.err.println("Capacity of Bag: "+ capacity);

        knapSack(objects, capacity);
    }
}

