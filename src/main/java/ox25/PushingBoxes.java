package ox25;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.ArrayDeque;
import java.util.Queue;

public class PushingBoxes {
    char[][] map;
    int n, m;
    char dirBox[] = {'N', 'S', 'W', 'E'};
    char dirMan[] = {'n', 's', 'w', 'e'};
    int dx[] = {-1, 1, 0, 0};
    int dy[] = {0, 0, -1, 1};

    public void solve(int testNumber, InputReader in, OutputWriter out) {
        n = in.nextInt();
        m = in.nextInt();
        if (n == 0 && m == 0) {
            return;
        }
        map = new char[n][m];
        Point box = new Point();
        Point tar = new Point();
        for (int i = 0; i < n; i++) {
            map[i] = in.nextString().toCharArray();
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 'S') {
                    box.mr = i;
                    box.mc = j;
                }
                if (map[i][j] == 'B') {
                    box.r = i;
                    box.c = j;
                    box.way = new StringBuilder();
                }
                if (map[i][j] == 'T') {
                    tar.r = i;
                    tar.c = j;
                    tar.way = new StringBuilder();
                }
            }
        }
        out.println("Maze #" + testNumber);
        out.println(boxBfs(box, tar));
        out.println();

    }


    boolean judgeEdge(int r, int c) {
        return r >= 0 && r < n && c >= 0 && c < m && map[r][c] != '#';
    }

    String manBfs(Point beg, Point end) {
        Queue<Point> que = new ArrayDeque<>();
        boolean[][] vis = new boolean[n][m];
        beg.way = new StringBuilder();
        que.add(beg);
        vis[beg.mr][beg.mc] = true;
        while (!que.isEmpty()) {
            Point cur = que.poll();
            if (cur.mr == end.mr && cur.mc == end.mc) {
                return cur.way.toString();
            }
            for (int i = 0; i < 4; i++) {
                Point next = new Point();
                next.mr = cur.mr + dx[i];
                next.mc = cur.mc + dy[i];
                next.way = cur.way;
                if (!judgeEdge(next.mr, next.mc) || vis[next.mr][next.mc] || (next.mr == beg.r && next.mc == beg.c)) {
                    continue;
                }
                next.way.append(dirMan[i]);
                que.add(next);
                vis[next.mr][next.mc] = true;
            }
        }
        return "Impossible.";
    }

    String boxBfs(Point box, Point tar) {
        Queue<Point> que = new ArrayDeque<>();
        boolean[][][] vis = new boolean[4][n][m];
        que.add(box);
        while (!que.isEmpty()) {
            Point cur = que.poll();
            if (cur.r == tar.r && cur.c == tar.c) {
                return cur.way.toString();
            }
            for (int i = 0; i < 4; i++) {
                Point next = new Point();
                // 箱子的下一个坐标
                next.r = cur.r + dx[i];
                next.c = cur.c + dy[i];
                // 人的下一个坐标
                next.mr = cur.r - dx[i];
                next.mc = cur.c - dy[i];
                next.way = cur.way;
                if (!judgeEdge(next.r, next.c)) {
                    continue;
                }
                if (!judgeEdge(next.mr, next.mc)) {
                    continue;
                }
                // 该点已从其他点往 i 方向得到过
                if (vis[i][next.r][next.c]) {
                    continue;
                }
                String mway = manBfs(cur, next);
                if (!mway.equals("Impossible.")) {
                    next.way.append(mway);
                    next.way.append(dirBox[i]);
                    // 箱子已经移动，人的位置为原来箱子的位置
                    next.mr = cur.r;
                    next.mc = cur.c;
                    que.add(next);
                    vis[i][next.r][next.c] = true;
                }
            }
        }
        return "Impossible.";
    }

    class Point {
        StringBuilder way;
        int r, c; // 箱子的位置
        int mr, mc; // 人的位置
    }

}
