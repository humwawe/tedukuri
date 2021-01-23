package ox32;

import common.io.InputReader;
import common.io.OutputWriter;

public class Emirp {
    int n;
    int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
    int res = 0;
    int maxCnt = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        dfs(0, 30, 1, 1);
        out.println(res);
    }

    private void dfs(int pos, int prevCnt, int s, int cnt) {
        if (cnt > maxCnt || cnt == maxCnt && s < res) {
            res = s;
            maxCnt = cnt;
        }
        if (pos == 9) {
            return;
        }
        for (int i = 1; i <= prevCnt; i++) {
            if ((long) s * primes[pos] > n) {
                break;
            }
            s *= primes[pos];
            dfs(pos + 1, i, s, cnt * (i + 1));
        }
    }
}
