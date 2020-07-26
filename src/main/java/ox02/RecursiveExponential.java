package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class RecursiveExponential {
    int n;
    List<Integer> list;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        dfs(0, 0, out);
    }

    private void dfs(int i, int state, OutputWriter out) {
        if (i == n) {
            for (int j = 0; j < n; j++) {
                if ((state >> j & 1) == 1) {
                    out.print(j + 1 + " ");
                }
            }
            out.println();
            return;
        }
        dfs(i + 1, state, out);
        dfs(i + 1, state | 1 << i, out);
    }

    public void solve2(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        list = new ArrayList<>();
        helper(1, out);
    }

    private void helper(int pos, OutputWriter out) {
        if (pos == n + 1) {
            for (int i = 0; i < list.size(); i++) {
                if (i != 0) {
                    out.print(' ');
                }
                out.print(list.get(i));
            }
            out.println();
            return;
        }
        helper(pos + 1, out);
        list.add(pos);
        helper(pos + 1, out);
        list.remove(list.size() - 1);
    }
}
