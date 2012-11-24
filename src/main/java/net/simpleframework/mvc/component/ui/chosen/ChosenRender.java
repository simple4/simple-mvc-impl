package net.simpleframework.mvc.component.ui.chosen;

import net.simpleframework.common.StringUtils;
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
public class ChosenRender extends ComponentJavascriptRender {

	public ChosenRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final String selector = (String) cParameter.getBeanProperty("selector");
		if (!StringUtils.hasText(selector)) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		sb.append("var selects = $$('").append(selector).append("');");
		sb.append("for (var i = 0; i < selects.length; i++) {");
		sb.append("new Chosen(selects[i], {");
		sb.append("disable_search: !").append(cParameter.getBeanProperty("enableSearch")).append(",");
		sb.append("allow_single_deselect: true,");
		sb.append("no_results_text: 'no_results_text'");
		sb.append("});");
		sb.append("}");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
