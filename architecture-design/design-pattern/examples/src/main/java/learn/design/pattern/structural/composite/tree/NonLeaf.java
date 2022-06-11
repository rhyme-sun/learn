package learn.design.pattern.structural.composite.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 树非叶子节点
 */
public class NonLeaf extends TreeNode {

    private List<TreeNode> children;

    public NonLeaf() {
        this.children = new ArrayList<>();
    }

    public NonLeaf(String key, String title) {
        super(key, title);
        this.children = new ArrayList<>();
    }

    @Override
    public void addChild(TreeNode child) {
        children.add(child);
    }
}
