package Part_1;

import java.io.*;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

public class DivideAndConquer {

    private static int[] swap(int []A, int i, int j) {

        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
        return A;

    }

    private static int getRandomNumber(int min, int max) {

        return (int) ((Math.random() * (max - min)) + min);

    }

    private static int partition(int A[], int p, int r) {

        int x = A[r];

        int i = p - 1;

        for (int j = p; j < r; j++) {
            if (A[j] <= x) {
                i = i + 1;
                A = swap(A, i, j);
            }
        }

        A = swap(A, i + 1, r);

        return i + 1;

    }

    private static int randomizedPartition(int A[], int p, int r) {

        int i = getRandomNumber(p, r);

        A = swap(A, r, i);

        return partition(A, p, r);

    }

    public static int randomizedSelect(int A[], int p, int r, int i) {

        if (p == r) {
            return A[p];
        }

        int q = randomizedPartition(A, p, r);
        int k = q - p + 1;

        if (i == k) {
            return A[q];
        }
        else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        }
        else {
            return randomizedSelect(A, q + 1, r, i - k);
        }

    }
}
