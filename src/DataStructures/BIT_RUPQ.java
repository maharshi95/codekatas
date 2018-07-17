package DataStructures;

/**
 * BIT_RUPQ stands for BIT - Range Update Point Query
 * Wraps around vanilla BIT (BIT - Point Update Range Query) to answer:
 * -- Range Updates in log(n)
 * -- Point Queries in log(n)
 * Applicable only for aggregate functions which have subtractive property
 * not for MAX, MIN
 */
public class BIT_RUPQ {
    private BIT_PURQ bit;

    public int size() {
        return bit.size();
    }

    public BIT_RUPQ(int size) {
        this.bit = new BIT_PURQ(size);
    }

    public BIT_RUPQ(int[] array) {
        int[] diff_array = new int[array.length];
        diff_array[0] = array[0];
        for (int i = 1; i < array.length; i++) {
            diff_array[i] = array[i] - array[i - 1];
        }
        bit = new BIT_PURQ(diff_array);
    }

    public int point_query(int i) {
        return bit.prefix_query(i);
    }

    public void suffix_update(int i, int v) {
        bit.point_update(i, v);
    }

    public void range_update(int i, int j, int v) {
        bit.point_update(i, v);
        bit.point_update(j + 1, v);
    }

    public static void main(String[] args) {
        int[] A = {4, 6, 7, 1, 7, 9, 3};
        BIT_RUPQ bit = new BIT_RUPQ(A);
        for (int i = 0; i < A.length; i++) {
            System.out.println(String.format("A[%d]: ", i) + bit.point_query(i));
        }
    }
}
