package ox5d;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class MysteryOfTheMoon {
    int l, r;
    int[] num = new int[12];
    int p;
    int[][][] f = new int[12][100][100];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        l = in.nextInt();
        r = in.nextInt();
        out.println(helper(r) - helper(l - 1));
    }

    private int helper(int v) {
        int idx = 0;
        Arrays.fill(num, 0);
        while (v > 0) {
            num[++idx] = v % 10;
            v /= 10;
        }
        int res = 0;
        for (p = 1; p < 100; p++) {
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 100; j++) {
                    Arrays.fill(f[i][j], -1);
                }
            }
            res += dfs(idx, 0, true, 0);
        }
        return res;
    }

    private int dfs(int len, int sum, boolean lim, int mod) {
        if (len == 0) {
            if (sum == p && mod == 0) {
                return 1;
            }
            return 0;
        }
        if (!lim && f[len][sum][mod] != -1) {
            return f[len][sum][mod];
        }
        int res = 0;
        int up = lim ? num[len] : 9;
        for (int i = 0; i <= up; i++) {
            res += dfs(len - 1, sum + i, lim && i == num[len], (mod * 10 + i) % p);
        }
        if (!lim) {
            f[len][sum][mod] = res;
        }
        return res;
    }
}
