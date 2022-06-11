package learn.design.pattern.behavioral.state.table;

/**
 * 测试
 */
public class Main {

    public static void main(String[] args) {
        MarioStateMachine mario = new MarioStateMachine();
        mario.obtainMushRoom();
        mario.obtainFireFlower();
        int score = mario.getScore();
        State state = mario.getCurrentState();
        System.out.println("mario score: " + score + "; state: " + state);
    }
}
