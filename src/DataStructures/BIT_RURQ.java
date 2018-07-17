package DataStructures;

/**
 * BIT_RURQ stands for BIT - Range Update Range Query
 * Uses two BIT_RUPQ (BIT - Range Update Point Query) to answer:
 * -- Range Updates in log(n)
 * -- Range Queries in log(n)
 * Applicable only for aggregate functions which have subtractive property
 * not for MAX, MIN
 *
 * prefix(i) = B1(i) * i - B2(i)
 * Explained at https://www.geeksforgeeks.org/binary-indexed-tree-range-update-range-queries/
 *
 */
public class BIT_RURQ {
    private BIT_RUPQ B1;
    private BIT_RUPQ B2;

    public BIT_RURQ(int size) {
        B1 = new BIT_RUPQ(size);
        B2 = new BIT_RUPQ(size);
    }

    public BIT_RURQ(int[] array) {
        this(array.length);
        for (int i = 0; i < array.length; i++) {
            range_update(i, i, array[i]);
        }
    }

    public int prefix_query(int i) {
        return B1.point_query(i) * (i + 1) - B2.point_query(i);
    }

    public void suffix_update(int i, int v) {
        B1.suffix_update(i, v);
        B2.suffix_update(i, i * v);
    }

    public int range_query(int i, int j) {
        return prefix_query(j) - prefix_query(i - 1);
    }

    public void range_update(int i, int j, int v) {
        suffix_update(i, v);
        suffix_update(j + 1, -v);
    }

    public static void main(String[] args) {
        int[] A = new int[]{4, 5, 6, 3, 2, 4, 7, 8};
        BIT_RURQ bit = new BIT_RURQ(A);
        for (int i = 0; i < A.length; i++) {
            System.out.println(bit.range_query(i, i));
            assert bit.range_query(i, i) == A[i];
        }
    }
}
