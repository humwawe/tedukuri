package ox48;

import common.io.InputReader;
import common.io.OutputWriter;

public class MaximumXorAnd {
    int N = 600010;
    int M = N * 25;
    int[] s = new int[N];
    int[][] tr = new int[2][M];
    int[] root = new int[N];
    int[] maxId = new int[M];
    int idx;
    int n;
    int m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        maxId[0] = -1;
        root[0] = ++idx;
        insert(0, 23, 0, root[0]);

        for (int i = 1; i <= n; i++) {
            int x = in.nextInt();
            s[i] = s[i - 1] ^ x;
            root[i] = ++idx;
            insert(i, 23, root[i - 1], root[i]);
        }
        for (int i = 0; i < m; i++) {
            char c = in.nextCharacter();
            if (c == 'A') {
                int x = in.nextInt();
                n++;
                s[n] = s[n - 1] ^ x;
                root[n] = ++idx;
                insert(n, 23, root[n - 1], root[n]);
            } else {
                int l = in.nextInt();
                int r = in.nextInt();
                int x = in.nextInt();
                out.println(ask(root[r - 1], s[n] ^ x, l - 1));
            }
        }
    }

    private int ask(int root, int val, int l) {
        int p = root;
        for (int i = 23; i >= 0; i--) {
            int v = val >> i & 1;
            if (maxId[tr[v ^ 1][p]] >= l) {
                p = tr[v ^ 1][p];
            } else {
                p = tr[v][p];
            }
        }
        return val ^ s[maxId[p]];
    }

    private void insert(int i, int k, int p, int q) {
        if (k < 0) {
            maxId[q] = i;
            return;
        }
        int v = s[i] >> k & 1;
        if (p != 0) {
            tr[v ^ 1][q] = tr[v ^ 1][p];
        }
        tr[v][q] = ++idx;
        insert(i, k - 1, tr[v][p], tr[v][q]);
        maxId[q] = Math.max(maxId[tr[0][q]], maxId[tr[1][q]]);
    }
}
