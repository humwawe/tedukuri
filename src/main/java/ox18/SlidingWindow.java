package ox18;

import common.io.InputReader;
import common.io.OutputWriter;

import java.util.LinkedList;

public class SlidingWindow {
    public void solve(int testNumber, InputReader in, OutputWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        int[] a = new int[n];
        LinkedList<Integer> min = new LinkedList<>();
        LinkedList<Integer> max = new LinkedList<>();
        StringBuilder minRes = new StringBuilder();
        StringBuilder maxRes = new StringBuilder();
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            while (!min.isEmpty() && i - min.getFirst() >= k) {
                min.removeFirst();
            }
            while (!min.isEmpty() && a[i] <= a[min.getLast()]) {
                min.removeLast();
            }
            min.add(i);
            while (!max.isEmpty() && i - max.getFirst() >= k) {
                max.removeFirst();
            }
            while (!max.isEmpty() && a[i] >= a[max.getLast()]) {
                max.removeLast();
            }
            max.add(i);
            if (i >= k - 1) {
                minRes.append(a[min.peek()]).append(" ");
                maxRes.append(a[max.peek()]).append(" ");
            }
        }
        out.println(minRes.toString());
        out.println(maxRes.toString());
    }
}
