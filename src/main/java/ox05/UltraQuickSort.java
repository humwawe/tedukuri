package ox05;

import common.io.InputReader;
import common.io.OutputWriter;

public class UltraQuickSort {
    int n;
    long res;
    int[] b;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        while (true) {
            res = 0;
            n = in.nextInt();
            if (n == 0) {
                return;
            }
            int[] a = new int[n];
            b = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            mergeSort(a, 0, n - 1);
            out.println(res);
        }
    }

    private void mergeSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = l + r >> 1;
        mergeSort(a, l, mid);
        mergeSort(a, mid + 1, r);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (j > r || i <= mid && a[i] < a[j]) {
                b[k] = a[i++];
            } else {
                b[k] = a[j++];
                res += mid - i + 1;
            }
        }
        for (int k = l; k <= r; k++) {
            a[k] = b[k];
        }
    }
}
