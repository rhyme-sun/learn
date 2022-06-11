package learn.design.pattern.behavioral.visitor;

/**
 * ResourceFile 是一个抽象类，表示不同格式的文件
 */
public abstract class ResourceFile {

    protected String filePath;

    public ResourceFile(String filePath) {
        this.filePath = filePath;
    }

    // 接待访问者并将数据传递出去
    abstract public void accept(Visitor visitor);
}
