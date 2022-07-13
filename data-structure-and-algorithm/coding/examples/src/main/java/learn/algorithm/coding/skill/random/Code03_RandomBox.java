package learn.algorithm.coding.skill.random;

/**
 * 随机盒子，让 N 个单位等概率占用盒子，即来即用。
 * 蓄水池算法，无限序列等概率占用盒子。
 */
public class Code03_RandomBox {

    static class RandomBox {
        private int[] bag;
        private int capacity;
        // 记录序列号
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
}