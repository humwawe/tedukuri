package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubwayTreeSystems {
    int idx = 0;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int t = in.nextInt();
        while (t-- > 0) {
            String a = in.nextString();
            String b = in.nextString();
            a = '0' + a + '1';
            b = '0' + b + '1';
            idx = 0;
            String resA = helper(a);
            idx = 0;
            String resB = helper(b);
            if (resA.equals(resB)) {
                out.println("same");
            } else {
                out.println("different");
            }
        }
    }

    private String helper(String str) {
        List<String> list = new ArrayList<>();
        idx++;
        while (str.charAt(idx) == '0') {
            list.add(helper(str));
        }
        idx++;
        Collections.sort(list);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(0);
        for (String s : list) {
            stringBuilder.append(s);
        }
        stringBuilder.append(1);
        return stringBuilder.toString();

    }
}
