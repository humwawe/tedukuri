package ox5a;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class TaskArrangementA {
    int N = 5010;
    int[] t = new int[N];
    int[] c = new int[N];
    int[] f = new int[N];
    int n, s;
    int inf = 0x3f3f3f3f;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int s = in.nextInt();
        for (int i = 1; i <= n; i++) {
            t[i] = in.nextInt();
            c[i] = in.nextInt();
            t[i] += t[i - 1];
            c[i] += c[i - 1];
        }
        Arrays.fill(f, inf);
        f[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                f[i] = Math.min(f[i], f[j] + t[i] * (c[i] - c[j]) + s * (c[n] - c[j]));
            }
        }
        out.println(f[n]);
    }
}
