package ox16;

import common.io.InputReader;
import common.io.OutputWriter;

public class PrefixStatistics {
    int N = (int) (1e6 + 5);
    int[][] son = new int[N][26];
    int[] cnt = new int[N];
    int idx = 0;

    void insert(String str) {
        int p = 0;
        for (int i = 0; i < str.length(); i++) {
            int u = str.charAt(i) - 'a';
            if (son[p][u] == 0) {
                son[p][u] = ++idx;
            }
            p = son[p][u];
        }
        cnt[p]++;
    }

    int query(String str) {
        int p = 0;
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            int u = str.charAt(i) - 'a';
            if (son[p][u] == 0) {
                return res;
            }
            p = son[p][u];
            res += cnt[p];
        }
        return res;
    }

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 0; i < n; i++) {
            insert(in.nextString());
        }
        for (int i = 0; i < m; i++) {
            String s = in.nextString();
            out.println(query(s));
        }
    }
}
