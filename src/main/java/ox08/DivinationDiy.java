package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.LinkedList;
import java.util.List;

public class DivinationDiy {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        LinkedList<Integer>[] lists = new LinkedList[13];
        for (int i = 0; i < 13; i++) {
            lists[i] = new LinkedList<>();
            lists[i].add(-1);
            for (int j = 0; j < 4; j++) {
                char c = in.nextCharacter();
                if (c == '0') {
                    lists[i].add(10);
                } else if (c == 'J') {
                    lists[i].add(11);
                } else if (c == 'Q') {
                    lists[i].add(12);
                } else if (c == 'K') {
                    lists[i].add(13);
                } else if (c == 'A') {
                    lists[i].add(1);
                } else {
                    lists[i].add(c - '0');
                }
            }
        }
        lists[12].removeFirst();
        while (!lists[12].isEmpty()) {
            int n = lists[12].get(0);
            lists[12].remove(0);
            while (n != 13 && n != -1) {
                lists[n - 1].addFirst(n);
                n = lists[n - 1].removeLast();
            }
        }
        int res = 0;
        for (int i = 0; i < 12; i++) {
            int cnt = 0;
            while (lists[i].getFirst() != -1) {
                cnt++;
                lists[i].removeFirst();
            }
            if (cnt == 4) {
                res++;
            }
        }
        out.println(res);
    }
}
