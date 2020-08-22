package ox15;

import common.io.InputReader;
import common.io.OutputWriter;

public class Period {
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        if (n == 0) {
            return;
        }
        char[] a = new char[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextCharacter();
        }
        int[] next = new int[n + 1];
        kmpPre(a, n, next);
        out.println(String.format("Test case #%d", testNumber));
        for (int i = 2; i <= n; i++) {
            if (i % (i - next[i]) == 0 && i / (i - next[i]) > 1) {
                out.println(i + " " + i / (i - next[i]));
            }
        }
        out.println();
    }

    void kmpPre(char x[], int m, int[] next) {
        int i, j;
        j = next[0] = -1;
        i = 0;
        while (i < m) {
            while (-1 != j && x[i] != x[j]) {
                j = next[j];
            }
            next[++i] = ++j;
        }
    }
}
