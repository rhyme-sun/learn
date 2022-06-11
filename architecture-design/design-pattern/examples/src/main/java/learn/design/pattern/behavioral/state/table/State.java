package learn.design.pattern.behavioral.state.table;

/**
 * 马里奥状态枚举类
 */
public enum State {

  /**
   * 小马里奥
   */
  SMALL(0),

  /**
   * 超级马里奥
   */
  SUPER(1),

  /**
   * 火焰马里奥
   */
  FIRE(2),

  /**
   * 斗篷马里奥
   */
  CAPE(3);

  private final int value;

  private State(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}

