package ox32;

import common.io.InputReader;
import common.io.OutputWriter;

public class VisibleLatticePoints {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        int[] eulers = getEulers(n);
        int res = 3;
        for (int i = 2; i <= n; i++) {
            res += 2 * eulers[i];
        }
        out.println(testNumber + " " + n + " " + res);
    }

    int[] getEulers(int n) {
        int[] euler = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            euler[i] = i;
        }
        for (int i = 2; i <= n; i++) {
            if (euler[i] == i) {
                for (int j = i; j <= n; j += i) {
                    euler[j] = euler[j] / i * (i - 1);
                }
            }
        }
        return euler;
    }
}
