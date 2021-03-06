package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class DetainingCriminals {
    int n, m;
    int[] p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        p = new int[2 * n + 1];
        for (int i = 1; i < 2 * n; i++) {
            p[i] = i;
        }
        int[][] c = new int[m][3];
        for (int i = 0; i < m; i++) {
            c[i][0] = in.nextInt();
            c[i][1] = in.nextInt();
            c[i][2] = in.nextInt();
        }
        Arrays.sort(c, (x, y) -> y[2] - x[2]);
        for (int i = 0; i < m; i++) {
            int x = c[i][0];
            int y = c[i][1];
            int xP = c[i][0] + n;
            int yP = c[i][1] + n;
            if (find(x) == find(y) || find(xP) == find(yP)) {
                out.println(c[i][2]);
                return;
            }
            p[find(x)] = find(yP);
            p[find(y)] = find(xP);
        }
        out.println(0);
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
