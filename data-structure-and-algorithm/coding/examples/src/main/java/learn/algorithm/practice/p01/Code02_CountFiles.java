package learn.algorithm.practice.p01;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 题目描述如下：
 * 给定一个文件目录的路径，写一个函数统计这个目录下所有的文件数量并返回，隐藏文件也算，但是文件夹不算。
 */
public class Code02_CountFiles {

    /**
     * 递归实现
     */
    static int countFiles(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return 0;
        }
        return process(dir);
    }

    /**
     * 当前来到 dir 文件夹，考虑文件的数量
     */
    private static int process(File dir) {
        File[] files = dir.listFiles();
        int count = 0;
        for (File file : files) {
            if (file.isDirectory()) {
                count += process(file);
            } else {
                count++;
            }
        }
        return count;
    }

    /**
     * 非递归解决，使用栈深度优先遍历
     * 将文件夹根目录压栈，出栈时，统计文件夹下面的文件数量，并将文件夹下面的子文件夹压栈，直到栈中为空时统计完毕。
     */
    static int dfs(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return 0;
        }
        Stack<File> stack = new Stack<>();
        stack.push(dir);
        int count = 0;
        while (!stack.isEmpty()) {
            File cur = stack.pop();
            for (File file : cur.listFiles()) {
                if (file.isDirectory()) {
                    stack.push(file);
                }
                if (file.isFile()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 非递归实现，宽度优先遍历
     */
    static int bfs(File dir) {
        if (dir == null || !dir.isDirectory()) {
            return 0;
        }
        Queue<File> queue = new LinkedList<>();
        queue.add(dir);
        int count = 0;
        while (!queue.isEmpty()) {
            File cur = queue.poll();
            for (File file : cur.listFiles()) {
                if (file.isDirectory()) {
                    queue.add(file);
                }
                if (file.isFile()) {
                    count++;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        File dir = new File("E:\\LearnProjects\\learn");
        System.out.println(countFiles(dir));
        System.out.println(dfs(dir));
        System.out.println(bfs(dir));
    }
}
