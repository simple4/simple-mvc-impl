package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.common.Convert;
import net.simpleframework.common.html.js.EJavascriptEvent;
import net.simpleframework.mvc.DefaultPageHandler;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.tooltip.ETipElement;
import net.simpleframework.mvc.component.ui.tooltip.ETipPosition;
import net.simpleframework.mvc.component.ui.tooltip.TipBean;
import net.simpleframework.mvc.component.ui.tooltip.TipBean.HideOn;
import net.simpleframework.mvc.component.ui.tooltip.TipBean.Hook;
import net.simpleframework.mvc.component.ui.tooltip.TooltipBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryLoad extends DefaultPageHandler {

	@Override
	public void beforeComponentRender(final PageParameter pParameter) {
		final ComponentParameter nComponentParameter = DictionaryUtils.get(pParameter);
		final DictionaryBean dictionaryBean = (DictionaryBean) nComponentParameter.componentBean;
		final AbstractComponentBean componentBean = (AbstractComponentBean) dictionaryBean
				.getDictionaryTypeBean().getAttr("$$component");
		if (componentBean != null) {
			pParameter.addComponentBean(componentBean);
		}

		if (Convert.toBool(nComponentParameter.getBeanProperty("showHelpTooltip"))) {
			final TooltipBean tooltip = (TooltipBean) pParameter.addComponentBean("helpTooltip",
					TooltipBean.class).setHandleClass(DictionaryTooltip.class);
			tooltip.addTip(new TipBean(tooltip)
					.setStem(ETipPosition.leftTop)
					.setHook(new Hook().setTarget(ETipPosition.topRight).setTip(ETipPosition.topLeft))
					.setHideOn(
							new HideOn().setTipElement(ETipElement.tip).setEvent(
									EJavascriptEvent.mouseover)).setWidth(240));
		}

		final IDictionaryHandle hdl = (IDictionaryHandle) nComponentParameter.getComponentHandler();
		if (hdl != null) {
			hdl.doDictionaryLoad(nComponentParameter);
		}
	}
}
