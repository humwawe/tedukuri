package ox27;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.*;

public class Eight {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        char[] g = new char[9];
        for (int i = 0; i < 9; i++) {
            g[i] = in.nextCharacter();
        }
        int t = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (g[i] != 'x' && g[j] != 'x') {
                    if (g[i] > g[j]) {
                        t++;
                    }
                }
            }

        }
        if (t % 2 != 0) {
            System.out.println("unsolvable");
            return;
        }
        String start = new String(g);
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        char[] ops = {'u', 'r', 'd', 'l'};
        String end = "12345678x";
        Map<String, Integer> dist = new HashMap<>();
        Set<String> vis = new HashSet<>();
        Map<String, Node> prev = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.d));
        queue.offer(new Node(f(start), start));
        dist.put(start, 0);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String state = node.state;
            if (state.equals(end)) {
                break;
            }
            if (vis.contains(state)) {
                continue;
            }
            vis.add(state);
            int x = 0, y = 0;
            for (int i = 0; i < 9; i++) {
                if (state.charAt(i) == 'x') {
                    x = i / 3;
                    y = i % 3;
                    break;
                }
            }
            String source = state;
            int step = dist.get(source);
            for (int i = 0; i < 4; i++) {
                int a = x + dx[i];
                int b = y + dy[i];
                if (a < 0 || a >= 3 || b < 0 || b >= 3) {
                    continue;
                }
                state = source;
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 9; j++) {
                    if (j == 3 * x + y) {
                        sb.append(state.charAt(3 * a + b));
                    } else if (j == 3 * a + b) {
                        sb.append(state.charAt(3 * x + y));
                    } else {
                        sb.append(state.charAt(j));
                    }
                }
                state = sb.toString();
                if (!dist.containsKey(state) || dist.get(state) > step + 1) {
                    dist.put(state, step + 1);
                    queue.offer(new Node(f(state) + dist.get(state), state));
                    prev.put(state, new Node(i, source));
                }
            }
        }
        StringBuffer res = new StringBuffer();
        while (!end.equals(start)) {
            Node node = prev.get(end);
            char c = ops[node.d];
            res.append(c);
            end = node.state;
        }
        out.println(res.reverse().toString());
    }

    int f(String state) {
        int res = 0;
        for (int i = 0; i < state.length(); i++) {
            if (state.charAt(i) != 'x') {
                int t = state.charAt(i) - '1';
                res += Math.abs(t / 3 - i / 3) + Math.abs(t % 3 - i % 3);
            }
        }
        return res;
    }

    class Node {
        int d;
        String state;

        public Node(int d, String state) {
            this.d = d;
            this.state = state;
        }
    }
}
