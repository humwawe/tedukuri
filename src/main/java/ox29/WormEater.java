package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class WormEater {
    int N = 30;

    int n;
    String[] eq = new String[3];
    int[] q = new int[N];
    int[] path = new int[N];
    boolean[] st = new boolean[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        for (int i = 0; i < 3; i++) {
            eq[i] = in.nextString();
        }

        for (int i = n - 1, k = 0; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                int c = eq[j].charAt(i) - 'A';
                if (!st[c]) {
                    st[c] = true;
                    q[k++] = c;
                }
            }
        }
        st = new boolean[N];
        Arrays.fill(path, -1);
        dfs(0);
        for (int i = 0; i < n; i++) {
            out.print(path[i] + " ");
        }
    }

    private boolean dfs(int u) {
        if (u == n) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            if (!st[i]) {
                st[i] = true;
                path[q[u]] = i;
                if (check() && dfs(u + 1)) {
                    return true;
                }
                st[i] = false;
                path[q[u]] = -1;
            }
        }
        return false;
    }

    private boolean check() {
        int t = 0;
        for (int i = n - 1; i >= 0; i--) {
            int a = path[eq[0].charAt(i) - 'A'];
            int b = path[eq[1].charAt(i) - 'A'];
            int c = path[eq[2].charAt(i) - 'A'];
            if (a != -1 && b != -1 && c != -1) {
                if (t == -1) {
                    if ((a + b) % n != c && (a + b + 1) % n != c) {
                        return false;
                    }
                    // 最高位有进位
                    if (i == 0 && a + b >= n) {
                        return false;
                    }
                } else {
                    if ((a + b + t) % n != c) {
                        return false;
                    }
                    if (i == 0 && a + b + t >= n) {
                        return false;
                    }
                    t = (a + b + t) / n;
                }
            } else {
                t = -1;
            }
        }
        return true;
    }

}
