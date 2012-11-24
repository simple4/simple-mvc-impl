package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(PagerRegistry.pager)
@ComponentBean(PagerBean.class)
@ComponentRender(PagerRender.class)
@ComponentResourceProvider(PagerResourceProvider.class)
public class PagerRegistry extends AbstractComponentRegistry {
	public static final String pager = "pager";

	@Override
	public AbstractComponentBean createComponentBean(final PageParameter pParameter,
			final Object data) {
		final PagerBean pagerBean = (PagerBean) super.createComponentBean(pParameter, data);

		final ComponentParameter nComponentParameter = ComponentParameter.get(pParameter, pagerBean);
		ComponentHtmlRenderEx.createAjaxRequest(nComponentParameter);
		PagerRender.createDoPager(nComponentParameter);

		return pagerBean;
	}
}
