package learn.design.pattern.behavioral.memento;

import java.util.Stack;

/**
 * 快照持有器，存储和释放快照
 */
public class SnapshotHolder {

    /**
     * 快照存储栈
     */
    private Stack<Snapshot> snapshots = new Stack<>();

    public Snapshot popSnapshot() {
        if (snapshots.isEmpty()) {
            throw new UnsupportedOperationException("已经为初始状态，无法撤销");
        }
        return snapshots.pop();
    }

    public void pushSnapshot(Snapshot snapshot) {
        snapshots.push(snapshot);
    }
}