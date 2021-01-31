package ox35;

import common.io.InputReader;
import common.io.OutputWriter;

public class SwitchProblem {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            int tmp = in.nextInt();
            // a[i][0] 存放方程右边的常量
            a[i] ^= tmp;
            // a[i][i]=1
            a[i] |= 1 << i;
        }
        int x = in.nextInt();
        int y = in.nextInt();
        while (x != 0 && y != 0) {
            // a[y][x]=1
            a[y] |= 1 << x;
            x = in.nextInt();
            y = in.nextInt();
        }
        int res = 1;
        for (int i = 1; i <= n; i++) {
            // 找最大的主元
            for (int j = i + 1; j <= n; j++) {
                if (a[j] > a[i]) {
                    int tmp = a[i];
                    a[i] = a[j];
                    a[j] = tmp;
                }
            }
            // 消元完毕，有i-1个主元
            if (a[i] == 0) {
                res = 1 << (n - i + 1);
                break;
            }
            // 出现0=1的方程
            if (a[i] == 1) {
                res = 0;
                break;
            }
            for (int k = n; k > 0; k--) {
                if ((a[i] >> k & 1) == 1) {
                    for (int j = 1; j <= n; j++) {
                        if (i != j && (a[j] >> k & 1) == 1) {
                            a[j] ^= a[i];
                        }
                    }
                    break;
                }
            }
        }
        if (res == 0) {
            out.println("Oh,it's impossible~!!");
        } else {
            out.println(res);
        }

    }
}
