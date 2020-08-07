package ox08;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class Raid {
    Point[] points;
    Point[] temp;
    double inf = 1e10;


    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        points = new Point[n + n];
        temp = new Point[n + n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.nextDouble(), in.nextDouble(), false);
        }
        for (int i = n; i < 2 * n; i++) {
            points[i] = new Point(in.nextDouble(), in.nextDouble(), true);
        }
        Arrays.sort(points, Comparator.comparingDouble(a -> a.x));
        double res = helper(0, 2 * n - 1);
        out.println(String.format("%.3f", res));
    }

    private double helper(int l, int r) {
        if (l >= r) {
            return inf;
        }
        int mid = l + r >> 1;
        double midX = points[mid].x;
        double res = Math.min(helper(l, mid), helper(mid + 1, r));
        int k = 0;
        int i = l;
        int j = mid + 1;
        while (i <= mid && j <= r) {
            if (points[i].y <= points[j].y) {
                temp[k++] = points[i++];
            } else {
                temp[k++] = points[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = points[i++];
        }
        while (j <= r) {
            temp[k++] = points[j++];
        }
        j = 0;
        for (i = l; j < k; i++, j++) {
            points[i] = temp[j];
        }

        k = 0;
        for (i = l; i <= r; i++) {
            if (points[i].x >= midX - res && points[i].x <= midX + res) {
                temp[k++] = points[i];
            }
        }

        for (i = 0; i < k; i++) {
            for (j = i + 1; j < k; j++) {
                if (temp[j].y - temp[i].y > res) {
                    break;
                }
                res = Math.min(res, dist(temp[i], temp[j]));
            }
        }
        return res;
    }

    private double dist(Point p1, Point p2) {
        if (p1.type == p2.type) {
            return inf;
        }
        double x = p1.x - p2.x;
        double y = p1.y - p2.y;
        return Math.sqrt(x * x + y * y);
    }

    class Point {
        double x, y;
        boolean type;

        public Point(double x, double y, boolean type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }
}
