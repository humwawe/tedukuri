package ox06;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class GeniusAcm {
    int[] a;
    long t;
    int m;
    long[] tmp1;
    long[] tmp2;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        m = in.nextInt();
        t = in.nextLong();
        a = in.nextIntArray(n);
        tmp1 = new long[n];
        tmp2 = new long[n];
        int res = 0;
        int l = 0;
        int r = 0;
        // [l,r)
        while (r < n) {
            int p = 1;
            while (p != 0) {
                if (r + p <= n && helper(l, r, r + p)) {
                    r += p;
                    p *= 2;
                    if (r >= n) {
                        break;
                    }
                    for (int i = l; i < r; i++) {
                        tmp1[i] = tmp2[i - l];
                    }
                } else {
                    p /= 2;
                }
            }
            l = r;
            res++;
        }
        out.println(res);
    }

    // 归并
    private boolean helper(int l, int mid, int r) {
        for (int i = mid; i < r; i++) {
            tmp1[i] = a[i];
        }
        Arrays.sort(tmp1, mid, r);
        int i = l;
        int j = mid;
        int k = 0;
        while (i < mid && j < r) {
            if (tmp1[i] < tmp1[j]) {
                tmp2[k++] = tmp1[i++];
            } else {
                tmp2[k++] = tmp1[j++];
            }
        }
        while (i < mid) {
            tmp2[k++] = tmp1[i++];
        }
        while (j < r) {
            tmp2[k++] = tmp1[j++];
        }
        long sum = 0;
        i = 0;
        j = k - 1;
        while (i < m && i < j) {
            sum += (tmp2[j] - tmp2[i]) * (tmp2[j] - tmp2[i]);
            i++;
            j--;
        }
        return sum <= t;
    }
}
