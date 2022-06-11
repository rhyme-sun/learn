package learn.design.pattern.behavioral.observer.asysn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ObserverAction 类用来表示 @Subscribe 注解的方法，其中，target 表示观察者类，method 表示方法。
 */
public class ObserverAction {

  /**
   * 观察者类
   */
  private Object target;

  /**
   * 观察者类注册方法
   */
  private Method method;

  public ObserverAction(Object target, Method method) {
    this.target = target;
    this.method = method;
    this.method.setAccessible(true);
  }

  public void execute(Object event) { // event 是 method 方法的参数
    try {
      method.invoke(target, event);
    } catch (InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}