package net.simpleframework.mvc.component.ui.listbox;

import java.util.Map;

import net.simpleframework.common.JsonUtils;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.common.xml.ItemUIBean;
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
public class ListboxRender extends ComponentJavascriptRender {
	public static final String EVENT_CLICK = "__click";

	public static final String EVENT_DBLCLICK = "__dblclick";

	public ListboxRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final ListboxBean listboxBean = (ListboxBean) cParameter.componentBean;

		ListItems listItems = null;
		final IListboxHandler lHandle = (IListboxHandler) cParameter.getComponentHandler();
		if (lHandle != null) {
			listItems = lHandle.getListItems(cParameter);
		}
		if (listItems == null) {
			listItems = listboxBean.getListItems();
		}

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));

		// event
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".")
				.append(eventClick(EVENT_CLICK, listboxBean.getJsClickCallback(), actionFunc));
		sb.append(actionFunc).append(".")
				.append(eventClick(EVENT_DBLCLICK, listboxBean.getJsDblclickCallback(), actionFunc));
		// new
		sb.append(actionFunc).append(".listbox = new $UI.ListBox(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", [");
		int i = 0;
		for (final ListItem listItem : listItems) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append("{");
			final String text = listItem.getText();
			if (StringUtils.hasText(text)) {
				sb.append("text: \"").append(JavascriptUtils.escape(text)).append("\",");
			}
			final String tip = listItem.getTip();
			if (StringUtils.hasText(tip)) {
				sb.append("tip: \"").append(JavascriptUtils.escape(tip)).append("\",");
			}
			if (lHandle != null) {
				final Map<String, Object> attributes = lHandle.getListItemAttributes(cParameter,
						listItem);
				if (attributes != null && attributes.size() > 0) {
					sb.append("\"attributes\": ").append(JsonUtils.toJSON(attributes)).append(",");
				}
			}
			sb.append(getUIEventData(listItem, actionFunc));
			sb.append("id: \"").append(listItem.getId()).append("\"");
			sb.append("}");
		}
		sb.append("], {");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("checkbox: ").append(listboxBean.isCheckbox()).append(",");
		sb.append("tooltip: ").append(listboxBean.isTooltip());
		sb.append("});");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}

	private String getUIEventData(final ItemUIBean bean, final String actionFunc) {
		final StringBuilder sb = new StringBuilder();
		sb.append("click: ").append(actionFunc).append(".");
		sb.append(EVENT_CLICK).append(",");
		sb.append("dblclick: ").append(actionFunc).append(".");
		sb.append(EVENT_DBLCLICK).append(",");

		final String click = bean.getJsClickCallback();
		if (StringUtils.hasText(click)) {
			sb.append(EVENT_CLICK).append(": \"");
			sb.append(JavascriptUtils.escape(click)).append("\",");
		}
		final String dblclick = bean.getJsDblclickCallback();
		if (StringUtils.hasText(dblclick)) {
			sb.append(EVENT_DBLCLICK).append(": \"");
			sb.append(JavascriptUtils.escape(click)).append("\",");
		}
		return sb.toString();
	}

	protected String eventClick(final String event, final String defaultBody, final String actionFunc) {
		final StringBuilder sb = new StringBuilder();
		sb.append(event).append("=").append("function(item, ev) {");
		sb.append("var cb = function(id, text, item, json, ev) {");
		sb.append("var func = item.data.").append(event).append(";");
		sb.append("if (func) { eval(func); } else {");
		sb.append(StringUtils.blank(defaultBody)).append("}};");
		sb.append("cb(item.getId(), item.getText(), item, ").append(actionFunc)
				.append(".json, ev); };");
		return sb.toString();
	}
}
