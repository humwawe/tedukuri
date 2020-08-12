package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.TreeMap;

public class Task {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] a = new int[n][2];
        int[][] b = new int[m][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = in.nextInt();
            a[i][1] = in.nextInt();
        }
        for (int i = 0; i < m; i++) {
            b[i][0] = in.nextInt();
            b[i][1] = in.nextInt();
        }
        Arrays.sort(a, (x, y) -> x[0] == y[0] ? y[1] - x[1] : y[0] - x[0]);
        Arrays.sort(b, (x, y) -> x[0] == y[0] ? y[1] - x[1] : y[0] - x[0]);
        int j = 0;
        long res = 0;
        int cnt = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int i = 0; i < m; i++) {
            while (j < n && a[j][0] >= b[i][0]) {
                map.put(a[j][1], map.getOrDefault(a[j][1], 0) + 1);
                j++;
            }
            Integer integer = map.ceilingKey(b[i][1]);
            if (integer != null) {
                cnt++;
                res += 500 * b[i][0] + 2 * b[i][1];
                int count = map.get(integer);
                if (count == 1) {
                    map.remove(integer);
                } else {
                    map.put(integer, count - 1);
                }
            }
        }
        out.print(cnt + " ");
        out.println(res);
    }
}
