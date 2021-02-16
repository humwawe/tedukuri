package ox3b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MagicBeads {
    int n;
    int[] a;
    int N = 1005;
    Integer[] memo = new Integer[N];

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = in.nextIntArray(n);
        int res = 0;
        for (int i = 0; i < n; i++) {
            res ^= sg(a[i]);
        }
        if (res != 0) {
            out.println("freda");
        } else {
            out.println("rainbow");
        }
    }

    private int sg(int x) {
        if (x == 1) {
            return 0;
        }
        if (memo[x] != null) {
            return memo[x];
        }
        List<Integer> list = new ArrayList<>();
        list.add(1);
        int res = sg(1);
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                list.add(i);
                res ^= sg(i);
                if (i * i != x) {
                    list.add(x / i);
                    res ^= sg(x / i);
                }
            }
        }
        Set<Integer> set = new HashSet<>();
        for (Integer i : list) {
            int tmp = sg(i);
            tmp ^= res;
            set.add(tmp);
        }
        return memo[x] = mex(set);
    }

    private int mex(Set<Integer> set) {
        for (int i = 0; ; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
    }
}
