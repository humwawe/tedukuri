package ox44;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class LittleZSocks {
    int N = 50005;
    int n, m;
    int[] pos = new int[N];
    int[] cnt = new int[N];
    int[] a = new int[N];
    long tmp = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        int size = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            pos[i] = i / size;
        }
        Node[] nodes = new Node[m];
        for (int i = 0; i < m; i++) {
            nodes[i] = new Node(in.nextInt(), in.nextInt(), i);
        }
        Arrays.sort(nodes, (x, y) -> {
            if (pos[x.l] == pos[y.l]) {
                return x.r - y.r;
            }
            return pos[x.l] - pos[y.l];
        });
        int l = 1;
        int r = 0;
        long[][] res = new long[m][2];
        for (int i = 0; i < m; i++) {
            int ql = nodes[i].l;
            int qr = nodes[i].r;
            while (l < ql) {
                del(l++);
            }
            while (l > ql) {
                add(--l);
            }
            while (r < qr) {
                add(++r);
            }
            while (r > qr) {
                del(r--);
            }
            if (nodes[i].l == nodes[i].r) {
                res[nodes[i].id][0] = 0;
                res[nodes[i].id][1] = 1;
                continue;
            }
            long len = nodes[i].r - nodes[i].l + 1;
            long d = gcd(tmp, len * (len - 1));
            res[nodes[i].id][0] = tmp / d;
            res[nodes[i].id][1] = len * (len - 1) / d;
        }
        for (int i = 0; i < m; i++) {
            out.println(res[i][0] + "/" + res[i][1]);
        }
    }

    void add(int pos) {
        int x = a[pos];
        tmp -= cnt[x] * (cnt[x] - 1);
        cnt[x]++;
        tmp += cnt[x] * (cnt[x] - 1);
    }

    void del(int pos) {
        int x = a[pos];
        tmp -= cnt[x] * (cnt[x] - 1);
        cnt[x]--;
        tmp += cnt[x] * (cnt[x] - 1);
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    class Node {
        int l, r;
        int id;

        public Node(int l, int r, int id) {
            this.l = l;
            this.r = r;
            this.id = id;
        }
    }
}
