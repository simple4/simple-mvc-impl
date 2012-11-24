package net.simpleframework.mvc.component.ui.pwdstrength;

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
public class PwdStrengthRender extends ComponentJavascriptRender {
	public PwdStrengthRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final PwdStrengthBean pwdStrength = (PwdStrengthBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".pwdstrength = new $UI.PwdStrength(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
		final String pwdInput = pwdStrength.getPwdInput();
		if (StringUtils.hasText(pwdInput)) {
			sb.append("\"pwdInput\": \"").append(pwdInput).append("\",");
		}
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		sb.append("\"effects\": Browser.effects && ").append(cParameter.getBeanProperty("effects"));
		sb.append("});");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
