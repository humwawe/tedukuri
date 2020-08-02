package ox05;

import common.io.InputReader;
import common.io.OutputWriter;
import jdk.nashorn.internal.ir.IfNode;

import java.util.Arrays;

public class MagpieFestival {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int t = in.nextInt();
        boolean f1 = t % n == 0;
        boolean f2 = t % m == 0;
        int[] row = new int[n];
        int[] col = new int[m];
        for (int i = 0; i < t; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            row[x]++;
            col[y]++;
        }
        long res = 0;
        if (f1) {
            int tmp = t / n;
            row[0] -= tmp;
            for (int i = 1; i < n; i++) {
                row[i] += row[i - 1] - tmp;
            }
            Arrays.sort(row);
            int mid = n / 2;
            for (int i = 0; i < n; i++) {
                res += Math.abs(row[i] - row[mid]);
            }
        }
        if (f2) {
            int tmp = t / m;
            col[0] -= tmp;
            for (int i = 1; i < m; i++) {
                col[i] += col[i - 1] - tmp;
            }
            Arrays.sort(col);
            int mid = m / 2;
            for (int i = 0; i < m; i++) {
                res += Math.abs(col[i] - col[mid]);
            }
        }
        if (f1 && f2) {
            out.println("both " + res);
        } else if (f1) {
            out.println("row " + res);
        } else if (f2) {
            out.println("column " + res);
        } else {
            out.println("impossible");
        }
    }

}
