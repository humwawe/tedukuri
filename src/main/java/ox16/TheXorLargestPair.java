package ox16;

import common.io.InputReader;
import common.io.OutputWriter;

public class TheXorLargestPair {
    int N = (int) (32 * 1e5 + 5);
    int[][] son = new int[N][2];
    int idx = 0;

    void insert(int num) {
        int p = 0;
        for (int i = 31; i >= 0; i--) {
            int t = (num >> i) & 1;
            if (son[p][t] == 0) {
                son[p][t] = ++idx;
            }
            p = son[p][t];
        }
    }

    int query(int num) {
        int p = 0;
        int res = 0;
        for (int i = 31; i >= 0; i--) {
            int t = (num >> i) & 1;
            if (son[p][t ^ 1] != 0) {
                p = son[p][t ^ 1];
                res += 1 << i;
            } else {
                p = son[p][t];
            }
        }
        return res;
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            int t = in.nextInt();
            insert(t);
            res = Math.max(res, query(t));
        }
        out.println(res);
    }
}
