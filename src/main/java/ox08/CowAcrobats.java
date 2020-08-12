package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class CowAcrobats {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] += in.nextInt();
            b[i] += in.nextInt();
            idx[i] = i;
        }
        Arrays.sort(idx, (x, y) -> a[x] + b[x] - a[y] - b[y]);
        long res = -Long.MAX_VALUE;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, sum - b[idx[i]]);
            sum += a[idx[i]];
        }
        out.println(res);
    }
}
