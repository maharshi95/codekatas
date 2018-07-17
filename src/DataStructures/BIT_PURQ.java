package DataStructures;

/**
 * BIT_PURQ stands for BIT - Point Update Range Query
 * Creates a data-structure to answer -
 * -- range queries in log(n)
 * -- point updates in log(n)
 * The BIT array has a size one greater than the original array
 * Applicable only for aggregate functions which have subtractive property
 * not for MAX, MIN
 */
public class BIT_PURQ {
    private int[] B;
    private int size;

    public int size() {
        return size - 1;
    }

    public BIT_PURQ(int size) {
        this.size = size + 1;
        B = new int[this.size];
    }

    public BIT_PURQ(int[] array) {
        size = array.length + 1;
        B = new int[size];
        for (int i = 0; i < array.length; i++) {
            point_update(i, B[i]);
        }
    }

    public int prefix_query(int i) {
        i++;
        int sum = 0;
        while (i > 0) {
            sum += this.B[i];
            i -= (i & -i);
        }
        return sum;
    }

    public int range_query(int i, int j) {
        return prefix_query(j) - prefix_query(i - 1);
    }

    public void point_update(int i, int key) {
        i++;
        while (i < this.size) {
            B[i] += key;
            i += (i & -i);
        }
    }

    public int aggregate(int res, int key) {
        return res + key;
    }

    public static void main(String[] args) {
        int[] A = {4, 6, 7, 1, 7, 9, 3};
        BIT_PURQ bit = new BIT_PURQ(A.length);
        for (int i = 0; i < A.length; i++) {
            bit.point_update(i, A[i]);
            System.out.println("Updated i : " + i);
        }
        for (int i = 0; i < A.length; i++) {
            System.out.println(String.format("A[%d]: ", i) + bit.range_query(i, i));
        }
    }
}
