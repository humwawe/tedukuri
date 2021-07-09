package ox61;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CowRelays {
    int k, m, s, e;
    int inf = 0x3f3f3f3f;
    int N = 210;
    int[][] g = new int[N][N];
    int[][] res = new int[N][N];
    int[][] tmp = new int[N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        k = in.nextInt();
        m = in.nextInt();
        s = mapping(in.nextInt());
        e = mapping(in.nextInt());
        for (int i = 0; i < N; i++) {
            Arrays.fill(g[i], inf);
        }
        for (int i = 0; i < m; i++) {
            int a = mapping(in.nextInt());
            int b = mapping(in.nextInt());
            int c = mapping(in.nextInt());
            g[a][b] = g[b][a] = Math.min(g[a][b], c);
        }

        for (int i = 0; i < cnt; i++) {
            Arrays.fill(res[i], inf);
            res[i][i] = 0;
        }

        while (k > 0) {
            if ((k & 1) == 1) {
                mul(res, g);
            }
            mul(g, g);
            k >>= 1;
        }
        out.println(res[s][e]);
    }

    private void mul(int[][] a, int[][] b) {
        for (int i = 0; i < cnt; i++) {
            Arrays.fill(tmp[i], inf);
        }
        for (int l = 0; l < cnt; l++) {
            for (int i = 0; i < cnt; i++) {
                for (int j = 0; j < cnt; j++) {
                    tmp[i][j] = Math.min(tmp[i][j], a[i][k] + b[k][j]);
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.arraycopy(tmp[i], 0, a[i], 0, a[i].length);
        }
    }

    Map<Integer, Integer> map = new HashMap<>();
    int cnt = 0;

    int mapping(int x) {
        if (!map.containsKey(x)) {
            map.put(x, cnt++);
        }
        return map.get(x);
    }
}
