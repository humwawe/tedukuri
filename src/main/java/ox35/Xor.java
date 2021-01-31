package ox35;

import common.io.InputReader;
import common.io.OutputWriter;

public class Xor {

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        long[] a = in.nextLongArray(n);
        int i = gaussXor(a);
        int m = in.nextInt();
        out.println("Case #" + testNumber + ":");
        while (m-- > 0) {
            long res = 0;
            long k = in.nextLong();
            if (i > 0) {
                k--;
            }
            if ((1L << (n - i)) <= k) {
                out.println(-1);
            } else {
                for (int j = n - i - 1; j >= 0; j--) {
                    if (((k >> j) & 1) == 1) {
                        res ^= a[n - i - 1 - j];
                    }
                }
                out.println(res);
            }
        }
    }

    int gaussXor(long[] a) {
        int row = a.length;
        int res = 0;
        for (int i = 0; i < row; i++) {
            // 找最大的主元
            for (int j = i + 1; j < row; j++) {
                if (a[j] > a[i]) {
                    long tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
            // 消元完毕，有i个主元，row-i个自由元
            if (a[i] == 0) {
                res = row - i;
                break;
            }

            for (int k = 63; k >= 0; k--) {
                if ((a[i] >> k & 1) == 1) {
                    for (int j = 0; j < row; j++) {
                        if (i != j && (a[j] >> k & 1) == 1) {
                            a[j] ^= a[i];
                        }
                    }
                    break;
                }
            }
        }
        return res;
    }
}