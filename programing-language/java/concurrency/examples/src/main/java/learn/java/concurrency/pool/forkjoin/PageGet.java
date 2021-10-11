package learn.java.concurrency.pool.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool 示例，从第一页分开始分页获取数据，直到获取到最后一页数据，再汇总分页查询的结果。
 *
 * @author ykthree
 * 2021/7/8
 */
public class PageGet extends RecursiveTask<List<Integer>> {

    private int pageSize;
    private int page;

    public PageGet(int pageSize, int page) {
        this.pageSize = pageSize;
        this.page = page;
    }

    @Override
    protected List<Integer> compute() {
        //

        return null;
    }

    public List<Integer> pageGet(int page, int pageSize) {
        if (page == 1) {
            return new ArrayList<>(pageSize);
        } else if (page == 2) {
            return new ArrayList<>(pageSize);
        } else {
            return new ArrayList<>(5);
        }
    }
}
