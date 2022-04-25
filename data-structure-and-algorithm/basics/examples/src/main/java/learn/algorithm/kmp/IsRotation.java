package learn.algorithm.kmp;

/**
 * 判断连个字符串是否互为旋转字符串。
 * 旋转字符串定义为：将字符串 str1 开头到某个位置 i 的字符移动到尾部，移动后的字符串 str2 就叫做 str1 字符串 i 位置的旋转串。
 * 比如字符串 "abcde"，0 位置的旋转串为 "abcde"，1 位置的旋转串为 "bcdea"，2 位置的旋转串为 "cdeab"，....
 */
public class IsRotation {

    static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        String b2 = b + b;
        return KMP.getIndexOf(b2, a) != -1;
    }

    public static void main(String[] args) {
        String str1 = "abcde";
        String str2 = "bcdea";
        System.out.println(isRotation(str1, str2));
    }
}
