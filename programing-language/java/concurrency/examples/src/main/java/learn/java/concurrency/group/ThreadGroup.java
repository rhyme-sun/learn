package learn.java.concurrency.group;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程组示例
 *
 * <p>
 * 2021/2/22 20:34
 */
@Slf4j
public class ThreadGroup {

    public static void main(String[] args) {
        java.lang.ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        java.lang.ThreadGroup systemThreadGroup = mainThreadGroup.getParent();
        log.info("MainThreadGroup's name is {}", mainThreadGroup.getName()); // main
        log.info("MainThreadGroup's parent name is {}", systemThreadGroup.getName()); // system
        java.lang.ThreadGroup subGroup1 = new java.lang.ThreadGroup("subGroup1");
        java.lang.ThreadGroup subGroup2 = new java.lang.ThreadGroup(subGroup1, "subGroup2");
        log.info("SubGroup1' parent name is {}", subGroup1.getParent().getName()); // main
        log.info("SubGroup2's parent name is {}", subGroup2.getParent().getName()); // subGroup1

        Thread t1 = new Thread(subGroup1, new ThreadInGroup(), "Thread1");
        Thread t2 = new Thread(subGroup1, new ThreadInGroup(), "Thread2");
        Thread t3 = new Thread(subGroup2, new ThreadInGroup(), "Thread3");
        t1.start();
        t2.start();
        t3.start();
        // 打印线程组相关信息
        systemThreadGroup.list();
        mainThreadGroup.list();
        subGroup1.list();
        subGroup2.list();
        // 当前线程组和子线程组活跃的线程数量
        log.info("ActiveCount : {}", systemThreadGroup.activeCount());
        // 当前线程组和子线程组活跃的线程组的数量
        log.info("ActiveGroupCount : {}", systemThreadGroup.activeGroupCount());
    }

    static class ThreadInGroup implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
