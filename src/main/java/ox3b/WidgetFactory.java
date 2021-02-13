package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

public class WidgetFactory {
    int n, m;
    String[] days = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    int[][] a;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        a = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            int k = in.nextInt();
            String t1 = in.nextString();
            String t2 = in.nextString();
            a[i][n] = getTime(t1, t2) % 7;
            for (int j = 0; j < k; j++) {
                int t = in.nextInt();
                a[i][t - 1]++;
                a[i][t - 1] %= 7;
            }
        }
        int res = gause();
        if (res == 2) {
            out.println("Inconsistent data.");
        } else if (res == 1) {
            out.println("Multiple solutions.");
        } else {
            for (int j = 0; j < n; j++) {
                out.print(a[j][n] + " ");
            }
            out.println();
        }
    }

    private int gause() {
        int r, c;
        for (c = 0, r = 0; c < n && r < m; c++) {
            int t = r;
            for (int i = r; i < m; i++) {
                if (Math.abs(a[i][c]) > Math.abs(a[t][c])) {
                    t = i;
                }
            }
            if (Math.abs(a[t][c]) == 0) {
                continue;
            }
            for (int i = c; i <= n; i++) {
                int tmp = a[t][i];
                a[t][i] = a[r][i];
                a[r][i] = tmp;
            }
            // 用当前行将下面所有的列消成0
            for (int i = r + 1; i < m; i++) {
                int g = gcd(a[r][c], a[i][c]);
                int mulI = a[i][c] / g % 7;
                int mulJ = a[r][c] / g % 7;
                for (int j = c; j <= n; j++) {
                    a[i][j] = a[i][j] * mulJ - a[r][j] * mulI;
                    a[i][j] = (a[i][j] % 7 + 7) % 7;
                }
            }
            r++;
        }
        for (int i = r; i < m; i++) {
            if (Math.abs(a[i][n]) > 0) {
                return 2;
            }
        }
        if (r < n) {
            return 1;
        }
        for (int i = n - 1; i >= 0; --i) {
            for (int j = 3; j <= 9; ++j) {
                int rr = 0;
                for (int k = i + 1; k < n; ++k) {
                    rr += a[k][n] * a[i][k] % 7;
                    rr %= 7;
                }
                rr += j * a[i][i];
                if (rr % 7 == a[i][n]) {
                    a[i][n] = j;
                    break;
                }
            }
        }
        return 0;

    }

    private int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    private int getTime(String t1, String t2) {
        int st = 0, ed = 0;
        for (int i = 0; i < days.length; i++) {
            if (t1.equals(days[i])) {
                st = i;
            }
            if (t2.equals(days[i])) {
                ed = i;
            }
        }
        if (ed - st >= 0) {
            return ed - st + 1;
        } else {
            return ed + 8 - st;
        }
    }
}
