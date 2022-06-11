package learn.design.pattern.structural.composite;

import java.util.Iterator;

/**
 * 菜单项
 */
public class MenuItem extends MenuComponent {

	String name;
	String description;
	boolean vegetrian;
	double price;

	public MenuItem(String name, String description, boolean vegetrian, double price) {
		this.name = name;
		this.description = description;
		this.vegetrian = vegetrian;
		this.price = price;
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
	public double getPrice() {
		return this.price;
	}

	@Override
	public boolean isVegetarian() {
		return this.vegetrian;
	}

	@Override
	public void print() {
		System.out.println("name:" + this.name);
		System.out.println(", description:" + this.description);
		System.out.println(", price:" + this.price);
	}

	@Override
	public Iterator createIterator() {
		return new NullIterator();
	}

}
