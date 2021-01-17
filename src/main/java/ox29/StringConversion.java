package ox29;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class StringConversion {
    int N = 6;
    String[] a = new String[N];
    String[] b = new String[N];
    int n;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        String start = in.nextString();
        String end = in.nextString();
        for (int i = 0; ; i++) {
            String tmp;
            try {
                tmp = in.nextString();
            } catch (Throwable e) {
                break;
            }
            n++;
            a[i] = tmp;
            b[i] = in.nextString();
        }
        int step = bfs(start, end);
        if (step > 10) {
            out.println("NO ANSWER!");
        } else {
            out.println(step);
        }
    }

    private int bfs(String start, String end) {
        Queue<String> qa = new ArrayDeque<>();
        Queue<String> qb = new ArrayDeque<>();
        Map<String, Integer> da = new HashMap<>();
        Map<String, Integer> db = new HashMap<>();
        da.put(start, 0);
        db.put(end, 0);
        qa.add(start);
        qb.add(end);
        while (!qa.isEmpty() && !qb.isEmpty()) {
            int t = 0;
            if (qa.size() <= qb.size()) {
                t = extend(qa, da, db, a, b);
            } else {
                t = extend(qb, db, da, b, a);
            }
            if (t <= 10) {
                return t;
            }
        }
        return 11;
    }

    private int extend(Queue<String> q, Map<String, Integer> da, Map<String, Integer> db, String[] a, String[] b) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
            String cur = q.poll();
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < cur.length(); k++) {
                    if (cur.substring(k, Math.min(k + a[j].length(), cur.length())).equals(a[j])) {
                        String ns = cur.substring(0, k) + b[j] + cur.substring(k + a[j].length());
                        if (db.containsKey(ns)) {
                            return da.get(cur) + db.get(ns) + 1;
                        }
                        if (da.containsKey(ns)) {
                            continue;
                        }
                        da.put(ns, da.get(cur) + 1);
                        q.add(ns);
                    }
                }
            }
        }
        return 11;
    }
}
