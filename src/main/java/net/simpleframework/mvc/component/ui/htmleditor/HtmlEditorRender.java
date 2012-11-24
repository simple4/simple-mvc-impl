package net.simpleframework.mvc.component.ui.htmleditor;

import java.util.Locale;

import net.simpleframework.common.I18n;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class HtmlEditorRender extends ComponentJavascriptRender {
	public HtmlEditorRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final HtmlEditorBean htmlEditor = (HtmlEditorBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final String textarea = htmlEditor.getTextarea();
		final boolean hasTextarea = StringUtils.hasText(textarea);
		sb.append("if (CKEDITOR._loading) return; CKEDITOR._loading = true;");
		if (hasTextarea) {
			sb.append(actionFunc).append(".editor = CKEDITOR.replace(\"");
			sb.append(textarea).append("\", {");
		} else {
			sb.append(ComponentRenderUtils.initContainerVar(cParameter));
			sb.append(actionFunc).append(".editor = CKEDITOR.appendTo(")
					.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
		}
		sb.append("contentsCss: [\"").append(ComponentUtils.getCssResourceHomePath(cParameter))
				.append("/contents.css").append("\"],");
		sb.append("smiley_path: \"").append(ComponentUtils.getResourceHomePath(HtmlEditorBean.class))
				.append("/smiley/\",");
		sb.append("enterMode: ").append(getLineMode(htmlEditor.getEnterMode())).append(",");
		sb.append("shiftEnterMode: ").append(getLineMode(htmlEditor.getShiftEnterMode())).append(",");
		sb.append("language: \"").append(getLanguage()).append("\",");
		sb.append("autoUpdateElement: false,");
		sb.append("uiColor: \"#C9E3F5\",");
		sb.append("startupFocus: ").append(htmlEditor.isStartupFocus()).append(",");
		sb.append("toolbarCanCollapse: ").append(htmlEditor.isToolbarCanCollapse()).append(",");
		sb.append("resize_enabled: ").append(htmlEditor.isResizeEnabled()).append(",");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("on: {");
		sb.append("instanceReady: function(ev) { CKEDITOR._loading = false; ");
		final String jsLoadedCallback = (String) cParameter.getBeanProperty("jsLoadedCallback");
		if (StringUtils.hasText(jsLoadedCallback)) {
			sb.append(jsLoadedCallback);
		}
		sb.append("}");
		sb.append("},");
		sb.append("toolbar: \"").append(htmlEditor.getToolbar()).append("\"");
		sb.append("});");
		if (hasTextarea) {
			sb.append("$(\"").append(textarea).append("\").htmlEditor = ");
			sb.append(actionFunc).append(".editor;");
		}

		String htmlContent = null;
		final IHtmlEditorHandler handle = (IHtmlEditorHandler) cParameter.getComponentHandler();
		if (handle != null) {
			htmlContent = handle.getHtmlContent(cParameter);
		}
		if (!StringUtils.hasText(htmlContent)) {
			htmlContent = htmlEditor.getHtmlContent();
		}
		if (StringUtils.hasText(htmlContent)) {
			sb.append(actionFunc).append(".editor.setData(\"");
			sb.append(JavascriptUtils.escape(htmlContent)).append("\");");
		}

		final StringBuilder sb2 = new StringBuilder();
		sb2.append("var act = $Actions[\"").append(cParameter.getBeanProperty("name")).append("\"];");
		sb2.append("if (act && act.editor) { CKEDITOR.remove(act.editor); }");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}

	private String getLanguage() {
		final Locale l = I18n.getLocale();
		if (l.equals(Locale.SIMPLIFIED_CHINESE)) {
			return "zh-cn";
		} else {
			return "en";
		}
	}

	private String getLineMode(final EEditorLineMode lineMode) {
		if (lineMode == EEditorLineMode.br) {
			return "CKEDITOR.ENTER_BR";
		} else if (lineMode == EEditorLineMode.div) {
			return "CKEDITOR.ENTER_DIV";
		} else {
			return "CKEDITOR.ENTER_P";
		}
	}
}
