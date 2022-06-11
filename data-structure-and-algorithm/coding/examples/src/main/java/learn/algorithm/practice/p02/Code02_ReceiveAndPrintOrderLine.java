package learn.algorithm.practice.p02;

import java.util.HashMap;
import java.util.Map;

/**
 * 题目描述如下：
 * <p>
 * 已知一个消息流会不断地吐出整数 1~N，但不一定按照顺序依次吐出。
 * 如果上次打印的序号为 i， 那么当 i+1 出现时，请打印 i+1 及其之后接收过的并且连续的所有数。直到 1~N 全部接收并打印完。
 * 请设计这种接收并打印的结构，要求时间复杂度为 `O(N)`。
 */
public class Code02_ReceiveAndPrintOrderLine {

    static class MessageBox {
        private Map<Integer, Node> headMap;
        private Map<Integer, Node> tailMap;
        private int waitPoint;

        public MessageBox() {
            headMap = new HashMap<>();
            tailMap = new HashMap<>();
            waitPoint = 1;
        }

        public void receive(int num, String message) {
            if (num < 1) {
                return;
            }
            Node cur = new Node(message);
            headMap.put(num, cur);
            tailMap.put(num, cur);
            if (tailMap.containsKey(num - 1)) {
                tailMap.remove(num - 1).next = cur;
                headMap.remove(num);
            }
            if (headMap.containsKey(num + 1)) {
                cur.next = headMap.remove(num + 1);
                tailMap.remove(num);
            }
            if (num == waitPoint) {
                print();
            }
        }

        private void print() {
            Node node = headMap.remove(waitPoint);
            while (node != null) {
                System.out.print(node.info + " ");
                node = node.next;
                waitPoint++;
            }
            tailMap.remove(waitPoint - 1);
        }
    }

    private static class Node {
        public String info;
        public Node next;

        public Node(String str) {
            info = str;
        }
    }

    public static void main(String[] args) {
        // MessageBox only receive 1~N
        MessageBox box = new MessageBox();
        box.receive(2, "B"); // - 2"
        box.receive(1, "A"); // 1 2 -> print, trigger is 1
        box.receive(4, "D"); // - 4
        box.receive(5, "E"); // - 4 5
        box.receive(7, "G"); // - 4 5 - 7
        box.receive(8, "H"); // - 4 5 - 7 8
        box.receive(6, "F"); // - 4 5 6 7 8
        box.receive(3, "C"); // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(9, "I"); // 9 -> print, trigger is 9
        box.receive(10, "J"); // 10 -> print, trigger is 10
        box.receive(12, "L"); // - 12
        box.receive(13, "M"); // - 12 13
        box.receive(11, "K"); // 11 12 13 -> print, trigger is 11
        box.receive(14, "N"); // 14 -> print, trigger is 14
    }
}
