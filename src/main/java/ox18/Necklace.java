package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

public class Necklace {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String a = in.nextString();
        String b = in.nextString();
        String resA = helper(a + a);
        String resB = helper(b + b);
        if (resA.equals(resB)) {
            out.println("Yes");
            out.println(resA);
        } else {
            out.println("No");
        }
    }

    private String helper(String str) {
        int len = str.length() / 2;
        int i = 0;
        int j = 1;
        while (i <= len && j <= len) {
            int k = 0;
            while (k < len) {
                if (str.charAt(i + k) == str.charAt(j + k)) {
                    k++;
                } else {
                    break;
                }
            }
            if (k == len) {
                break;
            }
            if (str.charAt(i + k) > str.charAt(j + k)) {
                i = i + k + 1;
                if (i == j) {
                    i++;
                }
            } else {
                j = j + k + 1;
                if (i == j) {
                    j++;
                }
            }
        }
        int res = Math.min(i, j);
        return str.substring(res, res + len);
    }
}
