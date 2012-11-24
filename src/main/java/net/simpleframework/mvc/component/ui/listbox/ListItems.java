package net.simpleframework.mvc.component.ui.listbox;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ListItems extends ArrayListEx<ListItems, ListItem> {

	public static ListItems of(final ListItem... items) {
		return new ListItems().append(items);
	}

	private static final long serialVersionUID = 6215022552238105810L;
}
