package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Rochambeau {
    int n, m;
    int[] p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        p = new int[3 * n];
        m = in.nextInt();
        int[][] q = new int[m][3];
        for (int i = 0; i < m; i++) {
            String s = in.nextString();
            String[] split = s.split("[<=>]");
            q[i][0] = Integer.parseInt(split[0]);
            if (s.contains("=")) {
                q[i][1] = 0;
            } else if (s.contains("<")) {
                q[i][1] = 1;
            } else {
                q[i][1] = 2;
            }
            q[i][2] = Integer.parseInt(split[1]);
        }
        int[] last = new int[n];
        Arrays.fill(last, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3 * n; j++) {
                p[j] = j;
            }
            for (int j = 0; j < m; j++) {
                if (q[j][0] == i || q[j][2] == i) {
                    continue;
                }
                if (conflict(q[j][0], q[j][1], q[j][2])) {
                    last[i] = j;
                    break;
                }
            }
        }
        int cnt = 0;
        int id = 1;
        int times = 0;
        for (int i = 0; i < n; i++) {
            if (last[i] == -1) {
                cnt++;
                id = i;
            }
            times = Math.max(times, last[i] + 1);
        }
        if (cnt > 1) {
            out.println("Can not determine");
        } else if (cnt == 1) {
            out.printf("Player %d can be determined to be the judge after %d lines\n", id, times);
        } else {
            out.println("Impossible");
        }

    }

    private boolean conflict(int a, int t, int b) {
        if (t == 0) {
            if (find(a) == find(b + n) || find(a) == find(b + 2 * n)) {
                return true;
            }
            p[find(a)] = find(b);
            p[find(a + n)] = find(b + n);
            p[find(a + 2 * n)] = find(b + 2 * n);
        } else if (t == 1) {
            if (find(a) == find(b) || find(a) == find(b + n)) {
                return true;
            }
            p[find(a + 2 * n)] = find(b + n);
            p[find(a)] = find(b + 2 * n);
            p[find(a + n)] = find(b);
        } else {
            if (find(a) == find(b) || find(a) == find(b + 2 * n)) {
                return true;
            }
            p[find(a + 2 * n)] = find(b);
            p[find(a + n)] = find(b + 2 * n);
            p[find(a)] = find(b + n);
        }
        return false;
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
