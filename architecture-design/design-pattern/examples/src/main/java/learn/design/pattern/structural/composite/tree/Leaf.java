package learn.design.pattern.structural.composite.tree;

/**
 * 树叶子节点
 */
public class Leaf extends TreeNode {

    public Leaf() {
        super.isLeaf = true;
    }

    public Leaf(String key, String title) {
        super(key, title);
        super.isLeaf = true;
    }

    @Override
    public void addChild(TreeNode child) {
        throw new UnsupportedOperationException("叶子节点不支持添加孩子节点");
    }
}
