package learn.design.pattern.structural.composite;

import java.util.Iterator;

/**
 * 女服务员
 */
public class Waitress {

	MenuComponent allMenus;

	public Waitress(MenuComponent allMenus) {
		this.allMenus = allMenus;
	}

	/**
	 * 打印全部菜单
	 */
	public void printMenu() {
		allMenus.print();
	}

	/**
	 * 打印素食菜单
	 */
	public void printVegetarianMenu() {
		Iterator iterator = allMenus.createIterator();
		while (iterator.hasNext()) {
			MenuComponent component = (MenuComponent) iterator.next();
			try {
				if (component.isVegetarian()) {
					component.print();
				}
			} catch (UnsupportedOperationException e) {

			}
		}
	}
}
