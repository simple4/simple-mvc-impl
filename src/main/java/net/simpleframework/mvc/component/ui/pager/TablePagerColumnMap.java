package net.simpleframework.mvc.component.ui.pager;

import java.util.Map;

import net.simpleframework.common.coll.AbstractKVMap;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerColumnMap extends AbstractKVMap<TablePagerColumn, TablePagerColumnMap> {
	public TablePagerColumnMap() {
		super();
	}

	public TablePagerColumnMap(final Map<String, TablePagerColumn> m) {
		putAll(m);
	}

	private static final long serialVersionUID = -6933151500969810904L;
}
