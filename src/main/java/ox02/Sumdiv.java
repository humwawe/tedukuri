package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.HashMap;
import java.util.Map;

public class Sumdiv {
    int mod = 9901;
    int a;
    int b;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextInt();
        b = in.nextInt();
        if (a == 0) {
            out.println(0);
        } else
            out.println(divideSum(a));
    }

    long divideSum(int x) {
        Map<Integer, Integer> map = divide(x);
        long res = 1;
        for (Map.Entry<Integer, Integer> kv : map.entrySet()) {
            int p = kv.getKey();
            int c = kv.getValue() * b;
            long t = sum(p, c);
//            while (c-- > 0) {
//                t = (t * p + 1) % mod;
//            }
            res = res * t % mod;
        }
        return res;
    }

    long sum(int p, int c) {
        if (c == 0) {
            return 1;
        }
        if (c % 2 == 0) {
            return (1 + qmi(p, c / 2)) * sum(p, c / 2 - 1) % mod + qmi(p, c);
        } else {
            return (1 + qmi(p, (c + 1) / 2)) * sum(p, (c - 1) / 2) % mod;
        }
    }

    int qmi(int m, int k) {
        long res = 1 % mod, t = m;
        while (k > 0) {
            if ((k & 1) == 1) {
                res = res * t % mod;
            }
            t = t * t % mod;
            k >>= 1;
        }
        return (int) res % mod;
    }

    Map<Integer, Integer> divide(int x) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 2; i <= x / i; i++) {
            while (x % i == 0) {
                x /= i;
                map.put(i, map.getOrDefault(i, 0) + 1);
            }
        }
        if (x > 1) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        return map;
    }
}
