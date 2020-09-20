package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Stack;

public class CityGame {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int row = in.nextInt();
        int col = in.nextInt();
        int[] c = new int[col + 1];
        int res = 0;
        for (int i = 0; i < row; i++) {
            Stack<Integer> stack = new Stack<>();
            for (int j = 0; j < col + 1; j++) {
                if (j != col) {
                    char tmp = in.nextCharacter();
                    if (tmp == 'F') {
                        c[j] = c[j] + 1;
                    } else {
                        c[j] = 0;
                    }
                }
                while (!stack.isEmpty() && c[j] < c[stack.peek()]) {
                    int idx = stack.pop();
                    int prev = -1;
                    if (!stack.isEmpty()) {
                        prev = stack.peek();
                    }
                    res = Math.max(res, c[idx] * (j - prev - 1));
                }
                stack.push(j);
            }
        }
        out.println(res * 3);
    }
}
