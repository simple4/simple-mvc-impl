package net.simpleframework.mvc.component.ui.dictionary;

import java.util.Map;

import net.simpleframework.mvc.component.ui.tree.AbstractTreeHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryTreeHandler extends AbstractTreeHandler {

	protected void disabled_selected(final Map<String, Object> kv) {
		kv.put("select_disable", Boolean.TRUE);
	}
}
