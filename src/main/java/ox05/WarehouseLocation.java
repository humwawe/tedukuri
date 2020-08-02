package ox05;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;

public class WarehouseLocation {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        Arrays.sort(a);
        int mid = a.length / 2;
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += Math.abs(a[i] - a[mid]);
        }
        out.println(res);
    }
}
