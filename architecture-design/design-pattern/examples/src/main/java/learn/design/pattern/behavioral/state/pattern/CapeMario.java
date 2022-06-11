package learn.design.pattern.behavioral.state.pattern;

/**
 * 斗篷马里奥
 */
public class CapeMario implements IMario {

    private static final CapeMario instance = new CapeMario();

    private CapeMario() {
    }

    public static CapeMario getInstance() {
        return instance;
    }

    @Override
    public State getName() {
        return State.CAPE;
    }

    @Override
    public void obtainMushRoom(MarioStateMachine stateMachine) {
        // do nothing
    }

    @Override
    public void obtainCape(MarioStateMachine stateMachine) {
        // do nothing
    }

    @Override
    public void obtainFireFlower(MarioStateMachine stateMachine) {
        stateMachine.setScore(300);
        stateMachine.setCurrentState(FireMario.getInstance());
    }

    @Override
    public void meetMonster(MarioStateMachine stateMachine) {
        stateMachine.setScore(-200);
        stateMachine.setCurrentState(SmallMario.getInstance());
    }
}
