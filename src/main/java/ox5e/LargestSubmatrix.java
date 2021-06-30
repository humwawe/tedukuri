package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Stack;

public class LargestSubmatrix {
    int N = 1005;
    int n, m;
    char[][] c = new char[N][N];
    int[][] up = new int[N][N];
    int res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                c[i][j] = in.nextCharacter();
            }
        }
        helper('a');
        helper('b');
        helper('c');

        out.println(res);
    }

    private void helper(char t) {
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (vaild(c[i][j], t)) {
                    up[i][j] = up[i - 1][j] + 1;
                } else {
                    up[i][j] = 0;
                }
            }

            int[] l = new int[m + 1];
            int[] r = new int[m + 1];
            Stack<Integer> stack = new Stack<>();
            stack.add(0);
            up[i][0] = -1;
            for (int j = 1; j <= m; j++) {
                while (!stack.isEmpty() && up[i][j] <= up[i][stack.peek()]) {
                    r[stack.peek()] = j;
                    stack.pop();
                }
                l[j] = stack.peek();
                stack.add(j);
            }
            while (!stack.isEmpty()) {
                r[stack.pop()] = m + 1;
            }
            for (int j = 1; j <= m; j++) {
                res = Math.max(res, up[i][j] * (r[j] - l[j] - 1));
            }
        }

    }

    boolean vaild(char cur, char t) {
        if (cur == 'w') {
            return t == 'a' || t == 'b';
        } else if (cur == 'x') {
            return t == 'b' || t == 'c';
        } else if (cur == 'y') {
            return t == 'a' || t == 'c';
        } else if (cur == 'z') {
            return true;
        }
        return cur == t;
    }
}
