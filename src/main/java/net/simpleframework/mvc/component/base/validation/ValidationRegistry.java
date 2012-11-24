package net.simpleframework.mvc.component.base.validation;

import java.util.Iterator;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(ValidationRegistry.validation)
@ComponentBean(ValidationBean.class)
@ComponentRender(ValidationRender.class)
@ComponentResourceProvider(ValidationResourceProvider.class)
public class ValidationRegistry extends AbstractComponentRegistry {
	public static final String validation = "validation";

	@Override
	public ValidationBean createComponentBean(final PageParameter pParameter, final Object data) {
		final ValidationBean validationBean = (ValidationBean) super.createComponentBean(pParameter,
				data);
		if (data instanceof XmlElement) {
			final IScriptEval scriptEval = pParameter.getScriptEval();

			final Iterator<?> it = ((XmlElement) data).elementIterator("validator");
			while (it.hasNext()) {
				final XmlElement xmlElement = (XmlElement) it.next();
				final Validator validator = new Validator(xmlElement);
				validator.parseElement(scriptEval);
				final String message = xmlElement.elementText("message");
				if (StringUtils.hasText(message)) {
					validator.setMessage(message.trim());
				}
				validationBean.getValidators().add(validator);
			}
		}
		return validationBean;
	}
}
