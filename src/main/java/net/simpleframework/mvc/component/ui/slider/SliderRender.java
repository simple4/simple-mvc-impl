package net.simpleframework.mvc.component.ui.slider;

import net.simpleframework.common.StringUtils;
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
public class SliderRender extends ComponentJavascriptRender {

	public SliderRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final SliderBean sliderBean = (SliderBean) cParameter.componentBean;

		final StringBuilder sb = new StringBuilder();
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(actionFunc).append(".slider = ").append("new $UI.Slider(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", {");
		sb.append("xMinValue: ").append(sliderBean.getXminValue()).append(",");
		sb.append("xMaxValue: ").append(sliderBean.getXmaxValue()).append(",");
		sb.append("yMinValue: ").append(sliderBean.getYminValue()).append(",");
		sb.append("yMaxValue: ").append(sliderBean.getYmaxValue()).append(",");
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "height"));
		sb.append(ComponentRenderUtils.jsonAttri(cParameter, "width"));

		sb.append("arrowImage: \"");
		final String arrowImage = sliderBean.getArrowImage();
		if (StringUtils.hasText(arrowImage)) {
			sb.append(cParameter.wrapContextPath(arrowImage));
		} else {
			sb.append(ComponentUtils.getCssResourceHomePath(cParameter));
			sb.append("/images/");
			final int xmaxValue = sliderBean.getXmaxValue();
			final int ymaxValue = sliderBean.getYmaxValue();
			if (xmaxValue > 0 && ymaxValue > 0) {
				sb.append("arrows_point.gif");
			} else if (xmaxValue > 0) {
				sb.append("arrows_h.gif");
			} else {
				sb.append("arrows_v.gif");
			}
		}
		sb.append("\"");
		sb.append("});");
		final String changeCallback = sliderBean.getJsChangeCallback();
		if (StringUtils.hasText(changeCallback)) {
			sb.append(actionFunc).append(".slider.onValuesChanged = function(slider) {");
			sb.append("var callback = function(x, y) {");
			sb.append(changeCallback);
			sb.append("};");
			sb.append("callback(slider.xValue, slider.yValue);");
			sb.append("};");
		}
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
