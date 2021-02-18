package ox41;

import common.io.InputReader;
import common.io.OutputWriter;

public class GalacticHeroes {
    int N = 30005;
    int[] p = new int[N];
    int[] d = new int[N];
    int[] size = new int[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        for (int i = 0; i < N; i++) {
            p[i] = i;
            size[i] = 1;
        }
        int t = in.nextInt();
        for (int i = 0; i < t; i++) {
            char c = in.nextCharacter();
            int a = in.nextInt();
            int b = in.nextInt();
            if (c == 'M') {
                union(a, b);
            } else {
                if (find(a) == find(b)) {
                    out.println(Math.max(0, Math.abs(d[a] - d[b]) - 1));
                } else {
                    out.println(-1);
                }
            }
        }

    }

    int find(int x) {
        if (p[x] != x) {
            int root = find(p[x]);
            d[x] += d[p[x]];
            p[x] = root;
        }
        return p[x];
    }

    void union(int a, int b) {
        int x = find(a);
        int y = find(b);
        if (x == y) {
            return;
        }
        p[x] = y;
        d[x] = size[y];
        size[y] += size[x];
    }
}
