package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Stack;

public class DoubleStackSort {

    int N = 1010;
    int M = N * N;
    int[] h = new int[N];
    int[] ne = new int[M];
    int[] e = new int[M];
    int idx;
    int n;
    int[] color = new int[N];
    int inf = 0x3f3f3f3f;
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        Arrays.fill(h, -1);
        int[] a = in.nextIntArray(n);
        for (int i = 0; i < n; i++) {
            int min = inf;
            for (int j = n - 1; j > i; j--) {
                if (a[i] < a[j] && min < a[i]) {
                    add(i, j);
                    add(j, i);
                }
                min = Math.min(min, a[j]);
            }
        }
        for (int i = 0; i < n; i++) {
            if (color[i] == 0 && !dfs(i, 1)) {
                out.println("0");
                return;
            }
        }

        int now = 1;
        out.print("a ");
        s1.push(a[0]);
        for (int i = 1; i < n; i++) {
            while ((!s1.isEmpty() && s1.peek() == now) || (!s2.isEmpty() && s2.peek() == now)) {
                if (!s1.isEmpty() && s1.peek() == now) {
                    out.print("b ");
                    s1.pop();
                }
                if (!s2.isEmpty() && s2.peek() == now) {
                    if (color[i] == 1) {
                        out.print("a ");
                        s1.push(a[i]);
                        i++;
                    }
                    out.print("d ");
                    s2.pop();
                }
                now++;
            }
            if (i >= n) {
                break;
            }
            if (color[i] == 1) {
                out.print("a ");

                s1.push(a[i]);
            } else {
                out.print("c ");
                s2.push(a[i]);
            }
        }

        while ((!s1.isEmpty() && s1.peek() == now) || (!s2.isEmpty() && s2.peek() == now)) {
            if (!s2.isEmpty() && s2.peek() == now) {
                out.print("d ");
                s2.pop();
            }
            if (!s1.isEmpty() && s1.peek() == now) {
                out.print("b ");
                s1.pop();
            }
            now++;
        }


    }

    boolean dfs(int u, int c) {
        color[u] = c;
        for (int i = h[u]; i != -1; i = ne[i]) {
            int v = e[i];
            if (color[v] == 0 && !dfs(v, 3 - c)) {
                return false;
            } else if (color[v] == c) {
                return false;
            }
        }
        return true;
    }

    void add(int u, int v) {
        ne[++idx] = h[u];
        e[idx] = v;
        h[u] = idx;
    }
}
