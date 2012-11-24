package net.simpleframework.mvc.component.ui.validatecode;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.simpleframework.common.ImageUtils;
import net.simpleframework.common.logger.Log;
import net.simpleframework.common.logger.LogFactory;
import net.simpleframework.common.web.HttpUtils;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class ValidateCodeUtils {
	static Log log = LogFactory.getLogger(ValidateCodeUtils.class);

	public static final String BEAN_ID = "validateCode_@bid";

	public static void doRender(final HttpServletRequest request, final HttpServletResponse response) {
		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			final ComponentParameter nComponentParameter = ComponentParameter.get(request, response,
					BEAN_ID);
			final IValidateCodeHandler vHandle = (IValidateCodeHandler) nComponentParameter
					.getComponentHandler();
			if (vHandle == null) {
				return;
			}
			HttpUtils.setNoCache(response);
			response.setContentType("image/png");
			response.reset();
			vHandle.doValidateCode(nComponentParameter, ImageUtils.genCode(outputStream));
		} catch (final Exception ex) {
			log.warn(ex);
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (final IOException e) {
			}
		}
	}
}
