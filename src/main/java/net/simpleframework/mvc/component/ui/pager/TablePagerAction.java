package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.AbstractUrlForward;
import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.JavascriptForward;
import net.simpleframework.mvc.JsonForward;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.base.ajaxrequest.DefaultAjaxRequestHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerAction extends DefaultAjaxRequestHandler {
	public IForward doExport(final ComponentParameter cParameter) {
		final JavascriptForward js = new JavascriptForward();
		js.append("$Actions.loc('");
		js.append(ComponentUtils.getResourceHomePath(PagerBean.class)).append(
				"/jsp/tablepager_export_dl.jsp");
		js.append(";jsessionid=").append(cParameter.getSession().getId()).append("?");
		js.append(AbstractUrlForward.putRequestData(cParameter, "p", true));
		js.append("&filetype=").append(cParameter.getParameter("filetype"));
		js.append("&v=").append(cParameter.getParameter("v")).append("');");
		return js;
	}

	public IForward doFilter(final ComponentParameter cParameter) {
		final JavascriptForward js = new JavascriptForward(
				"$Actions['tablePagerColumnFilterWindow'].close();");
		final ComponentParameter nComponentParameter = PagerUtils.get(cParameter);
		js.append("$Actions['").append(nComponentParameter.getBeanProperty("name")).append("']('")
				.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=");
		js.append(cParameter.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL)).append("&")
				.append(TablePagerUtils.PARAM_FILTER).append("=");
		js.append(cParameter.getParameter("tp_filter_r1"));
		js.append(";").append(TablePagerHTML.filterEncode(cParameter.getParameter("tp_filter_v1")));
		final String op = cParameter.getParameter("tp_filter_op");
		if ("and".equals(op) || "or".equals(op)) {
			js.append(";").append(op).append(";");
			js.append(cParameter.getParameter("tp_filter_r2"));
			js.append(";")
					.append(TablePagerHTML.filterEncode(cParameter.getParameter("tp_filter_v2")));
		}
		js.append("');");
		return js;
	}

	public IForward doFilter2(final ComponentParameter cParameter) {
		final StringBuilder sb = new StringBuilder();
		sb.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=");
		sb.append(cParameter.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL)).append("&")
				.append(TablePagerUtils.PARAM_FILTER).append("=");
		sb.append("like;").append(TablePagerHTML.filterEncode(cParameter.getParameter("v")));
		return new JsonForward("filter", sb.toString());
	}

	public IForward doFilterDelete(final ComponentParameter cParameter) {
		final JavascriptForward jf = new JavascriptForward();
		final ComponentParameter nComponentParameter = PagerUtils.get(cParameter);
		jf.append("$Actions['").append(nComponentParameter.getBeanProperty("name")).append("']('")
				.append(TablePagerUtils.PARAM_FILTER_CUR_COL).append("=")
				.append(cParameter.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL)).append("&")
				.append(TablePagerUtils.PARAM_FILTER).append("=")
				.append(TablePagerUtils.PARAM_FILTER_NONE).append("');");
		return jf;
	}

	public IForward doRowEdit(final ComponentParameter cParameter) {
		final String rowId = cParameter.getParameter(AbstractTablePagerSchema.ROW_ID);
		if (!StringUtils.hasText(rowId)) {
			final JavascriptForward js = new JavascriptForward();
			return js.append("alert('").append($m("TablePagerAction.0")).append("');");
		} else {
			final ComponentParameter nComponentParameter = PagerUtils.get(cParameter);
			final ITablePagerHandler hdl = (ITablePagerHandler) nComponentParameter
					.getComponentHandler();
			final Object rowData = hdl.getRowDataById(nComponentParameter, rowId);
			final JsonForward json = new JsonForward();
			json.put("$$edit", rowData != null);
			final TablePagerColumnMap columns = TablePagerUtils.getTablePagerData(nComponentParameter)
					.getTablePagerColumns(nComponentParameter);
			for (final TablePagerColumn column : columns.values()) {
				if (!column.isEdit()) {
					continue;
				}
				final String html = hdl.getEditorHTML(nComponentParameter, column, rowData);
				if (StringUtils.hasText(html)) {
					json.put(column.getColumnName(), html);
				}
			}
			return json;
		}
	}
}
