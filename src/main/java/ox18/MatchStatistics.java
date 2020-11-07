package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

public class MatchStatistics {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int q = in.nextInt();
        String a = in.nextString();
        String b = in.nextString();
        int[] next = new int[b.length() + 1];

        int i = 0;
        int j = next[0] = -1;
        while (i < m) {
            while (-1 != j && b.charAt(i) != b.charAt(j)) {
                j = next[j];
            }
            next[++i] = ++j;
        }

        int[] f = new int[m + 2];
        i = j = 0;
        while (i < n) {
            while (j >= m || -1 != j && a.charAt(i) != b.charAt(j)) {
                j = next[j];
            }
            i++;
            j++;
            f[j]++;
        }
        for (int k = m; k >= 1; k--) {
            f[next[k]] += f[k];
        }
        while (q-- > 0) {
            int x = in.nextInt();
            if (x > m) {
                out.println(0);
            } else {
                out.println(f[x] - f[x + 1]);
            }
        }
    }
}
