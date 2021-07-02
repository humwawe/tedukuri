package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

public class RoundNumbers {
    int a, b;
    char[] num;
    int N = 32;
    Integer[][][] memo = new Integer[N][N][N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextInt();
        b = in.nextInt();
        out.println(dp(b) - dp(a - 1));
    }

    private int dp(int v) {
        String s = Integer.toBinaryString(v);
        num = s.toCharArray();
        return dfs(true, true, num.length, 0, 0);
    }

    private int dfs(boolean lead, boolean lim, int dep, int z, int o) {
        if (dep == 0) {
            if (z >= o) {
                return 1;
            }
            return 0;
        }
        if (!lead && !lim && memo[dep][z][o] != null) {
            return memo[dep][z][o];
        }
        char max = lim ? num[num.length - dep] : '1';
        int res = 0;
        for (char i = '0'; i <= max; i++) {
            int tmp = 0;
            if (i == '0') {
                if (!lead) {
                    tmp = 1;
                }
            }
            res += dfs(lead && i == '0', lim && i == max, dep - 1, z + tmp, o + (i == '1' ? 1 : 0));
        }
        if (!lead && !lim) {
            memo[dep][z][o] = res;
        }
        return res;

    }
}
