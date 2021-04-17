package ox5a;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.LinkedList;

public class TaskArrangementB {
    int N = (int) (3e5 + 10);
    long[] t = new long[N];
    long[] c = new long[N];
    long[] f = new long[N];
    int n, s;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int s = in.nextInt();
        for (int i = 1; i <= n; i++) {
            t[i] = in.nextInt();
            c[i] = in.nextInt();
            t[i] += t[i - 1];
            c[i] += c[i - 1];
        }
        LinkedList<Integer> deque = new LinkedList<>();
        deque.addLast(0);
        for (int i = 1; i <= n; i++) {
            while (deque.size() >= 2 && (f[getFirstFirst(deque)] - f[deque.getFirst()]) <= (t[i] + s) * (c[getFirstFirst(deque)] - c[deque.getFirst()])) {
                deque.pollFirst();
            }
            f[i] = f[deque.peekFirst()] + t[i] * c[i] + s * c[n] - c[deque.peekFirst()] * (t[i] + s);

            while (deque.size() >= 2 && (f[deque.getLast()] - f[getLastLast(deque)]) * (c[i] - c[getLastLast(deque)]) >= (f[i] - f[getLastLast(deque)]) * (c[deque.getLast()] - c[getLastLast(deque)])) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        out.println(f[n]);
    }

    int getFirstFirst(LinkedList<Integer> deque) {
        return deque.get(1);
    }

    int getLastLast(LinkedList<Integer> deque) {
        return deque.get(deque.size() - 2);
    }
}
