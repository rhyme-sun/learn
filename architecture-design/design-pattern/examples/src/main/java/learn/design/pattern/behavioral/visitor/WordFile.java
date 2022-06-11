package learn.design.pattern.behavioral.visitor;

/**
 * Word 格式文件
 */
public class WordFile extends ResourceFile {

    public WordFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}