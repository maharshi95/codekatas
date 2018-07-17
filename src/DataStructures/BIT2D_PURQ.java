package DataStructures;

import java.util.Random;

/**
 * Vanilla 2D Bit to solve Point Update Range Query Problems
 */
public class BIT2D {
    private int rows;
    private int cols;
    private BIT_PURQ[] bit2d;

    public BIT2D(int rows, int cols) {
        this.rows = rows + 1;
        this.cols = cols;
        bit2d = new BIT_PURQ[this.rows];
        for (int i = 0; i < this.rows; i++) {
            bit2d[i] = new BIT_PURQ(cols);
        }
    }

    public BIT2D(int[][] matrix) {
        this(matrix.length, matrix[0].length);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                point_update(i, j, matrix[i][j]);
            }
        }
    }

    public void point_update(int i, int j, int v) {
        i++;
        while (i < rows) {
            bit2d[i].pointUpdate(j, v);
            i += (i & -i);
        }
    }

    /**
     * Sum of all values in matrix M[:i, :j]
     */
    public int prefixQuery(int i, int j) {
        i++;
        int sum = 0;
        while (i > 0) {
            sum += bit2d[i].prefixQuery(j);
            i -= (i & -i);
        }
        return sum;
    }

    /**
     * Sum of all values in matrix M[i1:i2, j1:j2] (both inclusive)
     */
    public int rangeQuery(int i1, int i2, int j1, int j2) {
        return prefixQuery(i2, j2)
                - prefixQuery(i2, j1 - 1)
                - prefixQuery(i1 - 1, j2)
                + prefixQuery(i1 - 1, j1 - 1);
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[][] M = new int[10][10];
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                M[i][j] = ((random.nextInt() % 10) + 10) % 10;
            }
        }

        for (int[] aM : M) {
            for (int j = 0; j < M[0].length; j++) {
                System.out.print(aM[j] + " ");
            }
            System.out.println();
        }
        System.out.println();

        BIT2D bit2d = new BIT2D(M);

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[0].length; j++) {
                System.out.print((bit2d.rangeQuery(i, i, j, j) == M[i][j]) + " ");
            }
            System.out.println();
        }
    }
}
