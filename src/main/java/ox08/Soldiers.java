package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class Soldiers {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            b[i] = in.nextInt();
        }
        Arrays.sort(a);
        Arrays.sort(b);
        for (int i = 0; i < n; i++) {
            a[i] -= i;
        }
        Arrays.sort(a);
        int x = a[n / 2];
        int y = b[n / 2];
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += Math.abs(a[i] - x);
            res += Math.abs(b[i] - y);
        }
        out.println(res);
    }
}
