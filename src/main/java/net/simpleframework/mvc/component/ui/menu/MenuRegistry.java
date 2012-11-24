package net.simpleframework.mvc.component.ui.menu;

import java.util.Iterator;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentException;
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
@ComponentName(MenuRegistry.menu)
@ComponentBean(MenuBean.class)
@ComponentRender(MenuRender.class)
@ComponentResourceProvider(MenuResourceProvider.class)
public class MenuRegistry extends AbstractComponentRegistry {
	public static final String menu = "menu";

	@Override
	public MenuBean createComponentBean(final PageParameter pParameter, final Object data) {
		final MenuBean menuBean = (MenuBean) super.createComponentBean(pParameter, data);
		if (data instanceof XmlElement) {
			final Iterator<?> it = ((XmlElement) data).elementIterator("menuitem");
			while (it.hasNext()) {
				final XmlElement xmlElement = (XmlElement) it.next();
				initMenuItem(pParameter, menuBean, null, menuBean.getMenuItems(), xmlElement);
			}
		}
		return menuBean;
	}

	void initMenuItem(final PageParameter pParameter, final MenuBean menuBean,
			final MenuItem parent, final MenuItems children, final XmlElement xmlElement) {
		final MenuItem menuItem = new MenuItem(xmlElement, menuBean, parent);
		menuItem.parseElement(pParameter.getScriptEval());
		final String ref = menuItem.getRef();
		if (StringUtils.hasText(ref)) {
			final MenuBean menuRef = (MenuBean) pParameter.getComponentBeanByName(ref);
			if (menuRef == null) {
				if (pParameter.getComponentBeanByName(ref) == null) {
					throw ComponentException.wrapException_ComponentRef(ref);
				}
			} else {
				children.addAll(menuRef.getMenuItems());
			}
		} else {
			children.add(menuItem);
		}
		final Iterator<?> it = xmlElement.elementIterator("menuitem");
		while (it.hasNext()) {
			final XmlElement ele = (XmlElement) it.next();
			initMenuItem(pParameter, menuBean, menuItem, menuItem.children(), ele);
		}
	}
}
