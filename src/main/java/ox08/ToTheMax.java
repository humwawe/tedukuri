package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

public class ToTheMax {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = in.nextInt();
            }
        }
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int[] b = new int[n];
            for (int j = i; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    b[k] += a[j][k];
                }
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += b[k];
                    res = Math.max(res, sum);
                    if (sum < 0) {
                        sum = 0;
                    }
                }
            }
        }
        out.println(res);
    }
}
