package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class CandyDelivery {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        long a[] = new long[n];
        long sum = 0;
        for (int i = 0; i < n; i++) {
            a[i] = in.nextLong();
            sum += a[i];
        }
        long avg = sum / n;
        long[] preSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            a[i] -= avg;
            preSum[i + 1] = preSum[i] + a[i];
        }
        Arrays.sort(preSum, 1, n + 1);
        int mid = (n + 1) / 2;
        long res = 0;
        for (int i = 1; i <= n; i++) {
            res += Math.abs(preSum[i] - preSum[mid]);
        }
        out.println(res);
    }
}
