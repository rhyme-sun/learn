package learn.design.pattern.behavioral.state.table;

/**
 * 马里奥事件枚举类
 */
public enum Event {

  /**
   * 吃了蘑菇
   */
  GOT_MUSHROOM(0),

  /**
   * 获得斗篷
   */
  GOT_CAPE(1),

  /**
   * 获得火焰
   */
  GOT_FIRE(2),

  /**
   * 碰到怪物
   */
  MET_MONSTER(3);

  private final int value;

  private Event(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
