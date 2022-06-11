package learn.design.pattern.behavioral.visitor;

/**
 * 访问者接口，提供了访问不同类型对象的方法抽象
 * <p>访问者模式针对的是一组类型不同的对象（PdfFile、PPTFile、WordFile）。不过，尽管这组对象的类型是不同的，
 * 但是，它们继承相同的父类（ResourceFile）或者实现相同的接口。在不同的应用场景下，我们需要对这组对象进行一系列不相关的业务操作
 * （抽取文本、压缩等），但为了避免不断添加功能导致类（PdfFile、PPTFile、WordFile）不断膨胀，职责越来越不单一，
 * 以及避免频繁地添加功能导致的频繁代码修改，我们使用访问者模式，将对象与操作解耦，将这些业务操作抽离出来，
 * 定义在独立细分的访问者类（Extractor、Compressor）中。
 */
public interface Visitor {

    void visit(PDFFile pdfFile);

    void visit(PPTFile pdfFile);

    void visit(WordFile pdfFile);
}