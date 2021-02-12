package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
    int a, b, c;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int type = in.nextInt();
        while (n-- > 0) {
            a = in.nextInt();
            b = in.nextInt();
            c = in.nextInt();
            if (type == 1) {
                out.println(qp(a, b, c));
            } else if (type == 2) {
                int d = exgcd(a, c);
                if (b % d != 0) {
                    out.println("Orz, I cannot find x!");
                } else {
                    out.println(((t1 * b / d) % c + c) % c);
                }
            } else if (type == 3) {
                int tmp = bsgs(a, b, c);
                if (tmp == -1) {
                    out.println("Orz, I cannot find x!");
                } else {
                    out.println(tmp);
                }
            }
        }
    }

    int qp(int m, int k, int p) {
        long res = 1 % p, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % p;
            }
            t = t * t % p;
            k >>= 1;
        }
        return (int) res % p;
    }

    long t1, t2;

    int exgcd(int a, int b) {
        if (b == 0) {
            t1 = 1;
            t2 = 0;
            return a;
        }
        int d = exgcd(b, a % b);
        long tmp = t1;
        t1 = t2;
        t2 = tmp - a / b * t2;
        return d;
    }

    int bsgs(int a, int b, int p) {
        Map<Integer, Integer> map = new HashMap<>();
        b %= p;
        int t = (int) (Math.sqrt(p)) + 1;
        for (int j = 0; j < t; j++) {
            // b*a^j
            int val = (int) ((long) b * qp(a, j, p) % p);
            map.put(val, j);
        }
        // a^t
        a = qp(a, t, p);
        if (a == 0) {
            return b == 0 ? 1 : -1;
        }
        for (int i = 0; i <= t; i++) {
            // (a^t)^i
            int val = qp(a, i, p);
            int j = map.getOrDefault(val, -1);
            if (j >= 0 && i * t - j >= 0) {
                return i * t - j;
            }
        }
        return -1;
    }

}
