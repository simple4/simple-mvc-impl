package net.simpleframework.mvc.component.base.ajaxrequest;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.js.JavascriptUtils;
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
public class AjaxRequestRender extends ComponentJavascriptRender {
	public AjaxRequestRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		final StringBuilder sb = new StringBuilder();
		final String containerId = (String) cParameter.getBeanProperty("updateContainerId");
		sb.append("if (").append(actionFunc).append(".doInit(");
		if (StringUtils.hasText(containerId)) {
			sb.append("\"").append(containerId).append("\", ");
		} else {
			sb.append("null, ");
		}
		final String confirmMessage = (String) cParameter.getBeanProperty("confirmMessage");
		if (StringUtils.hasText(confirmMessage)) {
			sb.append("\"").append(JavascriptUtils.escape(confirmMessage)).append("\", ");
		} else {
			sb.append("null, ");
		}

		sb.append(cParameter.getBeanProperty("parallel")).append(", ")
				.append(cParameter.getBeanProperty("showLoading")).append(", ")
				.append(cParameter.getBeanProperty("loadingModal")).append(", ")
				.append(cParameter.getBeanProperty("disabledTriggerAction"));
		sb.append(")) return;");

		// doComplete
		sb.append("function dc(req) { ").append(actionFunc).append(".doLoaded(req); };");

		sb.append(actionFunc).append(".doCallback = function(req, responseText, json, trigger) {");
		sb.append("var act = ").append(actionFunc).append(".jsCompleteCallback;");
		sb.append("if (act) { act(req, responseText, json, trigger); }");
		final String callback = (String) cParameter.getBeanProperty("jsCompleteCallback");
		if (StringUtils.hasText(callback)) {
			sb.append("else {").append(callback).append("}");
		}
		sb.append("};");

		sb.append("var p=\"ajax_request=true&").append(AjaxRequestUtils.BEAN_ID);
		sb.append("=").append(cParameter.hashId()).append("\";");
		ComponentRenderUtils.appendParameters(sb, cParameter, "p");
		sb.append("p = p.addParameter(").append(actionFunc).append(".doParameters(arguments[0]));");
		sb.append("new Ajax.Request(\"");
		sb.append(ComponentUtils.getResourceHomePath(AjaxRequestBean.class)).append(
				"/jsp/ajax_request.jsp\", {");
		sb.append("postBody: p,");

		final String encoding = (String) cParameter.getBeanProperty("encoding");
		if (StringUtils.hasText(encoding)) {
			sb.append("encoding: \"").append(encoding).append("\",");
		}
		sb.append("onComplete: function(req) {");
		sb.append(actionFunc).append(".doComplete(req, ");
		if (StringUtils.hasText(containerId)) {
			sb.append("\"").append(containerId).append("\", ");
		} else {
			sb.append("null, ");
		}

		sb.append(cParameter.getBeanProperty("updateContainerCache")).append(", ");
		final EThrowException throwException = (EThrowException) cParameter
				.getBeanProperty("throwException");
		sb.append(throwException == EThrowException.alert);
		sb.append(");");
		sb.append("}, onException: dc, onFailure: dc, on404: dc });");
		final StringBuilder sb2 = new StringBuilder();
		sb2.append("__ajax_actions_init(").append(actionFunc).append(", '")
				.append(cParameter.getBeanProperty("name")).append("');");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString(), sb2.toString());
	}
}
