package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

public class PowerHungryCows {
    int n;
    int depth = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        while (!idaStar(0, 1, 0)) {
            depth++;
        }
        out.println(depth);
    }

    private boolean idaStar(int dep, int x, int y) {
        if (dep == depth) {
            if (x == n) {
                return true;
            }
            return false;
        }

        if (x << (depth - dep) < n) {
            return false;
        }
        if (n % gcd(x, y) != 0) {
            return false;
        }
        int a;
        int b;
        a = x + y;
        b = y;
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = x + y;
        b = x;
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = x + x;
        b = x;
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = x + x;
        b = y;
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = y + y;
        b = x;
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = y + y;
        b = y;
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = x - y;
        b = x;
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        a = x - y;
        b = y;
        if (a < b) {
            int t = a;
            a = b;
            b = t;
        }
        if (b != 0 && idaStar(dep + 1, a, b)) {
            return true;
        }
        return false;
    }

    int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
}
