package net.simpleframework.mvc.component.base.submit;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.MVCContextFactory;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class SubmitRender extends ComponentJavascriptRender {

	public SubmitRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final SubmitBean submitBean = (SubmitBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();

		final String confirmMessage = submitBean.getConfirmMessage();
		if (StringUtils.hasText(confirmMessage)) {
			sb.append("if (!confirm(\"").append(JavascriptUtils.escape(confirmMessage));
			sb.append("\")) { return; }");
		}

		final String formName = submitBean.getFormName();
		sb.append("var form = $(\"").append(formName).append("\") || document.").append(formName)
				.append(";");
		sb.append("if (!form) { alert('").append($m("SubmitRender.0")).append("'); return; }");
		final IForward forward = MVCContextFactory.permission().accessForward(cParameter,
				cParameter.getBeanProperty("role"));
		if (forward != null) {
			sb.append("var win = parent || window;");
			sb.append("var ele = new Element('DIV'); $(win.document.body).insert(ele);");
			sb.append("new win.$UI.AjaxRequest(ele, '")
					.append(JavascriptUtils.escape(forward.getResponseText(cParameter))).append("', '")
					.append(formName).append("');");
			sb.append("return;");
		}
		final String beanId = submitBean.hashId();
		sb.append("form.action=\"").append(ComponentUtils.getResourceHomePath(SubmitBean.class))
				.append("/jsp/submit.jsp?").append(SubmitUtils.BEAN_ID).append("=").append(beanId)
				.append("\";");
		sb.append("form.action = form.action.addParameter(arguments[0]);");
		sb.append("form.method=\"post\";");
		if (submitBean.isBinary()) {
			sb.append("form.encoding = \"multipart/form-data\";");
		}
		sb.append("form.submit();");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
