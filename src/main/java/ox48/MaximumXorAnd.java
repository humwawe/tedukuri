package ox48;

import common.io.InputReader;
import common.io.OutputWriter;

public class MaximumXorAnd {
    int idx = 1;
    int N = 600010;
    int M = N * 50;
    int[] trie = new int[M];
    int[] maxId = new int[M];
    int[] s;
    int[] root;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        root = new int[n + m];
        s = new int[n + m];
        int cur = 0;
        for (int i = 0; i < n; ++i) {
            int x = in.nextInt();
            s[cur] = cur >= 1 ? (s[cur - 1] ^ x) : x;
            insert(s[cur], cur - 1, cur);
            cur++;
        }
        for (int i = 0; i < m; ++i) {
            String s = in.next();
            if (s.equals("A")) {
                int x = in.nextInt();
                this.s[cur] = this.s[cur - 1] ^ x;
                insert(this.s[cur], cur - 1, cur);
                cur++;

            } else {
                int l = in.nextInt() - 1;
                int r = in.nextInt() - 1;
                int v = in.nextInt();
                int val = v ^ this.s[cur - 1];
                out.println(ask(r - 1, val, l - 1));
            }
        }

    }

    void insert(int x, int v1, int v2) {
        root[v2] = idx;
        idx += 2;
        int p = root[v2];
        int cur = (v1 != -1 ? root[v1] : 0);
        for (int w = 23; w >= 0; --w) {
            int g = ((x & (1 << w)) != 0) ? 1 : 0;
            if (cur != 0) {
                trie[p + (g ^ 1)] = trie[cur + (g ^ 1)];
            }
            trie[p + g] = idx;
            idx += 2;
            p = trie[p + g];
            maxId[p >> 1] = v2;
            if (cur != 0) {
                cur = trie[cur + g];
            }
        }
    }

    int ask(int v1, int val, int l) {
        if (v1 == -1) {
            return val;
        }
        int p = root[v1];
        int ans = 0;

        for (int w = 23; w >= 0; --w) {
            int g = ((val & (1 << w)) != 0) ? 0 : 1;
            if (trie[p + g] != 0 && maxId[trie[p + g] >> 1] >= l) {
                p = trie[p + g];
                ans |= 1 << w;
            } else {
                p = trie[p + 1 - g];
            }
        }
        return ans;
    }
}
