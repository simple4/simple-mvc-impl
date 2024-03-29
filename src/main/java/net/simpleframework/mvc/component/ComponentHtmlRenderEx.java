package net.simpleframework.mvc.component;

import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.UrlForward;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentHtmlRender;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.base.ajaxrequest.DefaultAjaxRequestHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class ComponentHtmlRenderEx extends ComponentHtmlRender {

	public ComponentHtmlRenderEx(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getHtmlJavascriptCode(final ComponentParameter cParameter) {
		final AbstractContainerBean containerBean = (AbstractContainerBean) cParameter.componentBean;
		// 给container组件设置refresh函数
		final AjaxRequestBean ajaxRequestBean = (AjaxRequestBean) containerBean
				.getAttr("ajaxRequest");
		if (ajaxRequestBean == null) {
			return "";
		}
		final StringBuilder sb2 = new StringBuilder();
		sb2.append("var cf = $Actions[\"");
		sb2.append(cParameter.getBeanProperty("name")).append("\"];");
		sb2.append("var af = $Actions[\"").append(ajaxRequestBean.getName()).append("\"];");
		sb2.append("af.container = cf.container || '")
				.append(cParameter.getBeanProperty("containerId")).append("';");
		sb2.append("af.selector = cf.selector || '").append(cParameter.getBeanProperty("selector"))
				.append("';");
		sb2.append("af(arguments[0]);");

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.genActionWrapper(cParameter, sb2.toString(), null, false,
				false));

		// refresh函数
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".refresh = function(params) {");
		sb.append(actionFunc).append("(params);");
		sb.append("};");
		return sb.toString();
	}

	public static void createAjaxRequest(final ComponentParameter cParameter) {
		final AbstractContainerBean containerBean = (AbstractContainerBean) cParameter.componentBean;
		final AjaxRequestBean ajaxRequest = (AjaxRequestBean) cParameter.addComponentBean(
				"ajaxRequest_" + containerBean.hashId(), AjaxRequestBean.class).setHandleClass(
				RefreshAction.class);
		ajaxRequest.setAttr("container", containerBean);
		containerBean.setAttr("ajaxRequest", ajaxRequest);
	}

	public static class RefreshAction extends DefaultAjaxRequestHandler {
		@Override
		public Object getBeanProperty(final ComponentParameter cParameter, final String beanProperty) {
			if ("showLoading".equals(beanProperty) || "loadingModal".equals(beanProperty)) {
				final AbstractContainerBean containerBean = (AbstractContainerBean) cParameter.componentBean
						.getAttr("container");
				if (BeanUtils.hasProperty(containerBean, beanProperty)) {
					return ComponentParameter.get(cParameter, containerBean).getBeanProperty(
							beanProperty);
				}
			}
			return super.getBeanProperty(cParameter, beanProperty);
		}

		@Override
		public IForward ajaxProcess(final ComponentParameter cParameter) {
			final AbstractContainerBean containerBean = (AbstractContainerBean) cParameter.componentBean
					.getAttr("container");
			return new UrlForward(((ComponentHtmlRender) containerBean.getComponentRegistry()
					.getComponentRender()).getResponseUrl(ComponentParameter.get(cParameter,
					containerBean)));
		}
	}
}
