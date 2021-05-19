package ox5b;

import common.io.InputReader;
import common.io.OutputWriter;

public class MergingOfStones {
    int N = (int) (5e5 + 5);
    int[] a = new int[N];
    int n;
    int res, tot;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        res = 0;
        tot = 0;
        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            a[++tot] = a[i];
            while (tot > 2 && a[tot - 2] <= a[tot]) {
                unions(tot - 1);
            }
        }
        while (tot > 1) {
            unions(tot);
        }
        out.println(res);
    }

    private void unions(int x) {
        int tmp = a[x] + a[x - 1];
        res += tmp;
        for (int i = x; i < tot; i++) {
            a[i] = a[i + 1];
        }
        int j;
        for (j = x - 1; a[j - 1] < tmp && j > 1; j--) {
            a[j] = a[j - 1];
        }
        a[j] = tmp;
        tot--;
        for (int d = tot - j; j > 2 && a[j - 2] <= a[j]; d = tot - j) {
            unions(j - 1);
            j = tot - d;
        }
    }
}
