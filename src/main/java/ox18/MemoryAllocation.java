package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class MemoryAllocation {
    Queue<P1> waits = new ArrayDeque<>();
    TreeSet<P2> runs = new TreeSet<>(Comparator.comparingInt(a -> a.fi));
    PriorityQueue<P3> ends = new PriorityQueue<>(Comparator.comparingInt(a -> a.fi));
    int res;

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int cnt = 0;
        runs.add(new P2(-1, 1));
        runs.add(new P2(n, 1));
        while (true) {
            int t = in.nextInt();
            int m = in.nextInt();
            int p = in.nextInt();
            if (t == 0 && m == 0 && p == 0) {
                break;
            }
            finish(t);
            if (!give(t, m, p)) {
                waits.add(new P1(m, p));
                cnt++;
            }

        }
        finish((int) 2e9);
        out.println(res);
        out.println(cnt);
    }

    private boolean give(int t, int m, int p) {
        Iterator iter = runs.iterator();
        P2 first, second;
        first = (P2) iter.next();
        while (iter.hasNext()) {
            second = (P2) iter.next();
            int start = first.fi + first.se;
            if (m <= second.fi - start) {
                runs.add(new P2(start, m));
                ends.add(new P3(t + p, start));
                return true;
            }
            first = second;

        }
        return false;
    }

    private void finish(int t) {
        while (!ends.isEmpty() && ends.peek().fi <= t) {
            int f = ends.peek().fi;
            while (!ends.isEmpty() && ends.peek().fi == f) {
                P3 poll = ends.poll();
                runs.remove(new P2(poll.se, 0));
            }
            res = f;
            while (!waits.isEmpty()) {
                P1 peek = waits.peek();
                if (give(f, peek.fi, peek.se)) {
                    waits.poll();
                } else {
                    break;
                }
            }
        }
    }

    // 等待队列，first: 内存长度，second: 占用时间
    class P1 {
        int fi;
        int se;

        public P1(int fi, int se) {
            this.fi = fi;
            this.se = se;
        }
    }

    //当前进程，first: 起始下标，sercond：长度
    class P2 {
        int fi;
        int se;

        public P2(int fi, int se) {
            this.fi = fi;
            this.se = se;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            P2 p2 = (P2) o;
            return fi == p2.fi;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fi);
        }


    }

    //小根堆，first: 释放时间，second: 起始下标
    class P3 {
        int fi;
        int se;

        public P3(int fi, int se) {
            this.fi = fi;
            this.se = se;
        }
    }
}
