package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

public class PhoneList {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int N = 100010;
        int[][] son = new int[N][26];
        boolean[] end = new boolean[N];
        int idx = 0;
        boolean res = true;
        for (int t = 0; t < n; t++) {
            String str = in.nextString();
            if (res) {
                int p = 0;
                boolean f = true;
                for (int i = 0; i < str.length(); i++) {
                    int u = str.charAt(i) - '0';
                    if (son[p][u] == 0) {
                        f = false;
                        son[p][u] = ++idx;
                    }
                    p = son[p][u];
                    if (end[p]) {
                        res = false;
                    }
                }
                if (f && t != 0) {
                    res = false;
                }
                end[p] = true;
            }
        }
        if (res) {
            out.println("YES");
        } else {
            out.println("NO");
        }
    }

}
