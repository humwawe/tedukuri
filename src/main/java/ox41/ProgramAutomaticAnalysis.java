package ox41;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramAutomaticAnalysis {
    int n;
    int[] p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        cnt = 0;
        map = new HashMap<>();
        n = in.nextInt();
        p = new int[2 * n + 1];
        for (int i = 0; i < 2 * n + 1; i++) {
            p[i] = i;
        }
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = mapping(in.nextInt());
            int y = mapping(in.nextInt());
            int e = in.nextInt();
            if (e == 0) {
                list.add(new int[]{x, y});
            } else {
                p[find(x)] = find(y);
            }
        }
        for (int[] e : list) {
            if (find(e[0]) == find(e[1])) {
                out.println("NO");
                return;
            }
        }
        out.println("YES");
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    Map<Integer, Integer> map;
    int cnt;

    int mapping(int x) {
        if (!map.containsKey(x)) {
            map.put(x, cnt++);
        }
        return map.get(x);
    }
}