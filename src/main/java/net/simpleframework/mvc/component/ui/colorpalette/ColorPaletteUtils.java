package net.simpleframework.mvc.component.ui.colorpalette;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class ColorPaletteUtils {
	public static final String BEAN_ID = "colorpalette_@bid";

	public static ComponentParameter get(final PageRequestResponse rRequest) {
		return ComponentParameter.get(rRequest, BEAN_ID);
	}

	public static ComponentParameter get(final HttpServletRequest request,
			final HttpServletResponse response) {
		return ComponentParameter.get(request, response, BEAN_ID);
	}

	public static String jsColorpicker(final ComponentParameter cParameter) {
		final ColorPaletteBean colorPalette = (ColorPaletteBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append("var ").append(actionFunc).append(" = $Actions[\"");
		sb.append(cParameter.getBeanProperty("name")).append("\"];");

		sb.append(actionFunc).append(".options = Object.extend({");
		final String changeCallback = colorPalette.getChangeCallback();
		if (StringUtils.hasText(changeCallback)) {
			sb.append("onValuesChanged: function(picker) {");
			sb.append("var callback = function(value) {");
			sb.append(changeCallback).append("};");
			sb.append("callback(picker.color.hex);");
			sb.append("},");
		}
		sb.append("startMode:\"");
		sb.append(colorPalette.getStartMode()).append("\",");
		sb.append("startHex:\"");
		sb.append(colorPalette.getStartHex()).append("\",");

		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("clientFilesPath:\"").append(ComponentUtils.getCssResourceHomePath(cParameter))
				.append("/images/\"");
		sb.append("}, ").append(actionFunc).append(".options);");

		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		sb.append("var cId = ").append(ComponentRenderUtils.VAR_CONTAINER).append(".identify();");
		sb.append("c.select(\"INPUT, .map, .bar, .preview\").each(function(o) {");
		sb.append("var a1 = o.getAttribute(\"id\");");
		sb.append("if (a1) { o.setAttribute(\"id\", cId + a1); }");
		sb.append("var a2 = o.getAttribute(\"name\");");
		sb.append("if (a2) { o.setAttribute(\"name\", cId + a2); }");
		sb.append("});");
		sb.append(actionFunc).append(".colorpicker = new $UI.ColorPicker(cId, ");
		sb.append(actionFunc).append(".options);");
		return JavascriptUtils.wrapWhenReady(sb.toString());
	}
}
