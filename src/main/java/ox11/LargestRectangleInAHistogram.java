package ox11;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Stack;

public class LargestRectangleInAHistogram {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        if (n == 0) {
            return;
        }
        long[] a = new long[n + 1];
        Stack<Integer> st = new Stack<>();
        long res = 0;
        for (int i = 0; i < n + 1; i++) {
            if (i != n) {
                a[i] = in.nextLong();
            }
            while (!st.isEmpty() && a[i] < a[st.peek()]) {
                int idx = st.pop();
                int prev = -1;
                if (!st.isEmpty()) {
                    prev = st.peek();
                }
                res = Math.max(res, a[idx] * (i - prev - 1));
            }
            st.push(i);
        }
        out.println(res);
    }
}
