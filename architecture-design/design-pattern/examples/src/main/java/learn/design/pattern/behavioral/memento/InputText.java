package learn.design.pattern.behavioral.memento;

/**
 * 输入文本对象
 */
public class InputText {

    /**
     * 文本
     */
    private final StringBuilder text = new StringBuilder();

    public String getText() {
        return text.toString();
    }

    public void append(String input) {
        text.append(input);
    }

    /**
     * 未当前文本创建一个快照对象
     *
     * @return {@link Snapshot 快照对象}
     */
    public Snapshot createSnapshot() {
        return new Snapshot(text.toString());
    }

    /**
     * 用快照对象恢复文本
     *
     * @param snapshot 快照对象
     */
    public void restoreSnapshot(Snapshot snapshot) {
        this.text.replace(0, this.text.length(), snapshot.getText());
    }

    @Override
    public String toString() {
        return text.toString();
    }
}




