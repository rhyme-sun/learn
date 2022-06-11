package learn.design.pattern.behavioral.state.pattern;

/**
 * 马里奥状态接口
 */
public interface IMario {

    /**
     * 获取状态
     *
     * @return 当前状态
     */
    State getName();

    /**
     * 吃了蘑菇
     *
     * @param stateMachine 状态机
     */
    void obtainMushRoom(MarioStateMachine stateMachine);

    /**
     * 获得斗篷
     *
     * @param stateMachine 状态机
     */
    void obtainCape(MarioStateMachine stateMachine);

    /**
     * 获得火焰
     *
     * @param stateMachine 状态机
     */
    void obtainFireFlower(MarioStateMachine stateMachine);

    /**
     * 碰到怪物
     *
     * @param stateMachine 状态机
     */
    void meetMonster(MarioStateMachine stateMachine);
}

