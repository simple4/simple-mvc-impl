package net.simpleframework.mvc.component.ui.progressbar;

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
public class ProgressBarRender extends ComponentJavascriptRender {
	public ProgressBarRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".progressbar = new $UI.ProgressBar(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
		sb.append("\"url\": \"").append(ComponentUtils.getResourceHomePath(ProgressBarBean.class));
		sb.append("/jsp/progressbar.jsp?").append(ProgressBarUtils.BEAN_ID).append("=");
		sb.append(cParameter.hashId()).append("\",");
		sb.append("\"maxProgressValue\": ");
		sb.append(cParameter.getBeanProperty("maxProgressValue")).append(",");
		sb.append("\"step\": ");
		sb.append(cParameter.getBeanProperty("step")).append(",");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		final int detailHeight = (Integer) cParameter.getBeanProperty("detailHeight");
		if (detailHeight > 0) {
			sb.append("\"detailHeight\": \"").append(detailHeight).append("px\",");
		}
		sb.append("\"interval\": ");
		sb.append(cParameter.getBeanProperty("interval")).append(",");
		sb.append("\"startAfterCreate\": ").append(cParameter.getBeanProperty("startAfterCreate"))
				.append(",");
		sb.append("\"showAbortAction\": ").append(cParameter.getBeanProperty("showAbortAction"))
				.append(",");
		sb.append("\"showDetailAction\": ").append(cParameter.getBeanProperty("showDetailAction"))
				.append(",");
		sb.append("\"effects\": Browser.effects && ").append(cParameter.getBeanProperty("effects"));
		sb.append("});");

		final StringBuilder sb2 = new StringBuilder();
		sb2.append("__progressbar_actions_init(").append(actionFunc).append(");");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}
}
