package learn.design.pattern.structural.composite;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 菜单类
 *
 * @author ykthree
 * @date 2019/08/25
 **/
public class Menu extends MenuComponent {

	String name;
	String description;
	ArrayList<MenuComponent> menuComponents = new ArrayList<MenuComponent>();

	public Menu(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public Iterator createIterator() {
		return new CompositeIterator(menuComponents.iterator());
	}

	@Override
	public void print() {
		System.out.println("name:" + this.name);
		System.out.println(", description:" + this.description);
		Iterator<MenuComponent> iterator = menuComponents.iterator();
		while (iterator.hasNext()) {
			iterator.next().print();
		}
	}

	@Override
	public void add(MenuComponent component) {
		this.menuComponents.add(component);
	}

	@Override
	public void remove(MenuComponent component) {
		this.menuComponents.remove(component);
	}

	@Override
	public MenuComponent getChild(int index) {
		return this.menuComponents.get(index);
	}

}
