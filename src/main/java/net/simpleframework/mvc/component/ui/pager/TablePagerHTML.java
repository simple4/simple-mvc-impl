package net.simpleframework.mvc.component.ui.pager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.simpleframework.common.Convert;
import net.simpleframework.common.ID;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.ado.ColumnData;
import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.common.bean.IIdBeanAware;
import net.simpleframework.common.coll.KVMap;
import net.simpleframework.common.html.HtmlUtils;
import net.simpleframework.common.html.element.EInputType;
import net.simpleframework.common.html.element.InputElement;
import net.simpleframework.mvc.MVCUtils;
import net.simpleframework.mvc.PageRequestResponse;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentUtils;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class TablePagerHTML {
	public static String renderTable(final ComponentParameter cParameter) {
		final String groupColumn;
		if ((cParameter.componentBean instanceof GroupTablePagerBean)
				&& StringUtils
						.hasText(groupColumn = (String) cParameter.getBeanProperty("groupColumn"))) {
			return renderTable(cParameter, new RenderGroupTable(groupColumn));
		} else {
			return renderTable(cParameter, new RenderTable());
		}
	}

	static String renderTable(final ComponentParameter cParameter, final RenderTable rHandle) {
		final AbstractTablePagerSchema tablePagerData = TablePagerUtils.getTablePagerData(cParameter);
		final TablePagerBean tablePager = (TablePagerBean) cParameter.componentBean;
		final StringBuilder sb = new StringBuilder();
		sb.append("<div class=\"tablepager\" ");
		if (tablePager.isShowBorder()) {
			sb.append("style=\"padding: 4px;border-width: 1px;\" ");
		}
		sb.append("scrollHead=\"").append(tablePager.isScrollHead()).append("\">");
		final Object firstBean = cParameter.getRequestAttr(ITablePagerHandler.FIRST_ITEM);
		final Object lastBean = cParameter.getRequestAttr(ITablePagerHandler.LAST_ITEM);
		if (firstBean instanceof IIdBeanAware && lastBean instanceof IIdBeanAware) {
			final ID firstId = ((IIdBeanAware) firstBean).getId();
			final ID lastId = ((IIdBeanAware) lastBean).getId();
			sb.append(new InputElement("firstRow", EInputType.hidden).setText(String.valueOf(firstId)));
			sb.append(new InputElement("lastRow", EInputType.hidden).setText(String.valueOf(lastId)));
		}
		final KVMap beanAttributes = createBeanAttributes(cParameter);
		sb.append(rHandle.renderHeader(cParameter, tablePagerData, beanAttributes));
		sb.append(rHandle.renderBody(cParameter, tablePagerData, beanAttributes));
		sb.append("</div>");
		return sb.toString();
	}

	static KVMap createBeanAttributes(final ComponentParameter cParameter) {
		final KVMap beanAttributes = new KVMap();
		beanAttributes.add("rowMargin", Convert.toInt(cParameter.getBeanProperty("rowMargin")));
		beanAttributes
				.add("showCheckbox", Convert.toBool(cParameter.getBeanProperty("showCheckbox")));
		beanAttributes.add("showVerticalLine",
				Convert.toBool(cParameter.getBeanProperty("showVerticalLine")));
		final boolean showLineNo = Convert.toBool(cParameter.getBeanProperty("showLineNo"));
		if (showLineNo) {
			beanAttributes.add("pageNumber", PagerUtils.getPageNumber(cParameter));
			beanAttributes.add("pageItems", PagerUtils.getPageItems(cParameter));
		}
		beanAttributes.add("showLineNo", showLineNo);
		beanAttributes.add("showFilterBar",
				Convert.toBool(cParameter.getBeanProperty("showFilterBar")));
		beanAttributes.add("detailField", cParameter.getBeanProperty("detailField"));

		beanAttributes.add("headHeight", Convert.toInt(cParameter.getBeanProperty("headHeight")));

		beanAttributes.add("name", cParameter.getBeanProperty("name"));
		beanAttributes.add("height", cParameter.getBeanProperty("height"));
		return beanAttributes;
	}

	static int fixWidth(final PageRequestResponse rRequest, final int width) {
		// 当table-layout=fixed时，webkit的解释尽然去掉了padding，怪异
		// return HttpUtils.isWebKit(rRequest.request) ? width + 8 : width;
		return width;
	}

	static int getRownumWidth(final ComponentParameter cParameter, final int pageNumber,
			final int pageItems) {
		return fixWidth(
				cParameter,
				String.valueOf(
						Math.max(pageNumber, 1) * pageItems == Integer.MAX_VALUE ? 999 : pageItems)
						.length() * 8);
	}

	static String filterColumnKey(final String col) {
		return TablePagerUtils.PARAM_FILTER + "_" + col;
	}

	static String filterEncode(final String filter) {
		if (filter == null) {
			return "";
		}
		return StringUtils.encodeHex(filter.getBytes());
	}

	static String filterDecode(final String filter) {
		return StringUtils.decodeHexString(filter);
	}

	static class RenderTable {

		String renderHeader(final ComponentParameter cParameter,
				final AbstractTablePagerSchema tablePagerData, final KVMap beanAttributes) {
			final ITablePagerHandler pHandle = (ITablePagerHandler) cParameter.getComponentHandler();
			final StringBuilder sb = new StringBuilder();
			final int marginRight = Convert.toInt(cParameter.getParameter("tbl_thead_margin_right"));
			sb.append("<div class=\"thead\"><table style=\"width:100%;padding-right:").append(
					marginRight);
			sb.append("px;\" class=\"fixed_table\" cellpadding=\"0\" cellspacing=\"0\"><tr>");

			// showDetail
			final String detailField = (String) beanAttributes.get("detailField");
			final boolean showDetail = StringUtils.hasText(detailField);
			if (showDetail) {
				sb.append("<td class='plus'></td>");
			}
			int numWidth = 0;
			if ((Boolean) beanAttributes.get("showLineNo")) {
				final int pageNumber = (Integer) beanAttributes.get("pageNumber");
				final int pageItems = (Integer) beanAttributes.get("pageItems");
				sb.append("<td width=\"")
						.append(numWidth = getRownumWidth(cParameter, pageNumber, pageItems))
						.append("\">&nbsp;</td>");
			}
			final String componentName = (String) beanAttributes.get("name");
			final boolean showCheckbox = (Boolean) beanAttributes.get("showCheckbox");
			if (showCheckbox) {
				sb.append("<td class=\"cb\"><input type=\"checkbox\" ");
				sb.append("onclick=\"$Actions['").append(componentName);
				sb.append("'].checkAll(this);\" /></td>");
			}

			final int headHeight = (Integer) beanAttributes.get("headHeight");
			int i = 0;
			for (final Map.Entry<String, TablePagerColumn> entry : tablePagerData
					.getTablePagerColumns(cParameter).entrySet()) {
				final TablePagerColumn pagerColumn = entry.getValue();
				if (!pagerColumn.isVisible()) {
					continue;
				}
				final String columnName = pagerColumn.getColumnName();
				sb.append("<td");
				final String style = pagerColumn.toStyle(cParameter, true);
				if (StringUtils.hasText(style)) {
					sb.append(" style=\"").append(style).append("\"");
				}
				if (!pagerColumn.isNowrap()) {
					sb.append(" class=\"wrap_text\"");
				}
				sb.append(" columnName=\"").append(pagerColumn.getColumnName()).append("\" resize=\"")
						.append(pagerColumn.isResize()).append("\"><div class=\"box\"");
				if (headHeight > 0) {
					sb.append(" style=\"height: ").append(headHeight).append("px;\"");
				}
				sb.append(">");

				sb.append("<div class=\"lbl\">");
				String columnText = StringUtils.text(pagerColumn.getColumnText(), "&nbsp;");
				columnText = HtmlUtils.convertHtmlLines(columnText);
				if (pagerColumn.isSort()) {
					final String col2 = cParameter.getParameter(TablePagerUtils.PARAM_SORT_COL);
					StringBuilder img = null;
					if (columnName.equals(col2)) {
						final String sort = cParameter.getParameter(TablePagerUtils.PARAM_SORT);
						if ("up".equals(sort) || "down".equals(sort)) {
							img = new StringBuilder();
							img.append("<img style=\"margin-left: 4px;\" src=\"");
							img.append(MVCUtils.getPageResourcePath());
							img.append("/images/").append(sort).append(".png\" />");
						}
					}
					sb.append("<a onclick=\"$Actions['").append(componentName);
					sb.append("'].sort('").append(columnName).append("');\">");
					sb.append(columnText).append("</a>");
					if (img != null) {
						sb.append(img);
					}
				} else {
					sb.append(columnText);
				}
				sb.append("</div>");
				if (((Boolean) beanAttributes.get("showVerticalLine") || pagerColumn.isResize())
						&& (showCheckbox || i > 0)) {
					sb.append("<div class=\"sep\"></div>");
				}
				sb.append("</div>");
				final String tooltip = pagerColumn.getTooltip();
				if (StringUtils.hasText(tooltip)) {
					sb.append("<div style=\"display: none;\">");
					sb.append(HtmlUtils.convertHtmlLines(tooltip)).append("</div>");
				}
				sb.append("</td>");
				i++;
			}
			sb.append("</tr></table></div>");

			// fast filter pane
			if ((Boolean) beanAttributes.get("showFilterBar")) {
				sb.append("<div class=\"tfilter\"><table style=\"width:100%;padding-right:");
				sb.append(marginRight);
				sb.append("px;\" class=\"fixed_table\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
				if (showDetail) {
					sb.append("<td class='plus'></td>");
				}
				if (numWidth > 0) {
					sb.append("<td width=\"").append(numWidth).append("\">&nbsp;</td>");
				}
				if (showCheckbox) {
					sb.append("<td class=\"cb\">&nbsp;</td>");
				}
				final Map<String, ColumnData> dbColumns = pHandle.getFilterColumns(cParameter);
				for (final Map.Entry<String, TablePagerColumn> entry : tablePagerData
						.getTablePagerColumns(cParameter).entrySet()) {
					final TablePagerColumn pagerColumn = entry.getValue();
					if (!pagerColumn.isVisible()) {
						continue;
					}
					final String columnName = pagerColumn.getColumnName();
					sb.append("<td");
					final String style = pagerColumn.toStyle(cParameter, true);
					if (StringUtils.hasText(style)) {
						sb.append(" style=\"").append(style).append("\"");
					}
					sb.append("><div class=\"box\">");
					if (pagerColumn.isFilter()) {
						sb.append("<input type=\"text\" params=\"")
								.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=")
								.append(columnName).append("\"");
						final String val = columnName.equals(cParameter
								.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL)) ? cParameter
								.getParameter(TablePagerUtils.PARAM_FILTER) : cParameter
								.getParameter(filterColumnKey(columnName));
						if (StringUtils.hasText(val) && !TablePagerUtils.PARAM_FILTER_NONE.equals(val)) {
							sb.append(" value=\"");
							sb.append(filterDecode(StringUtils.split(val)[1])).append("\"");
						}
						sb.append(" />");
						if (dbColumns != null && dbColumns.containsKey(columnName)) {
							sb.append(
									"<a class=\"del\" style=\"float: left;\" title=\"#(TablePagerUtils.8)\"")
									.append(" onclick=\"TablePagerUtils.filterDelete(this, '")
									.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=");
							sb.append(columnName).append("');\"></a>");
						} else {
							sb.append("<a class=\"image_filter\" title=\"#(TablePagerUtils.7)\"");
							sb.append(" onclick=\"TablePagerUtils.filterWindow(this, '")
									.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=");
							sb.append(columnName).append("&").append(TablePagerUtils.PARAM_FILTER)
									.append("=');\"></a>");
						}
					}
					sb.append("</div></td>");
				}
				sb.append("</tr></table></div>");
			}
			return sb.toString();
		}

		void appendTbody(final ComponentParameter cParameter, final int dataSize,
				final StringBuilder sb, final KVMap beanAttributes) {
			sb.append("<div class=\"tbody\"");
			final List<?> data = PagerUtils.getPagerList(cParameter);
			if (data.size() > 0) {
				String height = cParameter.getParameter("tbl_tbody_height");
				if (!StringUtils.hasText(height)) {
					height = (String) beanAttributes.get("height");
				}
				final int h = Convert.toInt(height);
				if (h > 0) {
					height = h + "px";
				}
				if (StringUtils.hasText(height)) {
					sb.append(" style=\"height: ").append(height).append("\"");
				}
			}
			sb.append(">");
		}

		String renderBody(final ComponentParameter cParameter,
				final AbstractTablePagerSchema tablePagerData, final KVMap beanAttributes) {
			final List<?> data = PagerUtils.getPagerList(cParameter);
			final StringBuilder sb = new StringBuilder();
			appendTbody(cParameter, data.size(), sb, beanAttributes);
			for (int i = 0; i < data.size(); i++) {
				sb.append(buildRow(cParameter, beanAttributes, data.get(i), tablePagerData, i));
			}
			sb.append("</div>");
			return sb.toString();
		}

		String buildRow(final ComponentParameter cParameter, final KVMap beanAttributes,
				final Object bean, final AbstractTablePagerSchema tablePagerData, final int index) {
			final StringBuilder sb = new StringBuilder();
			final Map<String, Object> rowData = tablePagerData.getRowData(cParameter, bean);
			if (rowData == null) {
				return sb.toString();
			}

			sb.append("<div class=\"titem\" style=\"margin-top: ");
			sb.append(-1 + (Integer) beanAttributes.get("rowMargin")).append("px;\"");
			final Map<String, Object> rowAttributes = tablePagerData
					.getRowAttributes(cParameter, bean);
			if (rowAttributes != null) {
				for (final Map.Entry<String, Object> entry : rowAttributes.entrySet()) {
					sb.append(" ").append(entry.getKey()).append("=");
					sb.append("\"").append(entry.getValue()).append("\"");
				}
			}
			sb.append("><table style=\"width: 100%;\" class=\"fixed_table\" ");
			sb.append("cellpadding=\"0\" cellspacing=\"0\"><tr class=\"itr\">");

			// showDetail
			final String detailField = (String) beanAttributes.get("detailField");
			final String detailVal = detailField != null ? (String) rowData.get(detailField) : null;
			final boolean showDetail = StringUtils.hasText(detailField);
			if (showDetail) {
				sb.append("<td class='plus'>");
				if (StringUtils.hasText(detailVal)) {
					sb.append("<img src=\"").append(ComponentUtils.getCssResourceHomePath(cParameter));
					sb.append("/images/toggle2.gif\" />");
				}
				sb.append("</td>");
			}

			if ((Boolean) beanAttributes.get("showLineNo")) {
				final int pageNumber = (Integer) beanAttributes.get("pageNumber");
				final int pageItems = (Integer) beanAttributes.get("pageItems");
				sb.append("<td width=\"").append(getRownumWidth(cParameter, pageNumber, pageItems))
						.append("\" class=\"rownum\">");
				sb.append(Math.max(pageNumber - 1, 0) * pageItems + index + 1);
				sb.append("</td>");
			}
			if ((Boolean) beanAttributes.get("showCheckbox")) {
				sb.append("<td class=\"cb\"><input type=\"checkbox\" value=\"");
				sb.append(tablePagerData.getRowAttributes(cParameter, bean).get(
						AbstractTablePagerSchema.ROW_ID));
				sb.append("\" /></td>");
			}

			final TablePagerColumnMap pagerColumns = tablePagerData.getTablePagerColumns(cParameter);
			final boolean showVerticalLine = (Boolean) beanAttributes.get("showVerticalLine");
			for (final Map.Entry<String, TablePagerColumn> entry : pagerColumns.entrySet()) {
				final String key = entry.getKey();
				final TablePagerColumn pagerColumn = entry.getValue();
				if (!pagerColumn.isVisible()) {
					continue;
				}

				if (index == 0 && !StringUtils.hasText(pagerColumn.getPropertyClass())) {
					try {
						Object value;
						if ((value = BeanUtils.getProperty(bean, key)) != null) {
							pagerColumn.setPropertyClass(value.getClass().getName());
						}
					} catch (final Exception e) {
					}
				}
				sb.append("<td");
				final String style = pagerColumn.toStyle(cParameter, false);
				if (StringUtils.hasText(style)) {
					sb.append(" style=\"").append(style).append("\"");
				}
				String className = "";
				if (!pagerColumn.isNowrap()) {
					className = "wrap_text";
				}
				if (showVerticalLine) {
					className += " vline";
				}
				if (className.length() > 0) {
					sb.append(" class=\"").append(className).append("\"");
				}
				sb.append("><div class=\"lbl\"");
				final String lblStyle = pagerColumn.getLblStyle();
				if (StringUtils.hasText(lblStyle)) {
					sb.append(" style=\"").append(lblStyle).append("\"");
				}
				final Object value = rowData.get(key);
				sb.append(">").append(value == null ? "&nbsp;" : pagerColumn.objectToString(value))
						.append("</div></td>");
			}
			sb.append("</tr></table></div>");
			if (showDetail && StringUtils.hasText(detailVal)) {
				sb.append("<div class=\"tdetail\" style=\"display: none;\">");
				sb.append(HtmlUtils.convertHtmlLines(detailVal));
				sb.append("</div>");
			}
			return sb.toString();
		}
	}

	static class RenderGroupTable extends RenderTable {
		String groupColumn;

		RenderGroupTable(final String groupColumn) {
			this.groupColumn = groupColumn;
		}

		@Override
		String renderBody(final ComponentParameter cParameter,
				final AbstractTablePagerSchema tablePagerData, final KVMap beanAttributes) {
			final List<?> data = PagerUtils.getPagerList(cParameter);
			if (data == null) {
				return "";
			}
			final StringBuilder sb = new StringBuilder();
			final Map<String, List<Object>> convert = new LinkedHashMap<String, List<Object>>();
			for (final Object bean : data) {
				String key = Convert.toString(BeanUtils.getProperty(bean, groupColumn));
				if (!StringUtils.hasText(key)) {
					key = "Other";
				}
				List<Object> l = convert.get(key);
				if (l == null) {
					convert.put(key, l = new ArrayList<Object>());
				}
				l.add(bean);
			}
			final ITablePagerHandler gHandle = (ITablePagerHandler) cParameter.getComponentHandler();

			appendTbody(cParameter, data.size(), sb, beanAttributes);
			for (final Map.Entry<String, List<Object>> entry : convert.entrySet()) {
				final List<Object> l = entry.getValue();
				sb.append("<div class=\"group_t\">");
				sb.append("<table style=\"width: 100%;\" cellpadding=\"0\"><tr>");
				sb.append("<td class=\"toggle\"><img src=\"");
				sb.append(ComponentUtils.getCssResourceHomePath(cParameter));
				sb.append("/images/toggle.png\" /></td>");
				sb.append("<td class=\"t\">");
				final String groupValue = entry.getKey();
				final GroupWrapper gw = (gHandle instanceof IGroupTablePagerHandler) ? ((IGroupTablePagerHandler) gHandle)
						.getGroupWrapper(cParameter, groupValue) : null;
				sb.append(gw != null ? gw.getLeftTitle(l) : groupValue);
				sb.append("</td>");
				String rTitle;
				if (gw != null && StringUtils.hasText(rTitle = gw.getRightTitle(l))) {
					sb.append("<td class=\"t2\">").append(rTitle).append("</td>");
				}
				sb.append("</tr></table></div>");
				sb.append("<div class=\"group_c\">");
				for (int i = 0; i < l.size(); i++) {
					sb.append(buildRow(cParameter, beanAttributes, l.get(i), tablePagerData, i));
				}
				sb.append("</div>");
			}
			sb.append("</div>");
			return sb.toString();
		}
	}
}
