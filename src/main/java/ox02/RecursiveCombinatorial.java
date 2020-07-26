package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class RecursiveCombinatorial {
    int n, m;
    List<Integer> list;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        list = new ArrayList<>();
        helper(1, out);
    }

    private void helper(int pos, OutputWriter out) {
        if (list.size() > m || list.size() + n - pos + 1 < m) {
            return;
        }
        if (pos == n + 1) {
            for (Integer i : list) {
                out.print(i + " ");
            }
            out.println();
        }
        list.add(pos);
        helper(pos + 1, out);
        list.remove(list.size() - 1);
        helper(pos + 1, out);
    }
}
