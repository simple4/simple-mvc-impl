package net.simpleframework.mvc.component.ui.menu;

import net.simpleframework.common.Convert;
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
public class MenuRender extends ComponentJavascriptRender {
	public MenuRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final MenuBean menuBean = (MenuBean) cParameter.componentBean;
		MenuItems menuItems = null;
		final IMenuHandler mHandle = (IMenuHandler) cParameter.getComponentHandler();
		if (mHandle != null) {
			menuItems = mHandle.getMenuItems(cParameter, null);
		}
		if (menuItems == null) {
			menuItems = menuBean.getMenuItems();
		}
		final StringBuilder sb = new StringBuilder();
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final String containerId = (String) cParameter.getBeanProperty("containerId");
		final boolean container = StringUtils.hasText(containerId);
		if (container) {
			sb.append(ComponentRenderUtils.initContainerVar(cParameter));
			sb.append(actionFunc).append(".menu = new $UI.Menu(")
					.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
			sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
			sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		} else {
			sb.append(actionFunc).append(".menu = new $UI.Menu(null, {");
			final String selector = (String) cParameter.getBeanProperty("selector");
			if (StringUtils.hasText(selector)) {
				sb.append("\"selector\": \"").append(selector).append("\",");
			}
			final EMenuEvent menuEvent = (EMenuEvent) cParameter.getBeanProperty("menuEvent");
			if (menuEvent != null) {
				sb.append("\"menuEvent\": \"").append(menuEvent).append("\",");
			}
		}

		final int minWidth = Convert.toInt(cParameter.getBeanProperty("minWidth"));
		if (minWidth > 0) {
			sb.append("\"minWidth\": \"").append(minWidth).append("px\",");
		}
		sb.append("\"effects\": Browser.effects && ").append(cParameter.getBeanProperty("effects"))
				.append(",");

		final String beforeShowCallback = (String) cParameter.getBeanProperty("jsBeforeShowCallback");
		if (StringUtils.hasText(beforeShowCallback)) {
			sb.append("\"onBeforeShow\": function(menu, e) {");
			sb.append(beforeShowCallback);
			sb.append("},");
		}
		sb.append("\"menuItems\": ").append(jsonData(cParameter, menuItems));
		sb.append("});");

		final StringBuilder sb2 = new StringBuilder();
		sb2.append("__menu_actions_init(").append(actionFunc).append(", '")
				.append(cParameter.getBeanProperty("name")).append("');");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}

	String jsonData(final ComponentParameter cParameter, final MenuItems menuItems) {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for (final MenuItem menu : menuItems) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append("{");
			final String title = menu.getTitle();
			if (title.equals("-")) {
				sb.append("\"separator\": true");
			} else {
				sb.append("\"name\": \"").append(JavascriptUtils.escape(title)).append("\",");
				final boolean checkbox = menu.isCheckbox();
				if (checkbox) {
					sb.append("\"checkbox\": true,");
					sb.append("\"checked\": ").append(menu.isChecked()).append(",");
					final String checkCallback = menu.getJsCheckCallback();
					if (StringUtils.hasText(checkCallback)) {
						sb.append("\"onCheck\": function(item, e) {");
						sb.append(checkCallback);
						sb.append("},");
					}
				} else {
					final String icon = menu.getIconClass();
					if (StringUtils.hasText(icon)) {
						sb.append("\"icon\": \"").append(icon).append("\",");
					}
				}
				final String url = menu.getUrl();
				if (StringUtils.hasText(url)) {
					sb.append("\"url\": \"").append(cParameter.wrapContextPath(url)).append("\",");
				} else {
					final String selectCallback = menu.getJsSelectCallback();
					if (StringUtils.hasText(selectCallback)) {
						sb.append("\"onSelect\": function(item, e) {");
						sb.append(selectCallback.trim());
						sb.append("},");
					}
				}

				final String desc = menu.getDescription();
				if (StringUtils.hasText(desc)) {
					sb.append("\"desc\": \"").append(JavascriptUtils.escape(desc)).append("\",");
				}

				MenuItems children = null;
				final IMenuHandler handle = (IMenuHandler) cParameter.getComponentHandler();
				if (handle != null) {
					children = handle.getMenuItems(cParameter, menu);
				}
				if (children == null) {
					children = menu.children();
				}
				if (children.size() > 0) {
					sb.append("\"submenu\": ").append(jsonData(cParameter, children)).append(",");
				}
				sb.append("\"disabled\": ").append(menu.isDisabled());
			}
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}
}
