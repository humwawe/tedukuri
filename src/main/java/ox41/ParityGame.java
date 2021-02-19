package ox41;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class ParityGame {
    int[] p;
    int n;
    int[] o;
    int[] b;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int len = in.nextInt();
        int m = in.nextInt();
        int[][] a = new int[m][2];
        b = new int[m * 2];
        String[] c = new String[m];
        o = new int[m * 2];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
            c[i] = in.nextString();
            o[idx++] = a[i][0] - 1;
            o[idx++] = a[i][1];
        }
        discrete();
        p = new int[2 * n];
        for (int i = 0; i < n * 2; i++) {
            p[i] = i;
        }
        for (int i = 0; i < m; i++) {
            int x = get(a[i][0] - 1);
            int xOdd = x;
            int xEven = x + n;
            int y = get(a[i][1]);
            int yOdd = y;
            int yEven = y + n;
            if ("even".equals(c[i])) {
                if (find(xOdd) == find(yEven)) {
                    out.println(i);
                    return;
                }
                p[find(xOdd)] = find(yOdd);
                p[find(xEven)] = find(yEven);
            } else {
                if (find(xOdd) == find(yOdd)) {
                    out.println(i);
                    return;
                }
                p[find(xOdd)] = find(yEven);
                p[find(xEven)] = find(yOdd);
            }
        }
        out.println(m);
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    void discrete() {
        Arrays.sort(o);
        for (int i = 0; i < o.length; i++) {
            if (i == 0 || o[i] != o[i - 1]) {
                b[n++] = o[i];
            }
        }
    }

    int get(int x) {
        return Arrays.binarySearch(b, 0, n, x);
    }
}
