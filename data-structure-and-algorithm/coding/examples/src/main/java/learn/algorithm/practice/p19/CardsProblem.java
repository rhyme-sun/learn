package learn.algorithm.practice.p19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 一张扑克有 3 个属性，每种属性有 3 种值（A、B、C）；
 * 比如 "AAA"，第一个属性值 A，第二个属性值 A，第三个属性值 A；
 * 比如 "BCA"，第一个属性值 B，第二个属性值 C，第三个属性值 A；
 * 给定一个字符串类型的数组 cards[]，每一个字符串代表一张扑克；
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样。
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件。
 * 比如："ABC"、"CBC"、"BBC"。
 * 第一张第一个属性为 "A"、第二张第一个属性为 "C"、第三张第一个属性为 "B"，全不一样；
 * 第一张第二个属性为 "B"、第二张第二个属性为 "B"、第三张第二个属性为 "B"，全一样；
 * 第一张第三个属性为 "C"、第二张第三个属性为 "C"、第三张第三个属性为 "C"，全一样；
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标。
 * 返回在 cards[] 中任意挑选三张扑克，达标的方法数。
 */
public class CardsProblem {


    private static List<List<String>> validCard(String card) {
        List<List<String>> ans = new ArrayList<>();
        process(card.toCharArray(), new char[3], new char[3], 0, ans);
        return ans;
    }


    // 求能与 card 达标的所有情况
    // index 另外两张卡牌的 index 应该设置什么属性
    // 卡牌 2 是否选择和样本卡牌元素一样
    private static void process(char[] card, char[] card2, char[] card3, int index, List<List<String>> cards) {
        if (index == card.length) {
            cards.add(Arrays.asList(String.valueOf(card2), String.valueOf(card3)));
            return;
        }
        // index 位置选择和 card 一样
        card2[index] = card[index];
        card3[index] = card[index];
        process(card, card2, card3, index + 1, cards);

        // index 和 card 不一样
        if (card[index] == 'A') {
            card2[index] = 'B';
            card3[index] = 'C';
            process(card, card2, card3, index + 1, cards);
            card2[index] = 'C';
            card3[index] = 'B';
            process(card, card2, card3, index + 1, cards);
        } else if (card[index] == 'B') {
            card2[index] = 'A';
            card3[index] = 'C';
            process(card, card2, card3, index + 1, cards);
            card2[index] = 'C';
            card3[index] = 'A';
            process(card, card2, card3, index + 1, cards);
        } else {
            card2[index] = 'A';
            card3[index] = 'B';
            process(card, card2, card3, index + 1, cards);
            card2[index] = 'B';
            card3[index] = 'A';
            process(card, card2, card3, index + 1, cards);
        }
    }


    public static void main(String[] args) {
        // [[AAA, AAA], [AAB, AAC], [ABA, ACA], [ABB, ACC], [BAA, CAA], [BAB, CAC], [BBA, CCA], [BBB, CCC]]
        String card = "AAA";
        final List<List<String>> lists = validCard(card);
        Set<List<String>> set = new HashSet<>();
        for (List<String> list : lists) {
            Collections.sort(list);
            set.add(list);
        }
        System.out.println(set);
    }
}
