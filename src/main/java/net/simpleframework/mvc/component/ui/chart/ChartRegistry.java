package net.simpleframework.mvc.component.ui.chart;

import java.util.Iterator;

import net.simpleframework.common.Convert;
import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.script.ScriptEvalUtils;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentRegistry;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentRender;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.ui.chart.BarsBean.BarDataBean;
import net.simpleframework.mvc.component.ui.chart.LinesBean.LineDataBean;
import net.simpleframework.mvc.component.ui.chart.PieBean.PieDataBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(ChartRegistry.chart)
@ComponentBean(ChartBean.class)
@ComponentRender(ChartRender.class)
@ComponentResourceProvider(ChartResourceProvider.class)
public class ChartRegistry extends AbstractComponentRegistry {
	public static final String chart = "chart";

	@Override
	public ChartBean createComponentBean(final PageParameter pParameter, final Object data) {
		final ChartBean chartBean = (ChartBean) super.createComponentBean(pParameter, data);
		if (!(data instanceof XmlElement)) {
			return chartBean;
		}

		final XmlElement component = (XmlElement) data;
		final IScriptEval scriptEval = pParameter.getScriptEval();
		XmlElement xmlElement = component.element("grid");
		if (xmlElement != null) {
			final Chart_GridBean grid = new Chart_GridBean(xmlElement, chartBean);
			grid.parseElement(scriptEval);
			chartBean.setGrid(grid);
		}
		xmlElement = component.element("xaxis");
		if (xmlElement != null) {
			final Chart_AxisBean xaxis = new Chart_AxisBean(xmlElement, chartBean);
			xaxis.parseElement(scriptEval);
			chartBean.setXaxis(xaxis);
		}
		xmlElement = component.element("yaxis");
		if (xmlElement != null) {
			final Chart_AxisBean yaxis = new Chart_AxisBean(xmlElement, chartBean);
			yaxis.parseElement(scriptEval);
			chartBean.setYaxis(yaxis);
		}

		if ((xmlElement = component.element("lines")) != null) {
			final LinesBean lines = new LinesBean(xmlElement, chartBean);
			lines.parseElement(scriptEval);
			chartBean.setGraphType(lines);
			final Iterator<?> it = xmlElement.elementIterator("line");
			while (it.hasNext()) {
				final XmlElement lineE = (XmlElement) it.next();
				final LineDataBean lineData = new LineDataBean(lineE, lines);
				lineData.parseElement(scriptEval);
				lines.getLines().add(lineData);
				final Iterator<?> it2 = lineE.elementIterator("data");
				while (it2.hasNext()) {
					final XmlElement xyE = (XmlElement) it2.next();
					final double x = Convert.toDouble(ScriptEvalUtils.replaceExpr(scriptEval,
							xyE.attributeValue("x")));
					final double y = Convert.toDouble(ScriptEvalUtils.replaceExpr(scriptEval,
							xyE.attributeValue("y")));
					lineData.addData(x, y);
				}
			}
		} else if ((xmlElement = component.element("bars")) != null) {
			final BarsBean bars = new BarsBean(xmlElement, chartBean);
			bars.parseElement(scriptEval);
			chartBean.setGraphType(bars);
			final Iterator<?> it = xmlElement.elementIterator("bar");
			while (it.hasNext()) {
				final XmlElement barE = (XmlElement) it.next();
				final BarDataBean barData = new BarDataBean(barE, bars);
				barData.parseElement(scriptEval);
				bars.getBars().add(barData);
				final Iterator<?> it2 = barE.elementIterator("data");
				while (it2.hasNext()) {
					final XmlElement xyE = (XmlElement) it2.next();
					final String catalog = ScriptEvalUtils.replaceExpr(scriptEval,
							xyE.attributeValue("catalog"));
					final double y = Convert.toDouble(ScriptEvalUtils.replaceExpr(scriptEval,
							xyE.attributeValue("y")));
					barData.addData(catalog, y);
				}
			}
		} else if ((xmlElement = component.element("pie")) != null) {
			final PieBean pie = new PieBean(xmlElement, chartBean);
			pie.parseElement(scriptEval);
			chartBean.setGraphType(pie);
			final Iterator<?> it = xmlElement.elementIterator("slice");
			while (it.hasNext()) {
				final XmlElement sliceE = (XmlElement) it.next();
				final PieDataBean slice = new PieDataBean(sliceE, pie);
				slice.parseElement(scriptEval);
				pie.getSlices().add(slice);
			}
		}
		return chartBean;
	}
}
