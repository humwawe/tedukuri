package ox22;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class KittenClimbing {
    int res = Integer.MAX_VALUE;
    int[] cab;
    int w;
    int[] c;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        w = in.nextInt();
        c = in.nextIntArray(n);
        Arrays.sort(c);
        cab = new int[n];
        dfs(n - 1, 0);
        out.println(res);
    }

    private void dfs(int i, int cnt) {
        if (cnt >= res) {
            return;
        }
        if (i < 0) {
            res = Math.min(res, cnt);
            return;
        }

        for (int j = 0; j < cnt; j++) {
            if (cab[j] + c[i] <= w) {
                cab[j] += c[i];
                dfs(i - 1, cnt);
                cab[j] -= c[i];
            }
        }
        cab[cnt] += c[i];
        dfs(i - 1, cnt + 1);
        cab[cnt] -= c[i];
    }
}
