package net.simpleframework.mvc.component;

import java.io.InputStream;
import java.util.Iterator;

import net.simpleframework.common.ClassUtils;
import net.simpleframework.common.I18n;
import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.common.xml.XmlDocument;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.common.xml.XmlElement.Attri;
import net.simpleframework.mvc.component.ui.menu.MenuBean;
import net.simpleframework.mvc.component.ui.menu.MenuItem;
import net.simpleframework.mvc.component.ui.menu.MenuItems;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ComponentHandlerEx extends AbstractComponentHandler {

	public MenuItems getContextMenu(final ComponentParameter cParameter, final MenuBean menuBean,
			final MenuItem menuItem) {
		return createXmlMenu(ClassUtils.getResourceRecursively(getClass(), "menu.xml"), menuBean);
	}

	protected MenuItems createXmlMenu(final InputStream inputStream, final MenuBean menuBean) {
		if (inputStream == null) {
			return null;
		}
		final MenuItems menuItems = MenuItems.of();
		new XmlDocument(inputStream) {
			@Override
			protected void init() throws Exception {
				final Iterator<?> it = getRoot().elementIterator("item");
				while (it.hasNext()) {
					_create(menuItems, (XmlElement) it.next());
				}
			}

			private void _create(final MenuItems children, final XmlElement xmlElement) {
				final MenuItem item = new MenuItem(menuBean);
				Iterator<?> it = xmlElement.attributeIterator();
				while (it.hasNext()) {
					final Attri attri = (Attri) it.next();
					BeanUtils.setProperty(item, attri.getName(), I18n.replaceI18n(attri.getValue()));
					item.setJsSelectCallback(xmlElement.elementText("jsSelectCallback"));
				}
				children.add(item);

				it = xmlElement.elementIterator("item");
				while (it.hasNext()) {
					_create(item.children(), (XmlElement) it.next());
				}
			}
		};
		return menuItems;
	}
}
