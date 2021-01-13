package ox28;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class SquareDestroyer {
    int N = 65;
    int n;
    int m;
    boolean[] st;
    List<List<Integer>> lists;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        m = 0;
        lists = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (int a = 1; a + i - 1 <= n; a++) {
                for (int b = 1; b + i - 1 <= n; b++) {
                    List<Integer> tmp = new ArrayList<>();
                    for (int j = 0; j < i; j++) {
                        int d = 2 * n + 1;
                        tmp.add((a - 1) * d + b + j);
                        tmp.add((a - 1 + i) * d + b + j);
                        tmp.add(n + 1 + (a - 1) * d + b - 1 + j * d);
                        tmp.add(n + 1 + (a - 1) * d + b - 1 + j * d + i);
                    }
                    lists.add(tmp);
                }
            }
        }
        st = new boolean[N];
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            int t = in.nextInt();
            st[t] = true;
        }

        int depth = 0;
        while (!dfs(depth)) {
            depth++;
        }

        out.println(depth);

    }

    private boolean dfs(int depth) {
        if (f() > depth) {
            return false;
        }
        for (List<Integer> list : lists) {
            if (check(list)) {
                for (Integer x : list) {
                    st[x] = true;
                    if (dfs(depth - 1)) {
                        return true;
                    }
                    st[x] = false;
                }
                return false;
            }
        }
        return true;

    }

    boolean check(List<Integer> list) {
        for (Integer x : list) {
            if (st[x]) {
                return false;
            }
        }
        return true;
    }

    int f() {
        boolean[] state = new boolean[N];
        System.arraycopy(st, 0, state, 0, N);
        int res = 0;
        for (List<Integer> list : lists) {
            if (check(list)) {
                res++;
                for (Integer x : list) {
                    st[x] = true;
                }
            }
        }
        System.arraycopy(state, 0, st, 0, N);
        return res;
    }
}
