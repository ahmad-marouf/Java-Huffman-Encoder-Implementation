package Part_1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class SelectionAlgorithms {

    public static void main(String[] args) {
        final int n = 5;
        int median =  (n+1) / 2;
        System.out.println("Median: " + median);
//        if ((n%2) == 1) {
//            median = (n+1) / 2;
//        } else {
//            median = n/2 + 1;
//        }

        Random rand = new Random();

        // Initialize array of size n
        int[] A = new int[n];

        System.out.println("Generating array of size n = " + n);
        // Generate array of n random elements
        String s = "";
        for (int i = 0; i < n; i++) {
            A[i] = rand.nextInt(1000);
            s += A[i] + ",";
        }
        System.out.println(s);
        //if (true) return;

        // divide and conquer time
        long startTime = System.nanoTime();
        System.out.println("\nRunning divide and conquer selection algorithm");
        int x = DivideAndConquer.randomizedSelect(A, 0, n - 1, median);
        System.out.println("answer = " + x);
        System.out.println("Time taken = " + (System.nanoTime() - startTime) / 1000000 + " ms");

        // Median of medians time
        startTime = System.nanoTime();
        System.out.println("\nRunning median of medians selection algorithm");
        ArrayList<Integer> AConverted = new ArrayList<>();
        for (int i : A)
        {
            AConverted.add(i);
        }
        /*ArrayList AConverted = new ArrayList<Integer>(){
                {
                    add(2);
                    add(3);
                    add(5);
                    add(4);
                    add(1);
                    add(12);
                    add(11);
                    add(13);
                    add(16);
                    add(7);
                    add(8);
                    add(6);
                    add(10);
                    add(9);
                    add(17);
                    add(15);
                    add(19);
                    add(20);
                    add(18);
                    add(23);
                    add(21);
                    add(22);
                    add(25);
                    add(24);
                    add(14);
            }
        };*/
        MedianofMedians.selectionByMedianOfMedians(AConverted, 4);
        //System.out.println("answer = " + y);
        System.out.println("Time taken = " + (System.nanoTime() - startTime) / 1000000 + " ms");

        // naive method time
        System.out.println("\nRunning naive selection method algorithm");
        startTime = System.nanoTime();
        int z = NaiveMethod.ithSmallest(A, median);
        Instant end3 = Instant.now();
        System.out.println("answer = " + z);
        System.out.println("Time taken = " + (System.nanoTime() - startTime) / 1000000 + " ms");

    }

}


class MedianofMedians {
    static int findMedian(ArrayList array){
        int median;
        if (array.size() == 0) return 0;
//        if (array.size() == 1) return (int) array.get(0);
//        System.out.println(((array.size()+1) / 2) + " >> " + array.size());
//        if (array.size() % 2 == 0) {
            median = (int) array.get( ((array.size() ) / 2)  );
//        } else {
//            median = (int) array.get( ((array.size() ) / 2) - 1);
//        }

//        System.out.println(median);
        return median;
    }

    private static int findMedianOfMedians(ArrayList<ArrayList<Integer>> values){
        ArrayList medians = new ArrayList<Integer>();
        for (int i = 0; i < values.size(); i++) {
            int m = findMedian(values.get(i));
            medians.add(m);

        }
        return findMedian(medians);
    }

    public static void selectionByMedianOfMedians(ArrayList values, int k){
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

        int count = 0;
        while (count != values.size()) {
            int countRow = 0;
            ArrayList<Integer> list = new ArrayList<>();

            while ((countRow < k) && (count < values.size())) {
                list.add((Integer) values.get(count));
                count++;
                countRow++;
            }
            lists.add(list);
        }
        int m = findMedianOfMedians(lists);

        ArrayList L1 = new ArrayList();
        ArrayList L2 = new ArrayList();

        for (int i = 0; i < lists.size(); i++) {
            for (int j = 0; j < lists.get(i).size(); j++) {
                int currentValue = lists.get(i).get(j);
                if (currentValue > m) {
                    L1.add(currentValue);
                }else if (currentValue < m){
                    L2.add(currentValue);
                }
            }
        }

        if ((k - 1) == L1.size()) {
            System.out.println("Answer : "+ m);
            //return m;
        }else if (k <= L1.size()) {
            selectionByMedianOfMedians(L1, k);
            //return selectionByMedianOfMedians(L1, k);
        }else if (k > (L1.size() + 1)){
            selectionByMedianOfMedians(L2, k-((int)L1.size())-1);
            //return selectionByMedianOfMedians(L2, k-((int)L1.size())-1);
        }
        //return 0;
    }
}