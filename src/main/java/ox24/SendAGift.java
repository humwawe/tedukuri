package ox24;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.TreeSet;

public class SendAGift {
    long w;
    int n;
    int[] a;
    Integer[] idx;
    TreeSet<Long> set = new TreeSet<>();
    int k;
    long res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        w = in.nextLong();
        n = in.nextInt();
        a = new int[n];
        idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            idx[i] = i;
        }
        Arrays.sort(idx, (x, y) -> a[y] - a[x]);
        k = n / 2 + 2;
        dfs1(0, 0);
        dfs2(k, 0);
        out.println(res);
    }

    private void dfs2(int i, long s) {
        if (i == n) {
            Long t = set.floor((w - s));
            if (t != null) {
                res = Math.max(res, t + s);
            }
            return;
        }
        if (s + a[idx[i]] <= w) {
            dfs2(i + 1, s + a[idx[i]]);
        }
        dfs2(i + 1, s);
    }

    private void dfs1(int i, long s) {
        if (i == k) {
            set.add(s);
            return;
        }
        if (s + a[idx[i]] <= w) {
            dfs1(i + 1, s + a[idx[i]]);
        }
        dfs1(i + 1, s);
    }
}
