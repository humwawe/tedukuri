package ox5e;

import common.io.InputReader;
import common.io.OutputWriter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PlayingCards {
    Map<Character, Integer> map = new HashMap<>();

    {
        for (int i = 2; i <= 9; i++) {
            map.put((char) ('0' + i), i);
        }
        map.put('A', 1);
        map.put('T', 10);
        map.put('J', 11);
        map.put('Q', 12);
        map.put('K', 13);
    }

    int n;
    int[] a;
    int[] b;
    BigInteger[][][][][] memo = new BigInteger[14][14][14][14][5];
    BigInteger mod = BigInteger.valueOf(1L << 62).multiply(new BigInteger("4"));

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        a = new int[14];
        b = new int[5];
        for (int i = 0; i < n; i++) {
            char c = in.nextCharacter();
            a[map.get(c)]++;
            in.nextCharacter();
        }
        for (int i = 1; i <= 13; i++) {
            b[a[i]]++;
        }

        out.printf("Case #%d: %d\n", testNumber, dfs(b[1], b[2], b[3], b[4], 0));

    }


    private BigInteger dfs(int i1, int i2, int i3, int i4, int last) {
        if (i1 == 0 && i2 == 0 && i3 == 0 && i4 == 0) {
            return BigInteger.valueOf(1);
        }
        if (memo[i1][i2][i3][i4][last] != null) {
            return memo[i1][i2][i3][i4][last];
        }
        BigInteger res = BigInteger.valueOf(0);
        if (i1 > 0) {
            int tmp = 0;
            if (last == 1) {
                tmp = 1;
            }
            res = BigInteger.valueOf(i1 - tmp).multiply(dfs(i1 - 1, i2, i3, i4, 0)).add(res).mod(mod);
        }
        if (i2 > 0) {
            int tmp = 0;
            if (last == 2) {
                tmp = 1;
            }
            res = BigInteger.valueOf((i2 - tmp) * 2).multiply(dfs(i1 + 1, i2 - 1, i3, i4, 1)).add(res).mod(mod);
        }
        if (i3 > 0) {
            int tmp = 0;
            if (last == 3) {
                tmp = 1;
            }
            res = BigInteger.valueOf((i3 - tmp) * 3).multiply(dfs(i1, i2 + 1, i3 - 1, i4, 2)).add(res).mod(mod);
        }
        if (i4 > 0) {
            res = BigInteger.valueOf(i4 * 4).multiply(dfs(i1, i2, i3 + 1, i4 - 1, 3)).add(res).mod(mod);
        }

        return memo[i1][i2][i3][i4][last] = res;
    }

}
