package net.simpleframework.mvc.component.ui.window;

import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(WindowRegistry.window)
@ComponentBean(WindowBean.class)
@ComponentRender(WindowRender.class)
@ComponentResourceProvider(WindowResourceProvider.class)
public class WindowRegistry extends AbstractComponentRegistry {
	public static final String window = "window";

	@Override
	public WindowBean createComponentBean(final PageParameter pParameter, final Object data) {
		final WindowBean windowBean = (WindowBean) super.createComponentBean(pParameter, data);
		final String contentRef = windowBean.getContentRef();
		if (StringUtils.hasText(contentRef)) {
			final AbstractComponentBean ref = pParameter.getComponentBeanByName(contentRef);
			if (ref == null) {
				if (pParameter.getComponentBeanByName(contentRef) == null) {
					throw ComponentException.wrapException_ComponentRef(contentRef);
				}
			} else {
				ref.setRunImmediately(false);
				windowBean.setContent(getLoadingContent());
				if (ref instanceof AjaxRequestBean) {
					((AjaxRequestBean) ref).setShowLoading(false);
				}
			}
		}
		return windowBean;
	}
}
