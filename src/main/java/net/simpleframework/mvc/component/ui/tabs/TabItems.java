package net.simpleframework.mvc.component.ui.tabs;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TabItems extends ArrayListEx<TabItems, TabItem> {

	public static TabItems of(final TabItem... items) {
		return new TabItems().append(items);
	}

	private static final long serialVersionUID = 1620383138690227387L;
}
