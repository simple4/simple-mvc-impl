package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.web.HttpUtils;
import net.simpleframework.mvc.component.ComponentHtmlRenderEx;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.IComponentRegistry;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PagerRender extends ComponentHtmlRenderEx {
	public PagerRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	protected String getRelativePath(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		sb.append("/jsp/pager.jsp?").append(getBeanIdName(cParameter));
		sb.append("=").append(cParameter.hashId());
		return sb.toString();
	}

	private String getBeanIdName(final ComponentParameter cParameter) {
		final AbstractPagerHandler hdl = (AbstractPagerHandler) cParameter.getComponentHandler();
		return hdl == null ? PagerUtils.BEAN_ID_NAME : hdl.getBeanIdName();
	}

	@Override
	public String getResponseUrl(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		sb.append(PagerUtils.BEAN_ID_NAME).append("=").append(getBeanIdName(cParameter));
		final String xpParameter = PagerUtils.getXmlPathParameter(cParameter);
		if (StringUtils.hasText(xpParameter)) {
			sb.append("&").append(xpParameter);
		}
		final String pageItemsParameterName = (String) cParameter
				.getBeanProperty("pageItemsParameterName");
		final String pageItems = cParameter.getParameter(pageItemsParameterName);
		if (StringUtils.hasText(pageItems)) {
			sb.append("&").append(pageItemsParameterName);
			sb.append("=").append(pageItems);
		}
		return HttpUtils.addParameters(ComponentUtils.getResourceHomePath(PagerBean.class)
				+ getRelativePath(cParameter), sb.toString());
	}

	@Override
	public String getHtmlJavascriptCode(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		sb.append(super.getHtmlJavascriptCode(cParameter));

		sb.append(ComponentRenderUtils.actionFunc(cParameter)).append(
				".doPager = function(to, params) {");
		sb.append("var p = to.up(\".pager\");");
		sb.append("var af = $Actions[\"__doPager\"];");
		sb.append("af.container = p.up();");
		sb.append("af.selector = \"").append(cParameter.getBeanProperty("selector")).append("\";");
		sb.append("af(\"").append(getBeanIdName(cParameter)).append("=");
		sb.append(cParameter.hashId()).append("\".addParameter(params));");
		sb.append("};");
		return sb.toString();
	}

	public static void createDoPager(final ComponentParameter cParameter) {
		final PagerBean pagerBean = (PagerBean) cParameter.componentBean;
		final AjaxRequestBean ajaxRequest = (AjaxRequestBean) cParameter
				.addComponentBean("__doPager", AjaxRequestBean.class)
				.setAjaxRequestId("ajaxRequest_" + pagerBean.hashId())
				.setHandleClass(PagerAction.class);
		ajaxRequest.setAttr("container", pagerBean);
	}
}
