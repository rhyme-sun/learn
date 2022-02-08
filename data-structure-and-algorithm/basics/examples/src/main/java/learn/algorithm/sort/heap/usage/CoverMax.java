package learn.algorithm.sort.heap.usage;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import lombok.extern.slf4j.Slf4j;

/**
 * 最大线段重合问题：
 * 给定很多线段，每个线段都用两个数 [start, end] 来表示线段开始位置和结束位置，规定：
 * <p>
 * 左右都是闭区间；
 * 线段开始和结束的位置都是整数，且 start > end；
 * 线段重合的区域必须 >=1；
 * <p>
 * 问返回的线段最多重合区域中，包含了几条线段。
 */
@Slf4j
public class CoverMax {

    /**
     * 一般实现：
     * 找到线段中的最小起点和最大终点，从起点开始遍历每条单位线段（长度为 1 的线段），记录单位线段被线段包含的次数 c，
     * 直到单位线段遍历完毕，此时最大的 c 就是最大线段重合数量。
     */
    static int maxCover(Line[] lines) {
        if (lines == null || lines.length == 0) {
            return 0;
        }
        if (lines.length == 1) {
            return 1;
        }

        int maxEnd = lines[0].end;
        int minStart = lines[0].start;
        for (int i = 1; i < lines.length; i++) {
            maxEnd = Math.max(maxEnd, lines[i].end);
            minStart = Math.min(minStart, lines[i].start);
        }

        int coverMax = 1;
        for (int i = minStart; i < maxEnd; i++) {
            int cover = 0;
            for (Line line : lines) {
                if (i >= line.start && i + 1 <= line.end) {
                    cover++;
                }
            }
            coverMax = Math.max(cover, coverMax);
        }
        return coverMax;
    }

    /**
     * 使用堆实现：
     * 将线段按照起点升序排序，并创建一个小根堆；
     * 遍历线段，判断线段起点和堆顶元素的大小，如果线段起点大于或等于堆顶元素，则将堆顶元素移除，继续比较堆顶元素，直到堆为空或者起点小于堆顶元素，
     * 将线段的终点放入堆中；
     * 统计此事堆中元素的数量就是和当前线段重合线段的数量 c；
     * 线段遍历完毕后，最大的 c 就是最大线段重合数。
     */
    static int maxCover2(Line[] lines) {
        if (lines == null || lines.length == 0) {
            return 0;
        }
        if (lines.length == 1) {
            return 1;
        }

        Arrays.sort(lines, new LineStartComparator());
        PriorityQueue<Integer> lineEndHeap = new PriorityQueue<>();

        int coverMax = 1;
        for (Line line : lines) {
            while (!lineEndHeap.isEmpty() && line.start >= lineEndHeap.peek()) {
                lineEndHeap.poll();
            }
            lineEndHeap.add(line.end);
            coverMax = Math.max(coverMax, lineEndHeap.size());
        }

        return coverMax;
    }

    private static Line[] generateLines(int n, int l, int r) {
        int size = (int) (Math.random() * n) + 1;
        Line[] lines = new Line[size];

        for (int i = 0; i < size; i++) {
            int a = l + (int) (Math.random() * (r - l + 1));
            int b = l + (int) (Math.random() * (r - l + 1));
            if (a == b) {
                b = a + 1;
            }
            int start = Math.min(a, b);
            int end = Math.max(a, b);
            lines[i] = new Line(start, end);
        }
        return lines;
    }

    static class Line {

        private int start;
        private int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + ']';
        }
    }

    static class LineStartComparator implements Comparator<Line> {

        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static void main(String[] args) {
        int size = 100;
        int l = 0;
        int r = 200;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            final Line[] lines = generateLines(size, l, r);
            final int maxCover = maxCover(lines);
            final int maxCover2 = maxCover2(lines);
            if (maxCover != maxCover2) {
                log.info("Oops!");
                log.info("{}", Arrays.toString(lines));
                log.info("{}", maxCover);
                log.info("{}", maxCover2);
                break;
            }
        }
        log.info("Finish!");
    }
}
