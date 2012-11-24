package net.simpleframework.mvc.component.ui.tabs;

import net.simpleframework.common.I18n;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TabsRender extends ComponentJavascriptRender {
	public TabsRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final TabsBean tabsBean = (TabsBean) cParameter.componentBean;

		TabItems tabs = null;
		final ITabsHandler tabsHandle = (ITabsHandler) cParameter.getComponentHandler();
		if (tabsHandle != null) {
			tabs = tabsHandle.tabItems(cParameter);
		}
		if (tabs == null) {
			tabs = tabsBean.getTabItems();
		}

		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		sb.append(actionFunc).append(".options = {");
		sb.append("effects: Browser.effects && ").append(cParameter.getBeanProperty("effects"))
				.append(",");
		final String parameters = (String) cParameter.getBeanProperty("parameters");
		if (StringUtils.hasText(parameters)) {
			sb.append("parameters: \"").append(parameters).append("\".addParameter(arguments[0]),");
		} else {
			sb.append("parameters: arguments[0],");
		}
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("activeIndex: ").append(cParameter.getBeanProperty("activeIndex"));
		sb.append("};");

		sb.append(actionFunc).append(".tabs = new $UI.Tabs(");
		sb.append(ComponentRenderUtils.VAR_CONTAINER).append(", [");
		if (tabs != null) {
			int i = 0;
			for (final TabItem tab : tabs) {
				if (i++ > 0) {
					sb.append(",");
				}
				sb.append("{");
				final String content = tab.getContent();
				if (StringUtils.hasText(content)) {
					sb.append("content: \"");
					sb.append(JavascriptUtils.escape(I18n.replaceI18n(content))).append("\",");
				}
				final String contentStyle = tab.getContentStyle();
				if (StringUtils.hasText(contentStyle)) {
					sb.append("contentStyle: \"").append(contentStyle).append("\",");
				}
				final String contentRef = tab.getContentRef();
				if (StringUtils.hasText(contentRef)) {
					sb.append("contentRef: \"").append(contentRef).append("\",");
				}
				sb.append("cache: ").append(tab.isCache()).append(",");
				final String jsActiveCallback = tab.getJsActiveCallback();
				if (StringUtils.hasText(jsActiveCallback)) {
					sb.append("onActive: \"");
					sb.append(JavascriptUtils.escape(jsActiveCallback)).append("\",");
				}
				final String jsContentLoadedCallback = tab.getJsContentLoadedCallback();
				if (StringUtils.hasText(jsContentLoadedCallback)) {
					sb.append("onContentLoaded: \"");
					sb.append(JavascriptUtils.escape(jsContentLoadedCallback)).append("\",");
				}
				sb.append("title: \"");
				sb.append(JavascriptUtils.escape(tab.getTitle())).append("\"");
				sb.append("}");
			}
		}
		sb.append("], ").append(actionFunc).append(".options);");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}