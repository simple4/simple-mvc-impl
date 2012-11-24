package net.simpleframework.mvc.component.ui.window;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.common.web.HttpUtils;
import net.simpleframework.mvc.MVCUtils;
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
public class WindowRender extends ComponentJavascriptRender {

	public WindowRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		final boolean destroyOnClose = (Boolean) cParameter.getBeanProperty("destroyOnClose");
		if (!destroyOnClose) {
			sb.append("if (!").append(actionFunc).append(".window) {");
		}

		sb.append(actionFunc).append(".createWindow(");
		final String url = (String) cParameter.getBeanProperty("url");
		if (StringUtils.hasText(url)) {
			sb.append("\"");
			sb.append(
					cParameter.wrapContextPath(MVCUtils.doPageUrl(cParameter,
							HttpUtils.addParameters(url, "iframe=true")))).append("\", ");
		} else {
			sb.append("null, ");
		}

		final String contentStyle = (String) cParameter.getBeanProperty("contentStyle");
		if (StringUtils.hasText(contentStyle)) {
			sb.append("\"").append(contentStyle).append("\", ");
		} else {
			sb.append("null, ");
		}

		final String content = (String) cParameter.getBeanProperty("content");
		if (StringUtils.hasText(content)) {
			sb.append("\"").append(JavascriptUtils.escape(content)).append("\", ");
		} else {
			sb.append("null, ");
		}

		final String contentRef = (String) cParameter.getBeanProperty("contentRef");
		if (StringUtils.hasText(contentRef)) {
			sb.append("\"").append(contentRef).append("\"");
		} else {
			sb.append("null");
		}
		sb.append(");");
		final String jsHiddenCallback = (String) cParameter.getBeanProperty("jsHiddenCallback");
		if (StringUtils.hasText(jsHiddenCallback)) {
			sb.append(actionFunc).append(".observe('hidden', function() {");
			sb.append(jsHiddenCallback).append("});");
		}
		final String jsShownCallback = (String) cParameter.getBeanProperty("jsShownCallback");
		if (StringUtils.hasText(jsShownCallback)) {
			sb.append(actionFunc).append(".observe('shown', function() {");
			sb.append(jsShownCallback).append("});");
		}
		if (!destroyOnClose) {
			sb.append("}");
		}

		sb.append(actionFunc).append(".showWindow(");
		final String title = (String) cParameter.getBeanProperty("title");
		if (StringUtils.hasText(title)) {
			sb.append("\"").append(JavascriptUtils.escape(title)).append("\", ");
		} else {
			sb.append("null, ");
		}
		final boolean popup = (Boolean) cParameter.getBeanProperty("popup");
		final boolean showCenter = (Boolean) cParameter.getBeanProperty("showCenter");
		final boolean modal = (Boolean) cParameter.getBeanProperty("modal");
		sb.append(popup).append(", ");
		sb.append(showCenter).append(", ");
		sb.append(modal).append(", ");
		sb.append(destroyOnClose).append(", arguments[0]);");

		final StringBuilder sb2 = new StringBuilder();
		sb2.append("__window_actions_init(").append(actionFunc).append(", '")
				.append(cParameter.getBeanProperty("name")).append("', ")
				.append((!modal || popup) && (Boolean) cParameter.getBeanProperty("singleWindow"))
				.append(");");
		sb2.append(jsonOptions(cParameter, actionFunc, popup, destroyOnClose));
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}

	private static String jsonOptions(final ComponentParameter cParameter, final String actionFunc,
			final boolean popup, final boolean destroyOnClose) {
		final StringBuilder sb = new StringBuilder();
		sb.append(actionFunc).append(".options = {");
		if (popup) {
			sb.append("theme: \"popup\",");
		}
		final int top = (Integer) cParameter.getBeanProperty("top");
		if (top > 0) {
			sb.append("top: ").append(top).append(",");
		}
		final int left = (Integer) cParameter.getBeanProperty("left");
		if (left > 0) {
			sb.append("left: ").append(left).append(",");
		}
		sb.append("width: ").append(cParameter.getBeanProperty("width")).append(",");
		sb.append("height: ").append(cParameter.getBeanProperty("height")).append(",");
		sb.append("minWidth: ").append(cParameter.getBeanProperty("minWidth")).append(",");
		sb.append("minHeight: ").append(cParameter.getBeanProperty("minHeight")).append(",");
		final int maxWidth = (Integer) cParameter.getBeanProperty("maxWidth");
		if (maxWidth > 0) {
			sb.append("maxWidth: ").append(maxWidth).append(",");
		}
		final int maxHeight = (Integer) cParameter.getBeanProperty("maxHeight");
		if (maxHeight > 0) {
			sb.append("maxHeight: ").append(maxHeight).append(",");
		}

		if (!(Boolean) cParameter.getBeanProperty("minimize")) {
			sb.append("minimize: false,");
		}
		if (!(Boolean) cParameter.getBeanProperty("maximize")) {
			sb.append("maximize: false,");
		}
		if (!(Boolean) cParameter.getBeanProperty("resizable")) {
			sb.append("resizable: false,");
		}
		if (!(Boolean) cParameter.getBeanProperty("draggable")) {
			sb.append("draggable: false,");
		}
		if (!destroyOnClose) {
			sb.append("close: \"hide\",");
		}
		sb.append("effects : Browser.effects && ").append(cParameter.getBeanProperty("effects"));
		sb.append("};");
		return sb.toString();
	}
}
