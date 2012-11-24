package net.simpleframework.mvc.component.ui.listbox;

import java.util.Iterator;

import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(ListboxRegistry.listbox)
@ComponentBean(ListboxBean.class)
@ComponentRender(ListboxRender.class)
@ComponentResourceProvider(ListboxResourceProvider.class)
public class ListboxRegistry extends AbstractComponentRegistry {
	public static final String listbox = "listbox";

	@Override
	public ListboxBean createComponentBean(final PageParameter pParameter, final Object data) {
		final ListboxBean listboxBean = (ListboxBean) super.createComponentBean(pParameter, data);
		if (data instanceof XmlElement) {
			final IScriptEval scriptEval = pParameter.getScriptEval();
			final Iterator<?> it = ((XmlElement) data).elementIterator("item");
			while (it.hasNext()) {
				final XmlElement xmlElement = (XmlElement) it.next();
				final ListItem item = new ListItem(xmlElement, listboxBean, null);
				item.parseElement(scriptEval);
				listboxBean.getListItems().add(item);
			}
		}
		return listboxBean;
	}
}
