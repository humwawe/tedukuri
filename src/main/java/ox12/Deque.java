package ox12;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class Deque {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = in.nextIntArray(n);
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            idx[i] = i;
        }
        Arrays.sort(idx, Comparator.comparingInt(x -> a[x]));
        // 找单谷，默认为降
        boolean f = false;
        int inf = (int) 1e9;
        int curIdx = inf;
        int res = 1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && a[idx[i]] == a[idx[j]]) {
                j++;
            }
            j--;
            int minIdx = idx[i];
            int maxIdx = idx[j];
            if (!f) {
                if (maxIdx > curIdx) {
                    f = true;
                    curIdx = maxIdx;
                } else {
                    curIdx = minIdx;
                }
            } else {
                if (minIdx < curIdx) {
                    f = false;
                    curIdx = minIdx;
                    res++;
                } else {
                    curIdx = maxIdx;
                }
            }
            i = j;
        }
        out.println(res);

    }
}
