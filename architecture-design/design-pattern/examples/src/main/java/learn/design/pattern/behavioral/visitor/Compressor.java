package learn.design.pattern.behavioral.visitor;

/**
 * 压缩器，访问不同类型文件对象，提供压缩方法
 */
public class Compressor implements Visitor {

    @Override
    public void visit(PPTFile pptFile) {
        //...
        System.out.println("Compress PPT.");
    }

    @Override
    public void visit(PDFFile pdfFile) {
        //...
        System.out.println("Compress PDF.");
    }

    @Override
    public void visit(WordFile wordFile) {
        //...
        System.out.println("Compress WORD.");
    }
}