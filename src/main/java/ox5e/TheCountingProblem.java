package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class TheCountingProblem {
    int a, b;
    int N = 12;
    int[] num = new int[N];
    Long[][] memo = new Long[N][N];
    int x;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextInt();
        b = in.nextInt();
        if (a == 0 && b == 0) {
            return;
        }
        if (a > b) {
            int tmp = b;
            b = a;
            a = tmp;
        }

        for (int i = 0; i <= 9; i++) {
            out.printf("%d ", dp(b, i) - dp(a - 1, i));
        }
        out.println();

    }

    private long dp(int v, int x) {
        int len = 0;
        while (v > 0) {
            num[++len] = v % 10;
            v /= 10;
        }
        this.x = x;
        return dfs(true, true, len, 0);
    }

    private long dfs(boolean lead, boolean lim, int dep, int sum) {
        if (dep == 0) {
            return sum;
        }
        if (!lead && !lim && memo[dep][sum] != null) {
            return memo[dep][sum];
        }
        int max = lim ? num[dep] : 9;
        long res = 0;
        for (int i = 0; i <= max; i++) {
            int tmp = 0;
            if (i == x) {
                if (!lead || i != 0) {
                    tmp = 1;
                }
            }
            res += dfs(lead && i == 0, lim && i == max, dep - 1, sum + tmp);
        }
        if (!lead && !lim) {
            memo[dep][sum] = res;
        }
        return res;
    }
}
