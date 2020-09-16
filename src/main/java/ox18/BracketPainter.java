package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Stack;

public class BracketPainter {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s = in.nextString();
        int len = s.length();
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (!stack.isEmpty()) {
                char t = s.charAt(stack.peek());
                if (helper(c) < 0 && helper(t) == -helper(c)) {
                    stack.pop();
                } else {
                    stack.push(i);
                }
            } else {
                stack.push(i);
            }
            if (!stack.isEmpty()) {
                res = Math.max(res, i - stack.peek());
            } else {
                res = Math.max(res, i + 1);
            }

        }
        out.println(res);
    }

    private int helper(char c) {
        if (c == '(') {
            return 1;
        } else if (c == '{') {
            return 2;
        } else if (c == '[') {
            return 3;
        } else if (c == ')') {
            return -1;
        } else if (c == '}') {
            return -2;
        } else if (c == ']') {
            return -3;
        }
        return 0;
    }
}
