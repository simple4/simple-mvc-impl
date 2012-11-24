package net.simpleframework.mvc.component.ui.chart;

import java.io.IOException;

import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractChartBean extends AbstractElementBean {
	protected final ChartBean chartBean;

	public AbstractChartBean(final XmlElement xmlElement, final ChartBean chartBean) {
		super(xmlElement);
		this.chartBean = chartBean;
	}

	public ChartBean getChartBean() {
		return chartBean;
	}

	protected abstract String options() throws IOException;
}
