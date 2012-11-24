package net.simpleframework.mvc.component.ui.validatecode;

import static net.simpleframework.common.I18n.$m;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.simpleframework.mvc.component.AbstractComponentHandler;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DefaultValidateCodeHandler extends AbstractComponentHandler implements
		IValidateCodeHandler {

	@Override
	public void doValidateCode(final ComponentParameter cParameter, final String code) {
		cParameter.setSessionAttr(GEN_CODE, code);
	}

	public static String getErrorString() {
		return $m("DefaultValidateCodeHandle.0");
	}

	public static String getValidateCode(final HttpSession session) {
		return (String) session.getAttribute(GEN_CODE);
	}

	public static boolean isValidateCode(final HttpServletRequest request, final String inputName) {
		final String validateCode = request.getParameter(inputName);
		return validateCode != null ? validateCode.equalsIgnoreCase(getValidateCode(request
				.getSession())) : true;
	}
}
