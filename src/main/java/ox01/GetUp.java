package ox01;

import common.io.InputReader;
import common.io.OutputWriter;

public class GetUp {
    String[] commands = new String[(int) (5e5 + 5)];
    int[] nums = new int[(int) (5e5 + 5)];
    int n;
    int m;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        for (int i = 0; i < n; i++) {
            commands[i] = in.nextString();
            nums[i] = in.nextInt();
        }
        int val = 0;
        int res = 0;
        for (int bit = 29; bit >= 0; bit--) {
            int res0 = helper(bit, 0);
            int res1 = helper(bit, 1);
            if (res1 > res0 && val + (1 << bit) <= m) {
                val += 1 << bit;
                res += res1 << bit;
            } else {
                res += res0 << bit;
            }
        }
        out.println(res);
    }

    private int helper(int bit, int now) {
        for (int i = 0; i < n; i++) {
            int x = nums[i] >> bit & 1;
            if (commands[i].equals("OR")) {
                now |= x;
            } else if (commands[i].equals("XOR")) {
                now ^= x;
            } else {
                now &= x;
            }
        }
        return now;
    }
}
