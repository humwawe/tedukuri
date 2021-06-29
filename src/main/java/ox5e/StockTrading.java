package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class StockTrading {

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int maxP = in.nextInt();
        int w = in.nextInt();
        int[] ap = new int[n + 1];
        int[] bp = new int[n + 1];
        int[] as = new int[n + 1];
        int[] bs = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            ap[i] = in.nextInt();
            bp[i] = in.nextInt();
            as[i] = in.nextInt();
            bs[i] = in.nextInt();
        }
        int inf = 0x3f3f3f3f;
        int[][] f = new int[n + 1][maxP + 1];
        Arrays.fill(f[0], -inf);

        Deque<Integer> deque = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            int lDay = i - w - 1;
            for (int j = 0; j <= maxP; j++) {
                if (j <= as[i]) {
                    f[i][j] = -ap[i] * j;
                } else {
                    f[i][j] = -inf;
                }
                f[i][j] = Math.max(f[i][j], f[i - 1][j]);
            }
            if (i <= w) {
                continue;
            }
            // 买入
            for (int j = 0; j <= maxP; j++) {
                while (!deque.isEmpty() && deque.getFirst() < j - as[i]) {
                    deque.pollFirst();
                }
                if (!deque.isEmpty()) {
                    f[i][j] = Math.max(f[i][j], f[lDay][deque.getFirst()] + ap[i] * deque.getFirst() - ap[i] * j);
                }

                while (!deque.isEmpty() && f[lDay][deque.getLast()] + ap[i] * deque.getLast() <= f[lDay][j] + ap[i] * j) {
                    deque.pollLast();
                }
                deque.addLast(j);
            }
            // 卖出
            deque.clear();
            for (int j = maxP; j >= 0; j--) {
                while (!deque.isEmpty() && deque.getFirst() < j - as[i]) {
                    deque.pollFirst();
                }
                if (!deque.isEmpty()) {
                    f[i][j] = Math.max(f[i][j], f[lDay][deque.getFirst()] + ap[i] * deque.getFirst() - ap[i] * j);
                }

                while (!deque.isEmpty() && f[lDay][deque.getLast()] + ap[i] * deque.getLast() <= f[lDay][j] + ap[i] * j) {
                    deque.pollLast();
                }
                deque.addLast(j);
            }
        }

    }
}
