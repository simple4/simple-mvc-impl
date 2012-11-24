package net.simpleframework.mvc.component.ui.listbox;

import java.util.Map;

import net.simpleframework.common.coll.KVMap;
import net.simpleframework.mvc.component.AbstractComponentHandler;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractListboxHandler extends AbstractComponentHandler implements
		IListboxHandler {

	@Override
	public Map<String, Object> getListItemAttributes(final ComponentParameter cParameter,
			final ListItem listItem) {
		return new KVMap();
	}
}
