package net.simpleframework.mvc.component.base.jspinclude;

import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PageIncludeRender extends ComponentHtmlRenderEx {

	public PageIncludeRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getHtml(final ComponentParameter cParameter) {
		final PageIncludeBean include = (PageIncludeBean) cParameter.componentBean;

		final IPageIncludeHandler jHandle = (IPageIncludeHandler) cParameter.getComponentHandler();
		if (jHandle != null) {
			final IForward forward = jHandle.include(cParameter);
			if (forward != null) {
				return forward.getResponseText(cParameter);
			}
		}
		final String includePath = (String) cParameter.getBeanProperty("pageUrl");
		return new UrlForward(includePath, include.getIncludeRequestData())
				.getResponseText(cParameter);
	}

	@Override
	protected String getRelativePath(final ComponentParameter cParameter) {
		return null;
	}
}
