package learn.design.pattern.structural.composite;

import java.util.Iterator;

/**
 * 菜单组件
 */
public abstract class MenuComponent {

	/**
	 * 获得菜单名称
	 * @return
	 */
	public String getName() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获得菜单描述
	 * @return
	 */
	public String getDescription() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获得价格
	 * @return
	 */
	public double getPrice() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 是否是蔬菜类
	 * @return
	 */
	public boolean isVegetarian() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 打印菜单
	 */
	public void print() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 添加组件节点
	 * @param component
	 */
	public void add(MenuComponent component) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 去除组件节点
	 * @param component
	 */
	public void remove(MenuComponent component) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取孩子节点
	 * @param index
	 */
	public MenuComponent getChild(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 获取组合迭代器
	 * @return
	 */
	public abstract Iterator createIterator();
}
