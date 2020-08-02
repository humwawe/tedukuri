package ox05;

import common.io.InputReader;
import common.io.OutputWriter;

public class OddNumber {
    int[] tmp;
    long res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n * n - 1];
        int[] b = new int[n * n - 1];
        tmp = new int[n * n - 1];
        int idx = 0;
        for (int i = 0; i < n * n; i++) {
            int x = in.nextInt();
            if (x == 0) {
                continue;
            }
            a[idx++] = x;
        }
        idx = 0;
        for (int i = 0; i < n * n; i++) {
            int x = in.nextInt();
            if (x == 0) {
                continue;
            }
            b[idx++] = x;
        }
        res = 0;
        mergeSort(a, 0, n * n - 2);
        long cnt1 = res;
        res = 0;
        mergeSort(b, 0, n * n - 2);
        long cnt2 = res;
        if (cnt1 % 2 == cnt2 % 2) {
            out.println("TAK");
        } else {
            out.println("NIE");
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
                tmp[k] = a[i++];
            } else {
                tmp[k] = a[j++];
                res += mid - i + 1;
            }
        }
        for (int k = l; k <= r; k++) {
            a[k] = tmp[k];
        }
    }
}
