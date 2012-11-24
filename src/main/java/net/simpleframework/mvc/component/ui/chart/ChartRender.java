package net.simpleframework.mvc.component.ui.chart;

import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.component.AbstractComponentRender.ComponentJavascriptRender;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentRenderUtils;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ChartRender extends ComponentJavascriptRender {

	public ChartRender(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String getJavascriptCode(final ComponentParameter cParameter) {
		final ChartBean chartBean = (ChartBean) cParameter.componentBean;

		AbstractGraphTypeBean graphType;
		final IChartHandler chartHandle = (IChartHandler) cParameter.getComponentHandler();
		if (chartHandle != null) {
			graphType = chartHandle.getGraphType(cParameter);
		} else {
			graphType = chartBean.getGraphType();
		}
		if (graphType == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		final String actionFunc = ComponentRenderUtils.actionFunc(cParameter);
		sb.append(ComponentRenderUtils.initContainerVar(cParameter));
		sb.append(actionFunc).append(".chart = Flotr.draw(")
				.append(ComponentRenderUtils.VAR_CONTAINER).append(", ");
		sb.append(graphType.data()).append(", {");
		String s = chartBean.getTitle();
		if (StringUtils.hasText(s)) {
			sb.append("title: '").append(s).append("',");
		}
		s = chartBean.getSubtitle();
		if (StringUtils.hasText(s)) {
			sb.append("subtitle: '").append(s).append("',");
		}
		sb.append("grid: ").append(chartBean.getGrid().options()).append(",");
		sb.append("xaxis: ").append(chartBean.getXaxis().options()).append(",");
		sb.append("yaxis: ").append(chartBean.getYaxis().options());
		sb.append("});");
		sb.append(actionFunc).append(".draw = function() {");
		sb.append(actionFunc).append(".chart.restoreCanvas();");
		sb.append("};");
		return ComponentRenderUtils.genActionWrapper(cParameter, sb.toString());
	}
}
