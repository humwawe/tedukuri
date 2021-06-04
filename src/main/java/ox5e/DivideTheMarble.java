package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class DivideTheMarble {
    int N = 6;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int[] a = in.nextIntArray(N);
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += a[i] * (i + 1);
        }
        if (sum == 0) {
            return;
        }
        if (sum % 2 != 0) {
            out.println("Can't");
            return;
        }
        boolean[] dp = new boolean[sum + 1];

        dp[0] = true;
        int[] used = new int[sum + 1];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(used, 0);
            for (int j = i; j <= sum; j++) {
                if (!dp[j] && dp[j - i] && used[j - i] < a[i - 1]) {
                    dp[j] = true;
                    used[j] = used[j - i] + 1;
                }
            }
        }
        if (dp[sum / 2]) {
            out.println("Can");
        } else {
            out.println("Can't");
        }
    }
}
