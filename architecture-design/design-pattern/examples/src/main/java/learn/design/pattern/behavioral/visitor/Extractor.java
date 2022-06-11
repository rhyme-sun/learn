package learn.design.pattern.behavioral.visitor;

/**
 * 文本提取器，访问不同类型文件对象，提供提取文本方法
 */
public class Extractor implements Visitor {

    @Override
    public void visit(PPTFile pptFile) {
        //...
        System.out.println("Extract PPT.");
    }

    @Override
    public void visit(PDFFile pdfFile) {
        //...
        System.out.println("Extract PDF.");
    }

    @Override
    public void visit(WordFile wordFile) {
        //...
        System.out.println("Extract WORD.");
    }
}