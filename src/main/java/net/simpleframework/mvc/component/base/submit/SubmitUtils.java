package net.simpleframework.mvc.component.base.submit;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.simpleframework.common.IoUtils;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.web.HttpUtils;
import net.simpleframework.mvc.AbstractBasePage;
import net.simpleframework.mvc.AbstractUrlForward;
import net.simpleframework.mvc.MVCContextFactory;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class SubmitUtils {
	public static final String BEAN_ID = "submit_@bid";

	public static ComponentParameter get(final PageRequestResponse rRequest) {
		return ComponentParameter.get(rRequest, BEAN_ID);
	}

	public static ComponentParameter get(final HttpServletRequest request,
			final HttpServletResponse response) {
		return ComponentParameter.get(request, response, BEAN_ID);
	}

	public static void doSubmit(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		final ComponentParameter nComponentParameter = get(request, response);
		final ISubmitHandler submitHandle = (ISubmitHandler) nComponentParameter
				.getComponentHandler();
		if (submitHandle != null) {
			final String method = (String) nComponentParameter.getBeanProperty("handleMethod");
			AbstractUrlForward forward;
			if ((Boolean) nComponentParameter.getBeanProperty("binary")) {
				nComponentParameter.request = MVCContextFactory.ctx().createMultipartPageRequest(
						request,
						(int) IoUtils.toFileSize((String) nComponentParameter
								.getBeanProperty("fileSizeLimit")));
			}
			try {
				if (StringUtils.hasText(method) && !(submitHandle instanceof AbstractBasePage.Submit)) {
					final Method methodObject = submitHandle.getClass().getMethod(method,
							ComponentParameter.class);
					forward = (UrlForward) methodObject.invoke(submitHandle, nComponentParameter);
				} else {
					forward = submitHandle.submit(nComponentParameter);
				}
				if (forward != null) {
					nComponentParameter.loc(HttpUtils.addParameters(forward.getUrl(), AbstractUrlForward
							.putRequestData(nComponentParameter,
									(String) nComponentParameter.getBeanProperty("includeRequestData"))));
				}
			} catch (final Exception e) {
				throw ComponentException.of(e);
			}
		}
	}
}
