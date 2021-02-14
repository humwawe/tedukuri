package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class NewNimGame {
    int[] d = new int[35];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int k = in.nextInt();
        int[] a = in.nextIntArray(k);
        Arrays.sort(a);
        long res = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (!add(a[i])) {
                res += a[i];
            }
        }
        out.println(res);

    }

    boolean add(int x) {
        for (int i = 30; i >= 0; i--) {
            if (((x >> i) & 1) == 1) {
                if (d[i] != 0) {
                    x ^= d[i];
                } else {
                    d[i] = x;
                    return true;
                }
            }
        }
        return false;
    }
}
