package learn.algorithm.reservoir;

/**
 * 随机盒子，让 N 个单位等概率占用盒子，即来即用。
 */
public class RandomBox {

    /**
     * 盒子可占用位置
     */
    private int[] bag;
    /**
     * 盒子容量
     */
    private int capacity;
    /**
     * 记录成功占用盒子的次数
     */
    private int count;

    public RandomBox(int capacity) {
        bag = new int[capacity];
        capacity = capacity;
        count = 0;
    }

    /**
     * 等概率返回 1~i 中的一个数字
     */
    private int rand(int max) {
        return (int) (Math.random() * max) + 1;
    }

    public void add(int num) {
        count++;
        if (count <= capacity) {
            bag[count - 1] = num;
        } else {
            // canIn=true 的概率为 N/count
            boolean canIn = rand(count) <= capacity;
            if (canIn) {
                bag[rand(capacity) - 1] = num;
            }
        }
    }

    public int[] choices() {
        int[] ans = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            ans[i] = bag[i];
        }
        return ans;
    }
}