package ox49;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.TreeSet;

public class TurnoverStatistics {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        TreeSet<Integer> set = new TreeSet<>();
        long res = 0;
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            Integer ceiling = set.ceiling(x);
            Integer floor = set.floor(x);
            if (ceiling != null && floor != null) {
                res += Math.min(ceiling - x, x - floor);
            } else if (ceiling != null) {
                res += ceiling - x;
            } else if (floor != null) {
                res += x - floor;
            } else {
                res += x;
            }
            set.add(x);
        }
        out.println(res);
    }
}
