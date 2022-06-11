package learn.design.pattern.structural.composite;

import java.util.Iterator;
import java.util.Stack;

/**
 * 组合迭代器
 *
 * 不可小觑的代码
 */
public class CompositeIterator implements Iterator<MenuComponent> {

	private Stack<Iterator> stack = new Stack<>();

	public CompositeIterator(Iterator<MenuComponent> iterator) {
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		} else {
			// 获取栈顶元素，不弹出
			Iterator peek = stack.peek();
			if (!peek.hasNext()) {
				// 弹栈
				stack.pop();
				return hasNext();
			} else {
				return true;
			}
		}
	}

	@Override
	public MenuComponent next() {
		if (hasNext()) {
			Iterator peek = stack.peek();
			MenuComponent next = (MenuComponent) peek.next();
			// 如果为菜单节点，将改菜单的迭代器入栈
			if (next instanceof Menu) {
				stack.push(next.createIterator());
			}
			return next;
		} else {
			return null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
