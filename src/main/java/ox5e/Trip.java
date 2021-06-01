package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Trip {
    String a, b;
    int N = 85;
    int[][] f = new int[N][N];
    int[][] f1 = new int[N][N];
    int[][] f2 = new int[N][N];
    List<String> list = new ArrayList<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        a = in.nextString();
        b = in.nextString();

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                f[i][j] = Math.max(f[i - 1][j], f[i][j - 1]);
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    f[i][j] = Math.max(f[i - 1][j - 1] + 1, f[i][j]);
                }
            }
        }

        for (int i = 0; i < 26; i++) {
            for (int j = 1; j <= a.length(); j++) {
                if (a.charAt(j - 1) - 'a' == i) {
                    f1[i][j] = j;
                } else {
                    f1[i][j] = f1[i][j - 1];
                }
            }
            for (int j = 1; j <= b.length(); j++) {
                if (b.charAt(j - 1) - 'a' == i) {
                    f2[i][j] = j;
                } else {
                    f2[i][j] = f2[i][j - 1];
                }
            }
        }
        dfs(a.length(), b.length(), "", f[a.length()][b.length()]);
        Collections.sort(list);
        for (String s : list) {
            out.println(s);
        }

    }

    private void dfs(int aLen, int bLen, String s, int len) {
        if (aLen < 0 || bLen < 0) {
            return;
        }
        if (len == 0) {
            list.add(s);
            return;
        }

        for (int i = 0; i < 26; i++) {
            int p1 = f1[i][aLen], p2 = f2[i][bLen];
            if (f[p1][p2] != len) {
                continue;
            }
            dfs(p1 - 1, p2 - 1, (char) (i + 'a') + s, len - 1);
        }
    }
}
