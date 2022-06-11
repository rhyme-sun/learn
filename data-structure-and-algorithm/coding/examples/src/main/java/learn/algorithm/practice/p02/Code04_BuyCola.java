package learn.algorithm.practice.p02;

import java.util.Arrays;

/**
 * 题目描述如下：
 * 贩卖机只支持硬币支付，且收退都只支持 10 ，50，100 三种面额，一次购买只能出一瓶可乐，且投钱和找零都遵循优先使用大钱的原则。
 * 需要购买的可乐数量是 m，其中手头拥有的 10、50、100 的数量分别为 a、b、c，可乐的价格是 x（x 是 10 的倍数）。请计算出需要投入硬币次数？
 */
public class Code04_BuyCola {

    /**
     * 买可乐，每次买一瓶可乐，看买完 amount 瓶投币的数量
     *
     * @param coins  面值数组 [100,50,10]
     * @param count  面值拥有的数量
     * @param price  可乐单价
     * @param amount 购买可乐数量
     * @return 投币次数
     */
    static int putTimes(int[] coins, int[] count, int price, int amount) {
        // 考虑从大面值到小面值依次投币，看购买指定数量的可乐，需要投币的数量
        int putTimes = 0;
        while (amount > 0) {
            int buyOne = buyOne(coins, count, price);
            if (buyOne == -1) {
                return -1;
            }
            putTimes += buyOne;
            amount--;
        }
        return putTimes;
    }

    /**
     * 计算买一瓶可乐需要投币的数量，剩余的钱如果一瓶也买不了，返回 -1。
     */
    private static int buyOne(int[] coins, int[] count, int price) {
        // 前面大面值剩下的但不足以买一瓶可乐的钱
        int preMoney = 0;
        // 前面大面值钱剩下的总张数
        int preCount = 0;

        for (int i = 0; i < count.length; i++) {
            // 最大可以买可乐的面值
            if (count[i] != 0) {
                // 如果当前面值可以买一瓶可乐
                if (coins[i] * count[i] + preMoney >= price) {
                    // 买一瓶可乐需要多少张当前面值
                    int times = 1;
                    while (coins[i] * times + preMoney < price) {
                        times++;
                    }
                    count[i] -= times;
                    // 找零
                    giveRest(coins, count, coins[i] * times - (price - preMoney));
                    return times + preCount;
                } else {
                    // 如果最大面值的钱不够买一瓶可乐，去到下一个面值，但有可能大面值没有用完，将这些钱用于下个面值的购买行为
                    preMoney += coins[i] * count[i];
                    preCount += count[i];
                    count[i] = 0;
                }
            }
        }
        return -1;
    }

    /**
     * 找零，rest 表示零钱，找零也要从大面值开始找
     */
    private static void giveRest(int[] coins, int[] count, int rest) {
        for (int i = 0; i < coins.length; i++) {
            if (rest == 0) {
                break;
            }
            if (coins[i] <= rest) {
                int zhang = rest / coins[i];
                count[i] += zhang;
                rest = rest - zhang * coins[i];
            }
        }
    }

    /**
     * 更好的方法，不是每次买一瓶讨论情况，而是从最大的面值考虑，考虑用同一种面值一共可以买的可乐数量。
     * 这样讨论情况就由原来的每瓶到现在的每种面值。
     */
    static int putTimes2(int[] coins, int[] count, int price, int amount) {
        // 总共需要多少次投币
        int puts = 0;
        // 之前面值的钱还剩下多少总钱数
        int preQianRest = 0;
        // 之前面值的钱还剩下多少总张数
        int preQianZhang = 0;
        for (int i = 0; i < 3 && amount != 0; i++) {
            // 要用之前剩下的钱、当前面值的钱，共同买第一瓶可乐
            // 之前的面值剩下多少钱，是preQianRest
            // 之前的面值剩下多少张，是preQianZhang
            // 之所以之前的面值会剩下来，一定是剩下的钱，一直攒不出一瓶可乐的单价
            // 当前的面值付出一些钱+之前剩下的钱，此时有可能凑出一瓶可乐来
            // 那么当前面值参与搞定第一瓶可乐，需要掏出多少张呢？就是curQianFirstBuyZhang
            int curQianFirstBuyZhang = (price - preQianRest + coins[i] - 1) / coins[i];
            if (count[i] >= curQianFirstBuyZhang) { // 如果之前的钱和当前面值的钱，能凑出第一瓶可乐
                // 凑出来了一瓶可乐也可能存在找钱的情况，
                giveRest(coins, count, i + 1, (preQianRest + coins[i] * curQianFirstBuyZhang) - price, 1);
                puts += curQianFirstBuyZhang + preQianZhang;
                count[i] -= curQianFirstBuyZhang;
                amount--;
            } else { // 如果之前的钱和当前面值的钱，不能凑出第一瓶可乐
                preQianRest += coins[i] * count[i];
                preQianZhang += count[i];
                continue;
            }
            // 凑出第一瓶可乐之后，当前的面值有可能能继续买更多的可乐
            // 以下过程就是后续的可乐怎么用当前面值的钱来买
            // 用当前面值的钱，买一瓶可乐需要几张
            int curQianBuyOneColaZhang = (price + coins[i] - 1) / coins[i];
            // 用当前面值的钱，一共可以搞定几瓶可乐
            int curQianBuyColas = Math.min(count[i] / curQianBuyOneColaZhang, amount);
            // 用当前面值的钱，每搞定一瓶可乐，收货机会吐出多少零钱
            int oneTimeRest = coins[i] * curQianBuyOneColaZhang - price;
            // 每次买一瓶可乐，吐出的找零总钱数是oneTimeRest
            // 一共买的可乐数是curQianBuyColas，所以把零钱去提升后面几种面值的硬币数，
            // 就是giveRest的含义
            giveRest(coins, count, i + 1, oneTimeRest, curQianBuyColas);
            // 当前面值去搞定可乐这件事，一共投了几次币
            puts += curQianBuyOneColaZhang * curQianBuyColas;
            // 还剩下多少瓶可乐需要去搞定，继续用后面的面值搞定去吧
            amount -= curQianBuyColas;
            // 当前面值可能剩下若干张，要参与到后续买可乐的过程中去，
            // 所以要更新preQianRest和preQianZhang
            count[i] -= curQianBuyOneColaZhang * curQianBuyColas;
            preQianRest = coins[i] * count[i];
            preQianZhang = count[i];
        }
        return amount == 0 ? puts : -1;
    }

    private static void giveRest(int[] qian, int[] zhang, int i, int oneTimeRest, int times) {
        for (; i < 3; i++) {
            zhang[i] += (oneTimeRest / qian[i]) * times;
            oneTimeRest %= qian[i];
        }
    }

    public static void main(String[] args) {
        // testOnce();
        test();
    }

    private static void testOnce() {
        int[] coins = new int[]{100, 50, 10};
        int[] count1 = new int[]{4, 4, 0};
        int[] count2 = new int[]{4, 4, 0};
        int price = 180;
        int amount = 9;
        int ans1 = putTimes(coins, count1, price, amount);
        int ans2 = putTimes2(coins, count2, price, amount);
        System.out.println(ans1);
        System.out.println(ans2);
        System.out.println(Arrays.toString(count1));
        System.out.println(Arrays.toString(count2));
    }

    private static void test() {
        int testTimes = 1000;
        int zhangMax = 10;
        int colaMax = 10;
        int priceMax = 20;

        int[] coins = new int[]{100, 50, 10};
        for (int i = 0; i < testTimes; i++) {
            int amount = (int) (Math.random() * colaMax);
            int price = ((int) (Math.random() * priceMax) + 1) * 10;

            int a = (int) (Math.random() * zhangMax);
            int b = (int) (Math.random() * zhangMax);
            int c = (int) (Math.random() * zhangMax);

            int[] count1 = new int[]{a, b, c};
            int[] count2 = new int[]{a, b, c};
            int ans1 = putTimes(coins, count1, price, amount);
            int ans2 = putTimes2(coins, count2, price, amount);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                System.out.println(amount);
                System.out.println(price);
                System.out.println(a + "," + b + "," + c);
                System.out.println(ans1);
                System.out.println(Arrays.toString(count1));
                System.out.println(ans2);
                System.out.println(Arrays.toString(count2));
                break;
            }
        }
        System.out.println("Finish!");
    }
}
