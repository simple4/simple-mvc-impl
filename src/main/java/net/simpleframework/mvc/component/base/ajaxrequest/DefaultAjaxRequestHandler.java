package net.simpleframework.mvc.component.base.ajaxrequest;

import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.MVCUtils;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.AbstractComponentHandler;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DefaultAjaxRequestHandler extends AbstractComponentHandler implements
		IAjaxRequestHandler {
	public static final String JK_ERROR = "error";

	@Override
	public IForward ajaxProcess(final ComponentParameter cParameter) {
		return null;
	}

	public IForward doUrlForward(final ComponentParameter cParameter) {
		return new UrlForward(MVCUtils.doPageUrl(cParameter,
				((AjaxRequestBean) cParameter.componentBean).getUrlForward()));
	}
}
