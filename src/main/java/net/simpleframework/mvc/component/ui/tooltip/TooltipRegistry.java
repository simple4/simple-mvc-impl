package net.simpleframework.mvc.component.ui.tooltip;

import java.util.Iterator;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentException;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(TooltipRegistry.tooltip)
@ComponentBean(TooltipBean.class)
@ComponentRender(TooltipRender.class)
@ComponentResourceProvider(TooltipResourceProvider.class)
public class TooltipRegistry extends AbstractComponentRegistry {
	public static final String tooltip = "tooltip";

	@Override
	public TooltipBean createComponentBean(final PageParameter pParameter, final Object data) {
		final TooltipBean tooltipBean = (TooltipBean) super.createComponentBean(pParameter, data);
		if (!(data instanceof XmlElement)) {
			return tooltipBean;
		}
		final XmlElement component = (XmlElement) data;
		final IScriptEval scriptEval = pParameter.getScriptEval();
		final Iterator<?> it = component.elementIterator("tip");
		while (it.hasNext()) {
			final XmlElement xmlElement = (XmlElement) it.next();
			final TipBean tip = new TipBean(xmlElement, tooltipBean);
			tip.parseElement(scriptEval);
			final String ajaxRequest = tip.getAjaxRequest();
			if (StringUtils.hasText(ajaxRequest)) {
				final AjaxRequestBean ajaxRequestBean = (AjaxRequestBean) pParameter
						.getComponentBeanByName(ajaxRequest);
				if (ajaxRequestBean == null) {
					if (pParameter.getComponentBeanByName(ajaxRequest) == null) {
						throw ComponentException.wrapException_ComponentRef(ajaxRequest);
					}
				} else {
					ajaxRequestBean.setShowLoading(false);
				}
			}
			final XmlElement hideOnElement = xmlElement.element("hideOn");
			if (hideOnElement != null) {
				final TipBean.HideOn hideOn = new TipBean.HideOn(hideOnElement);
				hideOn.parseElement(scriptEval);
				tip.setHideOn(hideOn);
			}

			final XmlElement hookElement = xmlElement.element("hook");
			if (hookElement != null) {
				final TipBean.Hook hook = new TipBean.Hook(hookElement);
				hook.parseElement(scriptEval);
				tip.setHook(hook);
			}

			tooltipBean.getTips().add(tip);
		}
		return tooltipBean;
	}
}
