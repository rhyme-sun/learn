package learn.algorithm.basics.structure.graph.traversal;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import learn.algorithm.basics.structure.graph.Node;

/**
 * 图的深度优先遍历
 */
public class DFS {

    // 图深度优先遍历（递归实现）
    static void dfs1(Node node) {
        if (node == null) {
            return;
        }
        process(node, new HashSet<>());
    }

    static void process(Node node, Set<Node> visited) {
        if (node == null) {
            return;
        }
        visited.add(node);
        System.out.println(node.value);
        for (Node next : node.nexts) {
            if (!visited.contains(next)) {
                process(next, visited);
            }
        }
    }

    // 图深度优先遍历，非递归实现（栈）
    static void dfs2(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(node.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    // 注意这里需要将 cur 重新入栈
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    // break 表示选择子节点中的一条未遍历的路径（第一个入栈的子节点）进行遍历
                    // 也可以不用 break，那么就会优先遍历最后入栈的子节点
                    break;
                }
            }
        }
    }
}
