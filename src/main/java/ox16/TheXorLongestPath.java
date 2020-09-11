package ox16;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TheXorLongestPath {
    int N = (int) (1e5 + 5);
    int[] h = new int[N];
    int[] e = new int[N * 2];
    int[] ne = new int[N * 2];
    int[] w = new int[N * 2];
    int idx = 0;
    int[] a = new int[N];
    int[][] son = new int[N * 32][2];
    int idx2 = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        Arrays.fill(h, -1);
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int z = in.nextInt();
            add(x, y, z);
            add(y, x, z);
        }

        dfs(0);
    }

    private void dfs(int i) {

    }

    private void add(int x, int y, int z) {
        e[idx] = y;
        w[idx] = z;
        ne[idx] = h[x];
        h[x] = idx++;
    }
}
