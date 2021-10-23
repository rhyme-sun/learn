package learn.java.concurrency.pool.forkjoin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import lombok.extern.slf4j.Slf4j;

/**
 * 统计文章每个单词出现次数
 */
@Slf4j
public class CountWordNumber {

    public static void main(String[] args) {
        // 模拟文章内容，数组中的每个元素代表文章中的一行文本，每个单词使用空格分隔
        String[] article = {"hello world", "hello simon hello me", "hello fork", "hello join", "fork join in world"};
        final Map<String, Integer> times1 = count1(article, 0, article.length - 1);
        log.info("times1: {}", times1);
        final Map<String, Integer> times2 = count2(article, 0, article.length - 1);
        log.info("times2: {}", times2);
    }

    /**
     * 递归统计文章单词数量
     */
    static Map<String, Integer> count1(String[] article, int l, int r) {
        if (l == r) {
            return countWords(article[l]);
        }
        int mid = l + ((r - l) >> 1);
        final Map<String, Integer> leftTimes = count1(article, l, mid);
        final Map<String, Integer> rightTimes = count1(article, mid + 1, r);
        return merge(leftTimes, rightTimes);
    }

    /**
     * 使用 ForkJoinPool 统计文章单词数量
     */
    static Map<String, Integer> count2(String[] article, int l, int r) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        final WordNumberTask wordNumberTask = new WordNumberTask(article, l, r);
        return forkJoinPool.invoke(wordNumberTask);
    }

    static class WordNumberTask extends RecursiveTask<Map<String, Integer>> {
        private String[] article;
        private int l;
        private int r;

        public WordNumberTask(String[] article, int l, int r) {
            this.article = article;
            this.l = l;
            this.r = r;
        }

        @Override
        protected Map<String, Integer> compute() {
            if (l == r) {
                return countWords(article[l]);
            }
            int mid = l + ((r - l) >> 1);
            WordNumberTask f1 = new WordNumberTask(article, l, mid);
            // 创建子任务
            f1.fork();
            WordNumberTask f2 = new WordNumberTask(article, mid + 1, r);
            return merge(f2.compute(), f1.join());
            // 或者使用以下方式分解合并任务，注意不要写成 f1.fork(); f2.fork(); f1.join(); f2.join(); 这种形式
            // WordNumberTask f1 = new WordNumberTask(article, l, mid);
            // WordNumberTask f2 = new WordNumberTask(article, mid + 1, r);
            // invokeAll(f1, f2);
            // return merge(f1.join(), f2.join());
        }
    }

    static Map<String, Integer> countWords(String line) {
        Map<String, Integer> times = new HashMap<>();
        final String[] words = line.split(" ");
        for (String word : words) {
            final Integer time = times.get(word);
            if (Objects.isNull(time)) {
                times.put(word, 1);
            } else {
                times.put(word, time + 1);
            }
        }
        return times;
    }

    static Map<String, Integer> merge(Map<String, Integer> leftTimes, Map<String, Integer> rightTimes) {
        Map<String, Integer> result = new HashMap<>();
        result.putAll(leftTimes);
        rightTimes.forEach((k, v) -> {
            final Integer time = result.get(k);
            if (Objects.isNull(time)) {
                result.put(k, v);
            } else {
                result.put(k, v + time);
            }
        });
        return result;
    }
}