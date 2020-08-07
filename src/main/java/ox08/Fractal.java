package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.List;

public class Fractal {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        while (true) {
            int n = in.nextInt();
            if (n == -1) {
                return;
            }
            List<String> lists = helper(n);
            for (String list : lists) {
                out.println(list);
            }
            out.println("-");
        }

    }

    private List<String> helper(int n) {
        List<String> res = new ArrayList<>();
        if (n == 1) {
            res.add("X");
            return res;
        }
        List<String> last = helper(n - 1);
        int len = last.get(0).length();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            tmp.append(" ");
        }
        List<String> up = new ArrayList<>();
        List<String> mid = new ArrayList<>();
        for (String s : last) {
            up.add(s + tmp + s);
            mid.add(tmp + s + tmp);
        }
        res.addAll(up);
        res.addAll(mid);
        res.addAll(up);
        return res;
    }
}
