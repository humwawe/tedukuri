package ox02;

import common.io.InputReader;
import common.io.OutputWriter;

public class RecursivePermutation {
    boolean[] vis = new boolean[10];
    int[] nums = new int[10];
    int n;
    OutputWriter out;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        this.out = out;
        n = in.nextInt();
        helper(0);
    }

    private void helper(int i) {
        if (i == n) {
            for (int j = 0; j < n; j++) {
                out.print(nums[j] + " ");
            }
            out.println();
            return;
        }
        for (int j = 1; j <= n; j++) {
            if (!vis[j]) {
                nums[i] = j;
                vis[j] = true;
                helper(i + 1);
                vis[j] = false;
            }
        }
    }
}
