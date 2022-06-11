package learn.design.pattern.behavioral.memento;

/**
 * 文本快照对象，该对象是不可变的，用来备份文本，恢复对象
 */
public class Snapshot {

    /**
     * 备份文本内容
     */
    private String text;

    public Snapshot(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}