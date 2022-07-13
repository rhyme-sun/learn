package learn.design.pattern.structural.composite;

/**
 * 测试
 */
public class Main {

	public static void main(String[] args) {
		MenuComponent allMenus = new Menu("对象村菜单", "总菜单");

		// 煎饼屋菜单
		MenuComponent panCakeMenus = new Menu("煎饼屋菜单", "对象村菜单中的子菜单：煎饼屋菜单");

		MenuItem menuItem1 = new MenuItem("山东杂粮煎饼", "好吃不上火", true, 5.5);
		MenuItem menuItem2 = new MenuItem("美国松子煎饼", "有点贵", true, 15.5);
		MenuItem menuItem3 = new MenuItem("台湾香煎饼", "还行啦", false, 7.5);
		panCakeMenus.add(menuItem1);
		panCakeMenus.add(menuItem2);
		panCakeMenus.add(menuItem3);

		// 餐厅菜单
		MenuComponent restaurantMenus = new Menu("餐厅菜单", "对象村菜单中的子菜单：餐厅菜单");
		MenuItem menuItem4 = new MenuItem("鱼香肉丝", "还行", false, 16.5);
		MenuItem menuItem5 = new MenuItem("回锅肉", "不错", false, 16.5);
		MenuItem menuItem6 = new MenuItem("酸辣土豆丝", "巨好吃", true, 6.5);
		// 餐厅菜单的甜点菜单
		Menu dessertMenus = new Menu("甜点菜单", "餐厅菜单中的子菜单：甜点菜单");
		MenuItem menuItem7 = new MenuItem("冰淇淋", "凉凉的", false, 26.5);
		MenuItem menuItem8 = new MenuItem("饼干", "333", false, 26.5);
		dessertMenus.add(menuItem7);
		dessertMenus.add(menuItem8);
		restaurantMenus.add(menuItem4);
		restaurantMenus.add(menuItem5);
		restaurantMenus.add(menuItem6);
		restaurantMenus.add(dessertMenus);

		allMenus.add(panCakeMenus);
		allMenus.add(restaurantMenus);

		Waitress waitress = new Waitress(allMenus);
		waitress.printMenu();
		System.out.println("素食菜单。。。。。");
		waitress.printVegetarianMenu();
	}
}
