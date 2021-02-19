package ox41;

import common.io.InputReader;
import common.io.OutputWriter;

public class FoodChain {
    int n;
    int[] p;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        p = new int[3 * n];
        for (int i = 0; i < 3 * n; i++) {
            p[i] = i;
        }
        int k = in.nextInt();
        int res = 0;
        for (int i = 0; i < k; i++) {
            int t = in.nextInt();
            int a = in.nextInt() - 1;
            int b = in.nextInt() - 1;
            if (a >= n || b >= n) {
                res += 1;
                continue;
            }
            int aSelf = a;
            int aEat = a + n;
            int aEnemy = a + 2 * n;
            int bSelf = b;
            int bEat = b + n;
            int bEnemy = b + 2 * n;

            if (t == 1) {
                if (find(aSelf) == find(bEat) || find(aEat) == find(bSelf)) {
                    res += 1;
                    continue;
                }
                p[find(aSelf)] = find(bSelf);
                p[find(aEat)] = find(bEat);
                p[find(aEnemy)] = find(bEnemy);
            } else {
                if (find(aSelf) == find(bSelf) || find(aSelf) == find(bEat)) {
                    res += 1;
                    continue;
                }
                p[find(aSelf)] = find(bEnemy);
                p[find(aEat)] = find(bSelf);
                p[find(aEnemy)] = find(bEat);
            }
        }
        out.println(res);
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
