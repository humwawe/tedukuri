package ox23;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Sticks {
    int[] a;
    boolean[] vis;
    int len;
    int cnt = 0;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        while (true) {
            n = in.nextInt();
            if (n == 0) {
                break;
            }
            int sum = 0;
            int max = 0;
            a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
                sum += a[i];
                max = Math.max(max, a[i]);
            }
            Arrays.sort(a);
            for (len = max; len <= sum; len++) {
                if (sum % len != 0) {
                    continue;
                }
                cnt = sum / len;
                vis = new boolean[n];
                if (dfs(0, 0, n - 1)) {
                    out.println(len);
                    break;
                }
            }
        }

    }

    private boolean dfs(int stick, int cab, int start) {
        if (stick == cnt) {
            return true;
        }
        if (cab == len) {
            return dfs(stick + 1, 0, n - 1);
        }
        int last = -1;
        for (int i = start; i >= 0; i--) {
            if (!vis[i] && cab + a[i] <= len && last != a[i]) {
                vis[i] = true;
                if (dfs(stick, cab + a[i], i)) {
                    return true;
                }
                last = a[i];
                vis[i] = false;
                if (cab == 0 || cab + a[i] == len) {
                    return false;
                }
            }
        }
        return false;
    }
}
