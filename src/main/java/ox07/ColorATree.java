package ox07;

import common.io.InputReader;
import common.io.OutputWriter;

public class ColorATree {
    int root;
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        root = in.nextInt();
        Node[] nodes = new Node[n + 1];
        int res = 0;
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node();
            nodes[i].sum = in.nextInt();
            nodes[i].avg = nodes[i].sum;
            nodes[i].size = 1;
            res += nodes[i].sum;
        }
        for (int i = 0; i < n - 1; i++) {
            int x, y;
            x = in.nextInt();
            y = in.nextInt();
            nodes[y].p = x;
        }
        // 合并n-1次
        for (int i = 0; i < n - 1; i++) {
            int u = findMax(nodes);
            int fa = nodes[u].p;
            res += nodes[u].sum * nodes[fa].size;
            // 相当于删除了这个点
            nodes[u].avg = -1;
            for (int j = 1; j <= n; j++) {
                if (nodes[j].p == u) {
                    nodes[j].p = fa;
                }
            }
            nodes[fa].sum += nodes[u].sum;
            nodes[fa].size += nodes[u].size;
            nodes[fa].avg = (double) nodes[fa].sum / nodes[fa].size;
        }
        out.println(res);

    }

    private int findMax(Node[] nodes) {
        double avg = 0;
        int res = -1;
        for (int i = 1; i <= n; i++) {
            if (i != root && nodes[i].avg > avg) {
                avg = nodes[i].avg;
                res = i;
            }
        }
        return res;
    }

    class Node {
        int p;
        int size;
        int sum;
        double avg;
    }
}
