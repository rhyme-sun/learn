package learn.design.pattern.behavioral.state.table;

/**
 * 马里奥状态机实现方式二：，状态机还可以用二维表来表示，在这个二维表中，第一维表示当前状态，
 * 第二维表示事件，值表示当前状态经过事件之后，转移到的新状态及其执行的动作。
 */
public class MarioStateMachine {

    /**
     * 积分
     */
    private int score;

    /**
     * 当前状态
     */
    private State currentState;

    /**
     * 状态转换表
     */
    private static final State[][] transitionTable = {
            {State.SUPER, State.CAPE, State.FIRE, State.SMALL},
            {State.SUPER, State.CAPE, State.FIRE, State.SMALL},
            {State.CAPE, State.CAPE, State.CAPE, State.SMALL},
            {State.FIRE, State.FIRE, State.FIRE, State.SMALL}
    };

    /**
     * 动作表
     */
    private static final int[][] actionTable = {
            {+100, +200, +300, +0},
            {+0, +200, +300, -100},
            {+0, +0, +0, -200},
            {+0, +0, +0, -300}
    };

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = State.SMALL;
    }

    public void obtainMushRoom() {
        executeEvent(Event.GOT_MUSHROOM);
    }

    public void obtainCape() {
        executeEvent(Event.GOT_CAPE);
    }

    public void obtainFireFlower() {
        executeEvent(Event.GOT_FIRE);
    }

    public void meetMonster() {
        executeEvent(Event.MET_MONSTER);
    }

    private void executeEvent(Event event) {
        int stateValue = currentState.getValue();
        int eventValue = event.getValue();
        this.currentState = transitionTable[stateValue][eventValue];
        this.score += actionTable[stateValue][eventValue];
    }

    public int getScore() {
        return this.score;
    }

    public State getCurrentState() {
        return this.currentState;
    }
}