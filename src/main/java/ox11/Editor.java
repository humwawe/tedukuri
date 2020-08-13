package ox11;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Stack;

public class Editor {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        Stack<Integer> st1 = new Stack<>();
        Stack<Integer> st2 = new Stack<>();
        int n = in.nextInt();
        int idx = 1;
        int[] sum = new int[n + 1];
        int[] f = new int[n + 1];
        Arrays.fill(f, (int) -1e8);
        for (int i = 0; i < n; i++) {
            char c = in.nextCharacter();
            if (c == 'I') {
                int num = in.nextInt();
                st1.push(num);
                sum[idx] = sum[idx - 1] + num;
                f[idx] = Math.max(f[idx - 1], sum[idx]);
                idx++;
            } else if (c == 'D') {
                if (!st1.isEmpty()) {
                    st1.pop();
                    idx--;
                }
            } else if (c == 'L') {
                if (!st1.isEmpty()) {
                    st2.push(st1.pop());
                    idx--;
                }
            } else if (c == 'R') {
                if (!st2.isEmpty()) {
                    int num = st2.pop();
                    st1.push(num);
                    sum[idx] = sum[idx - 1] + num;
                    f[idx] = Math.max(f[idx - 1], sum[idx]);
                    idx++;
                }
            } else {
                int k = in.nextInt();
                out.println(f[k]);
            }
        }
    }
}
