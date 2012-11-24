package net.simpleframework.mvc.component.base.ajaxrequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.simpleframework.common.Convert;
import net.simpleframework.common.I18n;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.ThrowableUtils;
import net.simpleframework.common.coll.KVMap;
import net.simpleframework.common.coll.ParameterMap;
import net.simpleframework.common.logger.Log;
import net.simpleframework.common.logger.LogFactory;
import net.simpleframework.mvc.AbstractBasePage;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.JavascriptForward;
import net.simpleframework.mvc.JsonForward;
import net.simpleframework.mvc.LocalSessionCache;
import net.simpleframework.mvc.MVCContextFactory;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AjaxRequestUtils {
	public static final String BEAN_ID = "ajax_@bid";

	static Log log = LogFactory.getLogger(AjaxRequestUtils.class);

	public static void doAjaxRequest(final HttpServletRequest request,
			final HttpServletResponse response) {
		final ComponentParameter cParameter = ComponentParameter.get(request, response, BEAN_ID);
		final KVMap json = new KVMap();
		IForward forward = null;
		boolean callback = true;
		if (cParameter.componentBean == null) {
			forward = new JsonForward("exception", createException(ComponentException.of(I18n
					.$m("AjaxRequestUtils.0"))));
		} else {
			try {
				forward = MVCContextFactory.permission().accessForward(cParameter,
						cParameter.getBeanProperty("role"));
				if (forward != null) {
					callback = false;
				}
				final HttpSession httpSession = cParameter.getSession();
				final IAjaxRequestHandler ajaxRequestHandle = (IAjaxRequestHandler) cParameter
						.getComponentHandler();
				if (forward == null && ajaxRequestHandle != null) {
					boolean doing = false;
					final String method = (String) cParameter.getBeanProperty("handleMethod");
					String dKey = null;
					if (!(Boolean) cParameter.getBeanProperty("parallel")) {
						dKey = ajaxRequestHandle.getClass().getName();
						if (StringUtils.hasText(method)) {
							dKey += "#" + method;
						}
						final Boolean bObj = (Boolean) LocalSessionCache.get(httpSession, dKey);
						if (bObj != null && bObj.booleanValue()) {
							doing = true;
						} else {
							LocalSessionCache.put(httpSession, dKey, Boolean.TRUE);
						}
					}
					if (!doing) {
						try {
							if (StringUtils.hasText(method)
									&& !(ajaxRequestHandle instanceof AbstractBasePage.AjaxRequest)) {
								final Method methodObject = ajaxRequestHandle.getClass().getMethod(method,
										ComponentParameter.class);
								forward = (IForward) methodObject.invoke(ajaxRequestHandle, cParameter);
							} else {
								forward = ajaxRequestHandle.ajaxProcess(cParameter);
							}
						} catch (Throwable th) {
							th = ThrowableUtils.convertThrowable(th);
							log.error(th);
							forward = new JsonForward("exception", createException(th));
						} finally {
							if (dKey != null) {
								LocalSessionCache.remove(httpSession, dKey);
							}
						}
					}
				}

				final String id = StringUtils.text(
						(String) cParameter.getBeanProperty("ajaxRequestId"),
						(String) cParameter.getBeanProperty("name"));
				json.add("id", id);
			} catch (final Throwable th) {
				forward = new JsonForward("exception", createException(th));
			}
		}

		final String responseText = forward != null ? forward.getResponseText(cParameter) : "";
		json.add("rt", responseText.replace("\t", ""));
		json.add("cb", callback);
		json.add("isJSON", forward instanceof JsonForward);
		json.add("isJavascript", forward instanceof JavascriptForward);
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (final IOException e) {
			throw ComponentException.of(e);
		}
		out.write(json.toJSON());
		out.flush();
	}

	public static ParameterMap createException(final Throwable th) {
		final ParameterMap exception = new ParameterMap();
		exception.put("title", ThrowableUtils.getThrowableMessage(th));
		final String detail = Convert.toString(th);
		exception.put("detail", detail);
		exception.put("hash", StringUtils.hash(detail));
		return exception;
	}
}
