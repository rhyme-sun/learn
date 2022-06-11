package learn.design.pattern.behavioral.visitor;

/**
 * PDF 格式文件
 */
public class PDFFile extends ResourceFile {

    public PDFFile(String filePath) {
        super(filePath);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}