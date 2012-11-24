package net.simpleframework.mvc.component.ui.tabs;

import java.util.Collection;
import java.util.Iterator;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(TabsRegistry.tabs)
@ComponentBean(TabsBean.class)
@ComponentRender(TabsRender.class)
@ComponentResourceProvider(TabsResourceProvider.class)
public class TabsRegistry extends AbstractComponentRegistry {
	public static final String tabs = "tabs";

	@Override
	public TabsBean createComponentBean(final PageParameter pParameter, final Object data) {
		final TabsBean tabsBean = (TabsBean) super.createComponentBean(pParameter, data);
		if (!(data instanceof XmlElement)) {
			return tabsBean;
		}
		final IScriptEval scriptEval = pParameter.getScriptEval();
		final Iterator<?> it = ((XmlElement) data).elementIterator("tab");
		final Collection<TabItem> coll = tabsBean.getTabItems();
		while (it.hasNext()) {
			final XmlElement xmlElement = (XmlElement) it.next();
			final TabItem tab = new TabItem(xmlElement);
			tab.parseElement(scriptEval);

			final String contentRef = tab.getContentRef();
			if (StringUtils.hasText(contentRef)) {
				final AbstractComponentBean componentBean = pParameter
						.getComponentBeanByName(contentRef);
				if (componentBean == null) {
					if (pParameter.getComponentBeanByName(contentRef) == null) {
						throw ComponentException.wrapException_ComponentRef(contentRef);
					}
				} else {
					componentBean.setRunImmediately(false);
					if (componentBean instanceof AjaxRequestBean) {
						((AjaxRequestBean) componentBean).setShowLoading(false);
					}
					tab.setContent(getLoadingContent());
				}
			} else {
				tab.setBeanFromElementAttributes(scriptEval, new String[] { "content" });
			}
			coll.add(tab);
		}
		return tabsBean;
	}
}
