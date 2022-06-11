package learn.design.pattern.behavioral.state.logic;

/**
 * 马里奥状态机，实现方式一：分支逻辑法，分支逻辑法是状态机最简单的实现机制，参照状态转移图，将每一个状态转移，原模原样地直译成代码。
 * 这样编写的代码会包含大量的 if-else 或 switch-case 分支判断逻辑，甚至是嵌套的分支判断逻辑
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

    public MarioStateMachine() {
        this.score = 0;
        this.currentState = State.SMALL;
    }

    /**
     * 吃蘑菇
     */
    public void obtainMushRoom() {
        if (State.SMALL == currentState) {
            this.score += 100;
            this.currentState = State.SUPER;
        }
    }

    /**
     * 获取斗篷
     */
    public void obtainCape() {
        if (State.SMALL == currentState || State.SUPER == currentState) {
            this.score += 200;
            this.currentState = State.CAPE;
        }
    }

    /**
     * 获得火焰
     */
    public void obtainFireFlower() {
        if (State.SMALL == currentState || State.SUPER == currentState) {
            this.score += 300;
            this.currentState = State.FIRE;
        }
    }

    /**
     * 碰到怪物
     */
    public void meetMonster() {
        if (State.SMALL == currentState) {
            System.out.println("Game over ...");
        } else if (State.SUPER == currentState) {
            this.score -= 100;
            this.currentState = State.SMALL;
        } else if (State.CAPE == currentState) {
            this.score -= 200;
            this.currentState = State.SMALL;
        } else {
            this.score -= 300;
            this.currentState = State.SMALL;
        }
    }

    public int getScore() {
        return this.score;
    }

    public State getCurrentState() {
        return this.currentState;
    }
}