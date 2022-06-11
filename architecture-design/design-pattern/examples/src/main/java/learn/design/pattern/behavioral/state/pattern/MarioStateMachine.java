package learn.design.pattern.behavioral.state.pattern;

/**
 * 马里奥状态机实现方式三：IMario 是状态的接口，定义了所有的事件。SmallMario、SuperMario、CapeMario、FireMario
 * 是 IMario 接口的实现类，分别对应状态机中的 4 个状态。原来所有的状态转移和动作执行的代码逻辑，都集中在 MarioStateMachine 类中，
 * 现在，这些代码逻辑被分散到了这 4 个状态类中。
 * 状态类和状态机存在相互依赖的问题
 */
public class MarioStateMachine {

    /**
     * 积分
     */
    private int score;

    /**
     * 当前状态，改为状态类
     */
    private IMario currentState;

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = SmallMario.getInstance();
    }

    public void obtainMushRoom() {
        this.currentState.obtainMushRoom(this);
    }

    public void obtainCape() {
        this.currentState.obtainCape(this);
    }

    public void obtainFireFlower() {
        this.currentState.obtainFireFlower(this);
    }

    public void meetMonster() {
        this.currentState.meetMonster(this);
    }

    public int getScore() {
        return this.score;
    }

    public State getCurrentState() {
        return this.currentState.getName();
    }

    public void setScore(int score) {
        this.score += score;
    }

    public void setCurrentState(IMario currentState) {
        this.currentState = currentState;
    }
}