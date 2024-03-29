package net.simpleframework.mvc.component.base.validation;

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
public class ValidationRender extends ComponentJavascriptRender {
	public ValidationRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final ValidationBean validationBean = (ValidationBean) cParameter.componentBean;

		Validators validators;
		final IValidationHandler validationHandle = (IValidationHandler) cParameter
				.getComponentHandler();
		if (validationHandle != null) {
			validators = validationHandle.getValidators(cParameter);
		} else {
			validators = validationBean.getValidators();
		}
		if (validators == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.actionFunc(cParameter)).append(
				".validation = new Validation(\"");
		sb.append(validationBean.getTriggerSelector()).append("\", [");
		int i = 0;
		for (final Validator validator : validators) {
			if (i++ > 0) {
				sb.append(", ");
			}
			sb.append("{");
			sb.append("selector: \"").append(validator.getSelector()).append("\", ");
			final String args = validator.getArgs();
			if (StringUtils.hasText(args)) {
				sb.append("args: \"").append(args).append("\", ");
			}
			final String message = validator.getMessage();
			if (StringUtils.hasText(message)) {
				sb.append("message: \"");
				sb.append(JavascriptUtils.escape(message)).append("\", ");
			}
			final EWarnType warnType = validator.getWarnType();
			if (warnType != null) {
				sb.append("warnType: \"").append(warnType).append("\",");
			}
			sb.append("method: \"").append(validator.getMethod()).append("\"");
			sb.append("}");
		}
		sb.append("] ,{");
		sb.append("warnType: \"").append(validationBean.getWarnType()).append("\"");
		sb.append("});");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
