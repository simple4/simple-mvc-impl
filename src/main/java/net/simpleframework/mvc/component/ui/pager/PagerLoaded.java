package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.DefaultPageHandler;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PagerLoaded extends DefaultPageHandler {

	@Override
	public Object getBeanProperty(final PageParameter pParameter, final String beanProperty) {
		if ("role".equals(beanProperty)) {
			final ComponentParameter nComponentParameter = PagerUtils.get(pParameter);
			if (nComponentParameter.componentBean != null) {
				return nComponentParameter.getBeanProperty("role");
			}
		}
		return super.getBeanProperty(pParameter, beanProperty);
	}
}
