package ox13;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.TreeSet;

public class NeighborLookup {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        TreeSet<int[]> set = new TreeSet<>((x, y) -> x[0] == y[0] ? y[1] - x[1] : x[0] - y[0]);
        set.add(new int[]{in.nextInt(), 1});
        for (int i = 2; i <= n; i++) {
            int[] num = new int[]{in.nextInt(), i};
            int t1 = Integer.MAX_VALUE;
            int t2 = 0;
            int[] floor = set.floor(num);
            if (floor != null) {
                if (Math.abs(num[0] - floor[0]) < t1) {
                    t1 = Math.abs(num[0] - floor[0]);
                    t2 = floor[1];
                }
            }
            int[] ceiling = set.ceiling(num);
            if (ceiling != null) {
                if (Math.abs(num[0] - ceiling[0]) < t1) {
                    t1 = Math.abs(num[0] - ceiling[0]);
                    t2 = ceiling[1];
                }
            }
            out.println(t1 + " " + t2);
            set.add(num);
        }
    }
}
