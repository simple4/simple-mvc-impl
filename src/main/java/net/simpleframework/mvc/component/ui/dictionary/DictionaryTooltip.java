package net.simpleframework.mvc.component.ui.dictionary;

import static net.simpleframework.common.I18n.$m;

import java.util.Collection;

import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.AbstractDictionaryTypeBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryListBean;
import net.simpleframework.mvc.component.ui.dictionary.DictionaryBean.DictionaryTreeBean;
import net.simpleframework.mvc.component.ui.tooltip.AbstractTooltipHandler;
import net.simpleframework.mvc.component.ui.tooltip.TipBean;
import net.simpleframework.mvc.component.ui.tooltip.TooltipBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class DictionaryTooltip extends AbstractTooltipHandler {

	@Override
	public Object getBeanProperty(final ComponentParameter cParameter, final String beanProperty) {
		if ("name".equals(beanProperty)) {
			AbstractComponentBean componentBean = DictionaryUtils.get(cParameter).componentBean;
			if (componentBean == null) {
				componentBean = (AbstractComponentBean) cParameter
						.getRequestAttr(DictionaryUtils.BEAN_ID);
			}
			return "tooltip_" + componentBean.hashId();
		}
		return super.getBeanProperty(cParameter, beanProperty);
	}

	@Override
	public Collection<TipBean> getElementTips(final ComponentParameter cParameter) {
		final TooltipBean tooltipBean = (TooltipBean) cParameter.componentBean;
		final Collection<TipBean> tips = tooltipBean.getTips();
		if (tips != null && tips.size() > 0) {
			final TipBean tip = tips.iterator().next();
			final ComponentParameter nComponentParameter = DictionaryUtils.get(cParameter);
			final DictionaryBean dictionaryBean = (DictionaryBean) nComponentParameter.componentBean;
			tip.setSelector("#help" + dictionaryBean.hashId());
			final AbstractDictionaryTypeBean dictionaryType = dictionaryBean.getDictionaryTypeBean();
			String key = null;
			final boolean multiple = (Boolean) nComponentParameter.getBeanProperty("multiple");
			if (dictionaryType instanceof DictionaryTreeBean) {
				key = multiple ? "getElementTips.0" : "getElementTips.1";
			} else if (dictionaryType instanceof DictionaryListBean) {
				key = multiple ? "getElementTips.2" : "getElementTips.3";
			}
			if (key != null) {
				tip.setContent($m(key));
			}
			final IDictionaryHandle dHandle = (IDictionaryHandle) nComponentParameter
					.getComponentHandler();
			if (dHandle != null) {
				dHandle.doTip(nComponentParameter, tip);
			}
		}
		return tips;
	}
}
