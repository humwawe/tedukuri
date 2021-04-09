package ox57;

import common.io.InputReader;
import common.io.OutputWriter;

public class CountTheRepetitions {
    int N = 105, M = 31;
    long[][] f = new long[N][M];
    int n1, n2;
    String s1, s2;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        s2 = in.nextString();
        n2 = in.nextInt();
        s1 = in.nextString();
        n1 = in.nextInt();
        int len = s1.length();
        for (int i = 0; i < len; i++) {
            int pos = i;
            f[i][0] = 0;
            for (int j = 0; j < s2.length(); j++) {
                int step = 0;
                while (s1.charAt(pos) != s2.charAt(j)) {
                    pos = (pos + 1) % len;
                    if (++step >= len) {
                        out.println("0");
                        return;
                    }
                }
                pos = (pos + 1) % len;
                f[i][0] += step + 1;
            }
        }
        for (int i = 1; i < 31; i++) {
            for (int j = 0; j < len; j++) {
                f[j][i] = f[j][i - 1] + f[(int) ((j + f[j][i - 1]) % len)][i - 1];
            }
        }

        long m = 0;
        for (int st = 0; st < len; st++) {
            long x = st;
            long res = 0;
            for (int k = 30; k >= 0; k--) {
                if (x + f[(int) (x % len)][k] <= len * n1) {
                    x += f[(int) (x % len)][k];
                    res += 1 << k;
                }
            }
            m = Math.max(m, res);
        }
        out.println(m / n2);

    }
}
