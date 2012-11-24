package net.simpleframework.mvc.component.ui.syntaxhighlighter;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.ui.window.WindowBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(SyntaxHighlighterRegistry.syntaxHighlighter)
@ComponentBean(SyntaxHighlighterBean.class)
@ComponentRender(SyntaxHighlighterRender.class)
@ComponentResourceProvider(SyntaxHighlighterResourceProvider.class)
public class SyntaxHighlighterRegistry extends AbstractComponentRegistry {
	public static final String syntaxHighlighter = "syntaxHighlighter";

	@Override
	public SyntaxHighlighterBean createComponentBean(final PageParameter pParameter,
			final Object data) {
		final SyntaxHighlighterBean syntaxHighlighter = (SyntaxHighlighterBean) super
				.createComponentBean(pParameter, data);

		final String beanId = syntaxHighlighter.hashId();
		final String ajaxRequest = "ajaxRequest_" + beanId;
		pParameter.addComponentBean(ajaxRequest, AjaxRequestBean.class).setUrlForward(
				getComponentResourceProvider().getResourceHomePath() + "/jsp/sh_window.jsp?"
						+ SyntaxHighlighterUtils.BEAN_ID + "=" + beanId);
		pParameter.addComponentBean("window_" + beanId, WindowBean.class).setContentRef(ajaxRequest)
				.setTitle($m("SyntaxHighlighterRegistry.0")).setPopup(true).setHeight(380)
				.setWidth(500);
		return syntaxHighlighter;
	}
}
