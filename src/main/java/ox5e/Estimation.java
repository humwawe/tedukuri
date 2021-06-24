package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Estimation {
    int N = 2005;
    int K = 30;
    int[] a = new int[N];
    int[][] cost = new int[N][N];
    int n, k;
    int[][] f = new int[N][N];
    int inf = (int) 1e8;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        k = in.nextInt();
        if (n == 0 && k == 0) {
            return;
        }
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
        }
        for (int i = 0; i < N; i++) {
            Arrays.fill(f[i], inf);
        }
        f[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            PriorityQueue<Integer> p1 = new PriorityQueue<>((x, y) -> y - x);
            PriorityQueue<Integer> p2 = new PriorityQueue<>();
            int sumR = 0;
            int sumL = 0;
            p1.add(a[i]);
            sumL += a[i];
            for (int j = i - 1; j >= 0; j--) {
                int c = p1.size() * p1.peek() - sumL + sumR - p2.size() * p1.peek();
                for (int l = 1; l <= k; l++) {
                    f[i][l] = Math.min(f[i][l], f[j][l - 1] + c);
                }
                if (a[j] <= p1.peek()) {
                    p1.add(a[j]);
                    sumL += a[j];
                } else {
                    p2.add(a[j]);
                    sumR += a[j];
                }
                while (p1.size() > p2.size() + 1) {
                    int cur = p1.poll();
                    p2.add(cur);
                    sumL -= cur;
                    sumR += cur;
                }
                while (p1.size() < p2.size()) {
                    int cur = p2.poll();
                    p1.add(cur);
                    sumL += cur;
                    sumR -= cur;
                }
            }
        }
        out.println(f[n][k]);
    }
}
