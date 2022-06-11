package learn.design.pattern.structural.composite.tree;

import java.util.Map;

/**
 * 树节点对象抽象类
 */
public abstract class TreeNode {

    private String key;
    private String icon;
    private String title;
    protected boolean isLeaf;
    private Map<String, Object> props;

    public abstract void addChild(TreeNode child);

    public TreeNode() {
    }

    public TreeNode(String key, String title) {
        this.key = key;
        this.title = title;
    }
}
