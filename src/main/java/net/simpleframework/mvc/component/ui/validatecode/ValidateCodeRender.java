package net.simpleframework.mvc.component.ui.validatecode;

import java.util.UUID;

import net.simpleframework.common.StringUtils;
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
public class ValidateCodeRender extends ComponentJavascriptRender {

	public ValidateCodeRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final ValidateCodeBean validateCode = (ValidateCodeBean) cParameter.componentBean;

		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		sb.append(actionFunc).append(".validateCode = new $UI.ValidateCode(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));
		final int textWidth = validateCode.getTextWidth();
		if (textWidth > 0) {
			sb.append("textWidth: ").append(textWidth).append(",");
		}
		final String textName = validateCode.getTextName();
		if (StringUtils.hasText(textName)) {
			sb.append("textName: \"").append(textName).append("\",");
		}
		sb.append("path: \"").append(ComponentUtils.getResourceHomePath(ValidateCodeBean.class))
				.append("/jsp/validatecode.jsp?");
		sb.append(ValidateCodeUtils.BEAN_ID).append("=").append(validateCode.hashId());
		sb.append("&r=").append(UUID.randomUUID()).append("\"");
		sb.append("});");
		sb.append(actionFunc).append(".refresh = function() {");
		sb.append(actionFunc).append(".validateCode.refresh();");
		sb.append("};");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
