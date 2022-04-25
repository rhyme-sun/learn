package learn.algorithm.reservoir;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * 题目描述如下：
 * 假设有一个源源吐出不同球的机器，有一个能装下 10 个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉。
 * 如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里，即要求 i 号球在袋子满了情况下以 1/i 的概率进入袋子？
 */
@Slf4j
public class ReservoirSampling {

    /**
     * 求依次向容量为 10 的袋子放入球，要求每个球进入袋子的概率相等。
     *
     * @param ballNum 依次投放球的数量
     * @return 球投入完毕后，袋子中球的情况
     */
    static int[] addIntoBag(int ballNum) {
        int[] bag = new int[10];
        int index = 0;
        for (int i = 1; i <= ballNum; i++) {
            if (i <= 10) {
                bag[index++] = i;
            } else {
                // 袋子满时，新放入的球能够放入袋子的概率为 10/i
                boolean canIn = random(i) <= 10;
                if (canIn) {
                    // 袋子中的每个球以 1/10 的概率出袋子
                    index = (int) (Math.random() * 10);
                    bag[index] = i;
                }
            }
        }
        return bag;
    }

    /**
     * 等概率返回 1~i 中的一个数字
     */
    private static int random(int i) {
        return (int) (Math.random() * i) + 1;
    }

    public static void main(String[] args) {
        // 测试次数
        int testTimes = 100000;
        int ballNum = 50;
        // 记录在每次测试时，球在进入袋子的次数
        int[] count = new int[ballNum + 1];

        for (int i = 0; i < testTimes; i++) {
            int[] bag = addIntoBag(ballNum);
            for (int num : bag) {
                count[num]++;
            }
        }
        log.info("测试 {} 次后，每个球进入袋子的次数为：{}",testTimes, Arrays.toString(count));
    }
}
