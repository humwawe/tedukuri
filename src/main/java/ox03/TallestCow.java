package ox03;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.HashSet;
import java.util.Set;

public class TallestCow {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int p = in.nextInt();
        int h = in.nextInt();
        int m = in.nextInt();

        Set<Integer> set = new HashSet<>();
        int[] a = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int x = in.nextInt();
            int y = in.nextInt();
            int max = Math.max(x, y);
            int min = Math.min(x, y);
            if (set.add(min * 10001 + max)) {
                a[min + 1]--;
                a[max]++;
            }
        }

        for (int i = 1; i <= n; i++) {
            a[i] = a[i - 1] + a[i];
            out.println(a[i] + h);
        }
    }
}
