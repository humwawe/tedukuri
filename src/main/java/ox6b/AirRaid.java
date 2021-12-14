package ox6b;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class AirRaid {
    int N = 125;
    int M = N * N;
    int n, m;
    int[] h = new int[N];
    int[] e = new int[M];
    int[] ne = new int[M];
    int idx;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Arrays.fill(match, 0);
        Arrays.fill(h, -1);
        idx = 0;
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
//            add(a, b + n);
            add(a, b);
        }
        n1 = n;
        out.println(n - match());
    }

    int n1;
    // 存储第二个集合中的每个点当前匹配的第一个集合中的点是哪个
    int[] match = new int[N];
    boolean[] vis = new boolean[N];

    // 匈牙利算法中只会用到从第一个集合指向第二个集合的边，所以只用存一个方向的边
    int match() {
        int res = 0;
        for (int i = 1; i <= n1; i++) {
            Arrays.fill(vis, false);
            if (find(i)) {
                res++;
            }
        }
        return res;
    }

    boolean find(int x) {
        for (int i = h[x]; i != -1; i = ne[i]) {
            int j = e[i];
            if (!vis[j]) {
                vis[j] = true;
                if (match[j] == 0 || find(match[j])) {
                    match[j] = x;
                    return true;
                }

            }
        }
        return false;
    }

    void add(int a, int b) {
        e[idx] = b;
        ne[idx] = h[a];
        h[a] = idx++;
    }
}
