package learn.algorithm.comparator;

/**
 * 字符串对数器
 */
public class StringComparator {

    /**
     * 生成一个随机字符串，指包含数字字符
     *
     * @param strLen 随机字符串的最大长度
     * @return 随机字符串
     */
    public static String generateRandomDigitString(int strLen) {
        char[] str = new char[strLen];
        for (int i = 0; i < strLen; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }


    /**
     * 生成一个随机字符串
     *
     * @param strLen 随机字符串的最大长度
     * @return 随机字符串
     */
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    /**
     * 生成一个随机字符串数组
     *
     * @param arrLen 数组长度
     * @param strLen 字符串长度、
     * @return 随机字符串数组
     */
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    /**
     * 拷贝字符串数组
     */
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }
}
