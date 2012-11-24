package net.simpleframework.mvc.component.base.validation;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.IComponentResourceProvider.AbstractComponentResourceProvider;
import net.simpleframework.mvc.component.ui.calendar.CalendarResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ValidationResourceProvider extends AbstractComponentResourceProvider {

	public ValidationResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		return new String[] { getResourceHomePath(CalendarResourceProvider.class) + "/js/date.js",
				getResourceHomePath() + "/js/validation.js" };
	}
}
