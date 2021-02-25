package ox44;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Dandelion {
    int n, m;
    int[] a;
    int[] b;
    int len;
    int size, cnt;
    int[][] p;
    int[][] sum;
    int[] pos;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        size = (int) Math.sqrt(n);
        cnt = n / size;
        if (n % size != 0) {
            cnt++;
        }
        p = new int[cnt + 1][2];
        m = in.nextInt();
        a = new int[n + 1];
        b = new int[n + 1];
        pos = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            b[i] = a[i];
        }
        len = 0;
        discrete();
        for (int i = 1; i <= n; i++) {
            a[i] = find(a[i]);
        }
        sum = new int[cnt + 1][len + 1];
        build();
        int res = 0;
        for (int i = 0; i < m; i++) {
            int l = (in.nextInt() + res - 1) % n + 1;
            int r = (in.nextInt() + res - 1) % n + 1;
            res = cal(Math.min(l, r), Math.max(l, r));
            out.println(res);
        }
    }

    int cal(int l, int r) {
        if (pos[r] - pos[l] <= 1) {
            return violent(l, r);
        }
        int[] tmp = new int[len + 1];
        for (int i = 1; i <= len; i++) {
            tmp[i] = sum[pos[r] - 1][i] - sum[pos[l] + 1 - 1][i];
        }
        for (int i = l; i <= p[pos[l]][1]; i++) {
            tmp[a[i]]++;
        }
        for (int i = p[pos[r]][0]; i <= r; i++) {
            tmp[a[i]]++;
        }
        int val = 0, id = 0;
        for (int i = 1; i <= len; i++) {
            if (tmp[i] > val) {
                val = tmp[i];
                id = i;
            }
        }
        return raw(id);
    }

    int violent(int l, int r) {
        Set<Integer> set = new HashSet<>();
        int[] c = new int[len + 1];
        for (int i = l; i <= r; i++) {
            c[a[i]]++;
            set.add(a[i]);
        }
        int val = 0, id = 0x7fffffff;
        for (Integer i : set) {
            if (c[i] > val) {
                val = c[i];
                id = i;
            } else if (c[i] == val) {
                id = Math.min(i, id);
            }
        }
        return raw(id);
    }


    void build() {
        for (int i = 1; i <= cnt; i++) {
            p[i][0] = (i - 1) * size + 1;
            p[i][1] = i * size;
        }
        p[cnt][1] = n;
        for (int i = 1; i <= cnt; i++) {
            int l = p[i][0];
            int r = p[i][1];
            for (int j = l; j <= r; j++) {
                sum[i][a[j]]++;
                pos[j] = i;
            }
            for (int j = 1; j <= len; j++) {
                sum[i][j] += sum[i - 1][j];
            }
        }
    }

    public int find(int x) {
        return Arrays.binarySearch(b, 1, len + 1, x);
    }

    int raw(int x) {
        return b[x];
    }

    void discrete() {
        Arrays.sort(b);
        for (int i = 1; i <= n; i++) {
            if (i == 1 || b[i] != b[i - 1]) {
                b[++len] = b[i];
            }
        }
    }
}
