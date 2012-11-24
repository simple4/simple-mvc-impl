package net.simpleframework.mvc.component.ui.chart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.simpleframework.common.JsonUtils;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class LinesBean extends AbstractGraphTypeBean {

	private Collection<LineDataBean> lines;

	public LinesBean(final XmlElement xmlElement, final ChartBean chartBean) {
		super(xmlElement, chartBean);
		setFillOpacity(0.4);
	}

	public LinesBean(final ChartBean chartBean) {
		this(null, chartBean);
	}

	public Collection<LineDataBean> getLines() {
		if (lines == null) {
			lines = new ArrayList<LineDataBean>();
		}
		return lines;
	}

	@Override
	protected String data() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i = 0;
		for (final LineDataBean lineData : getLines()) {
			if (i++ > 0) {
				sb.append(",");
			}
			sb.append("{");
			lineData.appendAttr(sb);
			sb.append("lines: ").append(options()).append(",");
			options_points(sb);
			options_mouse(sb);
			sb.append("data: ").append(JsonUtils.toJSON(lineData.getData()));
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
	}

	public static class LineDataBean extends AbstractGraphData {

		private List<Number[]> data;

		public LineDataBean(final XmlElement xmlElement, final AbstractGraphTypeBean graphType) {
			super(xmlElement, graphType);
		}

		public LineDataBean(final AbstractGraphTypeBean graphType) {
			this(null, graphType);
		}

		public List<Number[]> getData() {
			if (data == null) {
				data = new ArrayList<Number[]>();
			}
			return data;
		}

		public void addData(final Number x, final Number y) {
			getData().add(new Number[] { x, y });
		}
	}
}
