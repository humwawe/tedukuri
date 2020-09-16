package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Stack;

public class ExpressionCalculation4 {
    Stack<Integer> nums = new Stack<>();
    Stack<Character> ops = new Stack<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String s = in.nextString();
        StringBuilder stringBuilder = new StringBuilder();
        // 避免右括号太多导致错误
        for (int i = 0; i < s.length(); i++) {
            stringBuilder.append('(');
        }
        s = stringBuilder.append(s).append(')').toString();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int val = 0;
                int j = i;
                while (j < s.length() && Character.isDigit(s.charAt(j))) {
                    val = val * 10 + (s.charAt(j++) - '0');
                }
                nums.push(val);
                i = j - 1;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (ops.peek() != '(') {
                    cal();
                }
                ops.pop();
            }
            // '-'可能是负号 或者 减号
            else if (c == '-') {
                if (i != 0 && !Character.isDigit(s.charAt(i - 1)) && s.charAt(i - 1) != ')') {
                    int val = 0, j = i + 1;
                    while (j < s.length() && Character.isDigit(s.charAt(j))) {
                        val = val * 10 + (s.charAt(j++) - '0');
                    }
                    nums.push(-val);
                    i = j - 1;
                } else {
                    while (ops.peek() != '(') {
                        cal();
                    }
                    ops.push(c);
                }
            } else {
                while (!ops.isEmpty() && ops.peek() != '(' && helper(ops.peek()) >= helper(c)) {
                    cal();
                }
                ops.push(c);
            }
        }
        out.println(nums.peek());
    }

    private void cal() {
        int b = nums.pop();
        int a = nums.pop();
        char c = ops.pop();
        if (c == '+') {
            nums.push(a + b);
        } else if (c == '-') {
            nums.push(a - b);
        } else if (c == '*') {
            nums.push(a * b);
        } else if (c == '/') {
            nums.push(a / b);
        } else if (c == '^') {
            nums.push((int) Math.pow(a, b));
        }
    }

    private int helper(char c) {
        if (c == '+') {
            return 1;
        } else if (c == '-') {
            return 1;
        } else if (c == '*') {
            return 2;
        } else if (c == '/') {
            return 2;
        } else if (c == '^') {
            return 3;
        }
        return 0;
    }
}
