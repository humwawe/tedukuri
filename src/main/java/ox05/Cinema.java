package ox05;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cinema {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int l = in.nextInt();
            map.put(l, map.getOrDefault(l, 0) + 1);
        }
        int m = in.nextInt();
        Integer[] idx = new Integer[m];
        int[] b = new int[m];
        int[] c = new int[m];
        for (int i = 0; i < m; i++) {
            idx[i] = i;
            b[i] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            c[i] = in.nextInt();
        }
        Arrays.sort(idx, (x, y) -> map.getOrDefault(b[x], 0) == map.getOrDefault(b[y], 0) ? map.getOrDefault(c[y], 0) - map.getOrDefault(c[x], 0) : map.getOrDefault(b[y], 0) - map.getOrDefault(b[x], 0));
        out.println(idx[0] + 1);
    }

}
