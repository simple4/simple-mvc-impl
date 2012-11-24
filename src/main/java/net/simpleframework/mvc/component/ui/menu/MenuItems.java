package net.simpleframework.mvc.component.ui.menu;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class MenuItems extends ArrayListEx<MenuItems, MenuItem> {

	public static MenuItems of(final MenuItem... items) {
		return new MenuItems().append(items);
	}

	private static final long serialVersionUID = 5724329853563479717L;
}
