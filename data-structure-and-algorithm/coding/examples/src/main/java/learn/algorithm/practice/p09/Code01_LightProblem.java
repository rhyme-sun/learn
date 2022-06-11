package learn.algorithm.practice.p09;

import java.util.Arrays;

import learn.algorithm.coding.comparator.ArrayComparator;

/**
 * 题目描述如下：
 * 给定一个数组 arr，长度为 N，arr 中的值不是 0 就是 1。`arr[i]` 表示第 i 盏灯的状态，0 代表灭灯，1 代表亮灯。
 * 每盏灯都有开关，i 为中间位置时，i 号灯的开关能影响 i-1、i 和 i+1；
 * 0 号灯的开关只能影响 0 和 1 位置的灯；
 * N-1 号灯的开关只能影响 N-2 和 N-1 位置的灯。
 * <p>
 * 注：这里的影响是指会影响周围开关的状态，比如如果 0、1、2 位置开关状态分别为 `(0,1,0)`，按下 1 位置开关后状态变为 `(1,0,1)`。
 * <p>
 * 问题一：如果 N 盏灯排成一条直线，请问最少按下多少次开关？
 * 问题二：如果 N 盏灯排成一个圈（0 位置能影响到 N-1 位置，N-1 位置也能影响到 0 位置），请问最少按下多少次开关，能让灯都亮起来？
 */
public class Code01_LightProblem {

    /**
     * 线性点灯的暴力版本，每个位置都尝试点灯或不点灯，看那种情况下灯能全部点亮，且数量最少
     */
    static int noLoopMinStep1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        return process1(arr, 0);
    }

    /**
     * 当前来到 i 位置的开关，考虑 i 位置开关按或不按，能让全部灯点亮的最少次数
     * 如果不能点亮所有灯返回系统最大值（代表无效值）
     */
    private static int process1(int[] arr, int i) {
        if (i == arr.length) {
            return valid(arr) ? 0 : Integer.MAX_VALUE;
        }
        int p1 = process1(arr, i + 1);
        change(arr, i);
        int p2 = process1(arr, i + 1);
        change(arr, i);
        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    /**
     * i 位置开关按下后，改变周围灯的状态
     */
    private static void change(int[] arr, int i) {
        if (i == 0) {
            arr[0] ^= 1;
            arr[1] ^= 1;
        } else if (i == arr.length - 1) {
            arr[i - 1] ^= 1;
            arr[i] ^= 1;
        } else {
            arr[i - 1] ^= 1;
            arr[i] ^= 1;
            arr[i + 1] ^= 1;
        }
    }

    /**
     * 判断灯是否被全部点亮
     */
    private static boolean valid(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 线性点灯改良递归版本，添加贪心策略
     */
    static int noLoopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        // 因为递归函数是从 1 位置开始讨论的，所以这里针对 0 位置按下开关的情况，有以下两种可能性：
        // 不按 0 位置的开关
        int p1 = process2(arr, 1);
        // 按下 0 位置的开关
        arr[0] ^= 1;
        arr[1] ^= 1;
        int p2 = process2(arr, 1);
        if (p2 != Integer.MAX_VALUE) {
            p2++;
        }
        return Math.min(p1, p2);
    }

    /**
     * 当前来到 index 位置（index 从 1 开始），且假设 [0,index-2] 范围内的灯全部点亮，求点亮剩余灯需要按下开关的次数
     * 无法全部点亮返回系统最大值
     */
    private static int process2(int[] arr, int index) {
        if (index == arr.length - 1) {
            return arr[index - 1] == arr[index] ? arr[index - 1] ^ 1 : Integer.MAX_VALUE;
        }
        // 前一个灯已经点亮了，index 位置的灯不能按
        if (arr[index - 1] == 1) {
            return process2(arr, index + 1);
        } else {
            // 前一个位置的灯没点亮，index 位置的灯必须按下
            arr[index - 1] ^= 1;
            arr[index] ^= 1;
            arr[index + 1] ^= 1;
            int ans = process2(arr, index + 1);
            arr[index - 1] ^= 1;
            arr[index] ^= 1;
            arr[index + 1] ^= 1;
            return ans == Integer.MAX_VALUE ? ans : ans + 1;
        }
    }

    /**
     * 上述递归版本的进一步改良，使用两个变量来记录上次和当前位置灯的状态，避免修改样本数组
     */
    static int noLoopMinStep3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] ^ 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        // 因为递归函数是从 1 位置开始讨论的，所以这里针对 0 位置按下开关的情况，有以下两种可能性：
        // 不按 0 位置的开关
        int p1 = process3(arr, 1, arr[0], arr[1]);
        // 按下 0 位置的开关
        int p2 = process3(arr, 1, arr[0] ^ 1, arr[1] ^ 1);
        if (p2 != Integer.MAX_VALUE) {
            p2++;
        }
        return Math.min(p1, p2);
    }

    /**
     * 当前来到 index 位置（index 从 1 开始），且假设 `[0,index-2]` 范围内的灯已经全部点亮，求点亮剩余灯需要按下开关的次数
     *
     * @param index     下个位置
     * @param preStatus 前一个位置是否被点亮
     * @param curStatus 当前位置是否被点亮
     * @return 全部灯被点了需要按下开关的次数
     */
    private static int process3(int[] arr, int index, int preStatus, int curStatus) {
        // 当前来到最后一个开关位置
        if (index == arr.length - 1) {
            return preStatus == curStatus ? (curStatus ^ 1) : (Integer.MAX_VALUE);
        }
        // 前一个位置的灯亮了，一定不能按下开关
        if (preStatus == 1) {
            return process3(arr, index + 1, curStatus, arr[index + 1]);
        } else {
            // 前一个位置的灯没亮，一定要按下开关
            int next = process3(arr, index + 1, curStatus ^ 1, arr[index + 1] ^ 1);
            return next == Integer.MAX_VALUE ? next : (next + 1);
        }
    }

    /**
     * 线性点灯的迭代版本
     */
    static int noLoopMinStep4(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        // 因为递归函数是从 1 位置开始讨论的，所以这里针对 0 位置按下开关的情况，有以下两种可能性：
        // 不按 0 位置的开关
        int p1 = traceNoLoop(arr, arr[0], arr[1]);
        // 按下 0 位置的开关
        int p2 = traceNoLoop(arr, arr[0] ^ 1, arr[1] ^ 1);
        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    /**
     * 从 1 位置开始讨论，点亮全部灯需要按下开关的次数
     */
    private static int traceNoLoop(int[] arr, int preStatus, int curStatus) {
        int index = 1;
        int op = 0;
        while (index != arr.length - 1) {
            if (preStatus == 1) {
                preStatus = curStatus;
                curStatus = arr[++index];
            } else {
                op++;
                preStatus = curStatus ^ 1;
                curStatus = arr[++index] ^ 1;
            }
        }
        return (preStatus == curStatus) ? (op + (curStatus ^ 1)) : Integer.MAX_VALUE;
    }


    /**
     * 环形电灯暴力递归版本，每个位置都尝试点灯或不点灯，看那种情况下灯能全部点亮，且数量最少
     */
    static int loopMinStep1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        return process(arr, 0);
    }

    private static int process(int[] arr, int i) {
        if (i == arr.length) {
            return valid(arr) ? 0 : Integer.MAX_VALUE;
        }
        int p1 = process(arr, i + 1);
        change2(arr, i);
        int p2 = process(arr, i + 1);
        change2(arr, i);
        p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
        return Math.min(p1, p2);
    }

    private static void change2(int[] arr, int i) {
        arr[lastIndex(i, arr.length)] ^= 1;
        arr[i] ^= 1;
        arr[nextIndex(i, arr.length)] ^= 1;
    }

    private static int lastIndex(int i, int N) {
        return i == 0 ? (N - 1) : (i - 1);
    }

    private static int nextIndex(int i, int N) {
        return i == N - 1 ? 0 : (i + 1);
    }

    /**
     * 环形电灯递归优化
     */
    static int loopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] == arr[1] ? arr[0] ^ 1 : Integer.MAX_VALUE;
        }
        if (arr.length == 3) {
            return (arr[0] == arr[1] && arr[1] == arr[2]) ? arr[0] ^ 1 : Integer.MAX_VALUE;
        }
        // 由于递归函数是从 2 位置开始讨论的，正对 0、1 位置开关的按下状态分为以下四种情况：
        // 0 没按 1 没按
        int p1 = process2(arr, 2, arr[1], arr[2], arr[0]);
        // 0 没按 1 按了
        int p2 = process2(arr, 2, arr[1] ^ 1, arr[2] ^ 1, arr[0] ^ 1);
        p2 = p2 == Integer.MAX_VALUE ? p2 : p2 + 1;
        // 0 按了，1 没按
        arr[arr.length - 1] ^= 1;
        int p3 = process2(arr, 2, arr[1] ^ 1, arr[2], arr[0] ^ 1);
        p3 = p3 == Integer.MAX_VALUE ? p3 : p3 + 1;
        arr[arr.length - 1] ^= 1;
        // 0 按了 1 按了
        arr[arr.length - 1] ^= 1;
        int p4 = process2(arr, 2, arr[1], arr[2] ^ 1, arr[0]);
        arr[arr.length - 1] ^= 1;
        p4 = p4 == Integer.MAX_VALUE ? p4 : p4 + 2;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }

    /**
     * 当前来到 index 位置（index 从 2 开始），且假设 [1,index-2] 位置的灯全部点亮，求点亮全部灯需要按下开关的次数
     *
     * @param index       当前位置，从 2 开始
     * @param preStatus   前个位置灯的状态
     * @param curStatus   后个位置灯的状态
     * @param firstStatus 第一个位置灯的状态
     * @return 灯全部点亮需要按下开关的次数，无法点亮返回系统最大值
     */
    private static int process2(int[] arr, int index, int preStatus, int curStatus, int firstStatus) {
        if (index == arr.length - 1) {
            return (preStatus == curStatus && preStatus == firstStatus) ? firstStatus ^ 1 : Integer.MAX_VALUE;
        }
        // 前一个位置的灯亮着，index 位置开关不能按
        if (preStatus == 1) {
            return process2(arr, index + 1, curStatus, arr[index + 1], firstStatus);
        } else {
            int ans = process2(arr, index + 1, curStatus ^ 1, arr[index + 1] ^ 1, firstStatus);
            return ans == Integer.MAX_VALUE ? ans : ans + 1;
        }
    }

    /**
     * 环形电灯迭代版本
     */
    static int loopMinStep3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] == arr[1] ? arr[0] ^ 1 : Integer.MAX_VALUE;
        }
        if (arr.length == 3) {
            return (arr[0] == arr[1] && arr[1] == arr[2]) ? arr[0] ^ 1 : Integer.MAX_VALUE;
        }
        // 由于递归函数是从 2 位置开始讨论的，正对 0、1 位置开关的按下状态分为以下四种情况：
        // 0 没按 1 没按
        int p1 = traceLoop(arr, arr[1], arr[2], arr[0]);
        // 0 没按 1 按了
        int p2 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[0] ^ 1);
        p2 = p2 == Integer.MAX_VALUE ? p2 : p2 + 1;
        // 0 按了，1 没按
        arr[arr.length - 1] ^= 1;
        int p3 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[0] ^ 1);
        p3 = p3 == Integer.MAX_VALUE ? p3 : p3 + 1;
        arr[arr.length - 1] ^= 1;
        // 0 按了 1 按了
        arr[arr.length - 1] ^= 1;
        int p4 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[0]);
        arr[arr.length - 1] ^= 1;
        p4 = p4 == Integer.MAX_VALUE ? p4 : p4 + 2;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }

    private static int traceLoop(int[] arr, int preStatus, int curStatus, int firstStatus) {
        int index = 2;
        int op = 0;
        while (index < arr.length - 1) {
            if (preStatus == 1) {
                preStatus = curStatus;
                curStatus = arr[++index];
            } else {
                op++;
                preStatus = curStatus ^ 1;
                curStatus = (arr[++index] ^ 1);
            }
        }
        return (preStatus == curStatus && curStatus == firstStatus) ? (op + (firstStatus ^ 1)) : Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        int testTime = 100;
        int maxSize = 12;

        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize);
            int ans1 = noLoopMinStep1(ArrayComparator.copyArray(arr));
            int ans2 = noLoopMinStep2(ArrayComparator.copyArray(arr));
            int ans3 = noLoopMinStep3(ArrayComparator.copyArray(arr));
            int ans4 = noLoopMinStep4(ArrayComparator.copyArray(arr));
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println("Oops1!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }

        for (int i = 0; i < testTime; i++) {
            int[] arr = ArrayComparator.generateRandomArray(maxSize);
            int ans1 = loopMinStep1(ArrayComparator.copyArray(arr));
            int ans2 = loopMinStep2(ArrayComparator.copyArray(arr));
            int ans3 = loopMinStep3(ArrayComparator.copyArray(arr));
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops2!");
                ArrayComparator.printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
        System.out.println("Finish!");


        int len = 100000000;
        System.out.println("性能测试");
        System.out.println("数组大小：" + len);
        int[] arr = ArrayComparator.generateRandomArray(len);
        long start = 0;
        long end = 0;
        start = System.currentTimeMillis();
        noLoopMinStep4(Arrays.copyOf(arr, arr.length));
        end = System.currentTimeMillis();
        System.out.println("noLoopMinStep4 run time: " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        loopMinStep3(arr);
        end = System.currentTimeMillis();
        System.out.println("loopMinStep3 run time: " + (end - start) + "(ms)");
    }
}
