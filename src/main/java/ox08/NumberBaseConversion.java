package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.LinkedList;

public class NumberBaseConversion {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int a = in.nextInt();
        int b = in.nextInt();
        String s1 = in.nextString();
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            if (Character.isDigit(c)) {
                list.add(c - '0');
            } else if (Character.isUpperCase(c)) {
                list.add(c - 'A' + 10);
            } else {
                list.add(c - 'a' + 36);
            }
        }
        LinkedList<Integer> res = new LinkedList<>();
        while (!list.isEmpty()) {
            int size = list.size();
            int r = 0;
            boolean leadZero = true;
            for (int i = 0; i < size; i++) {
                int f = list.removeFirst();
                f += r * a;
                r = f % b;
                int t = f / b;
                if (leadZero && t == 0) {
                    continue;
                }
                list.add(t);
                leadZero = false;
            }
            res.add(0, r);
        }
        StringBuilder s2 = new StringBuilder();
        for (Integer i : res) {
            if (i < 10) {
                s2.append(i);
            } else if (i < 36) {
                s2.append((char) ('A' + i - 10));
            } else {
                s2.append((char) ('a' + i - 36));
            }
        }
        out.println(a + " " + s1);
        out.println(b + " " + s2);
        out.println();
    }
}
