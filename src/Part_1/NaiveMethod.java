package Part_1;

import java.util.Arrays;

public class NaiveMethod {
    public static int ithSmallest(int[] A, int i) {
        Arrays.sort(A);
        String s = "";
        for (int j = 0; j < A.length; j++) {
            s += A[j] + ",";
        }
        System.out.println(s);
        return A[i - 1];
    }
}
