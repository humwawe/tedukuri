package ox14;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.HashSet;
import java.util.Set;

public class SnowflakeSnowSnowflakes {
    int mod = (int) (1e9 + 7);

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        Set<Snow> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int[] a = in.nextIntArray(6);
            Snow snow = new Snow(a);
            if (set.contains(snow)) {
                out.println("Twin snowflakes found.");
                return;
            }
            set.add(snow);
        }
        out.println("No two snowflakes are alike.");
    }

    class Snow {
        int[] a;

        public Snow(int[] a) {
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Snow b = (Snow) o;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    boolean f = true;
                    for (int k = 0; k < 6; k++) {
                        if (a[(i + k) % 6] != b.a[(j + k) % 6]) {
                            f = false;
                        }
                    }
                    if (f) {
                        return true;
                    }
                    f = true;
                    for (int k = 0; k < 6; k++) {
                        if (a[(i + k) % 6] != b.a[(j - k + 6) % 6]) {
                            f = false;
                        }
                    }
                    if (f) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            long sum = 0;
            long mul = 1;
            for (int i = 0; i < 6; i++) {
                sum = (sum + a[i]) % mod;
                mul = mul * a[i] % mod;
            }
            return (int) ((sum + mul) % mod);
        }
    }
}
