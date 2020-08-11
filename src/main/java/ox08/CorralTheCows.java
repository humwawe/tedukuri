package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class CorralTheCows {
    int c, n;
    int[][] a;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        c = in.nextInt();
        n = in.nextInt();
        a = new int[n][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        Arrays.sort(a, Comparator.comparingInt(x -> x[0]));
        int l = 1;
        int r = 10000;
        while (l < r) {
            int mid = l + r >> 1;
            if (check(mid-1)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        out.println(l);
    }

    private boolean check(int len) {
        for (int i = 0; i < n; i++) {
            int j = i;
            // 确定x的[j,i]
            while (j >= 0 && a[i][0] - a[j][0] <= len) {
                j--;
            }
            j++;
            if (i - j + 1 < c) {
                continue;
            }
            // 在可选的y区间中找满足的最大个数
            for (int k = j; k <= i; k++) {
                int d = a[k][1];
                int u = a[k][1] + len;
                int res = 0;
                for (int l = j; l <= i; l++) {
                    if (a[l][1] >= d && a[l][1] <= u) {
                        res++;
                    }
                }
                if (res >= c) {
                    return true;
                }
            }
        }
        return false;
    }
}
