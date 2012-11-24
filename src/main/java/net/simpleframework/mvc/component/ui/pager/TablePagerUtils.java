package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.ado.EFilterRelation.equal;
import static net.simpleframework.common.ado.EFilterRelation.gt;
import static net.simpleframework.common.ado.EFilterRelation.gt_equal;
import static net.simpleframework.common.ado.EFilterRelation.like;
import static net.simpleframework.common.ado.EFilterRelation.lt;
import static net.simpleframework.common.ado.EFilterRelation.lt_equal;
import static net.simpleframework.common.ado.EFilterRelation.not_equal;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class TablePagerUtils {

	/**
	 * 当前排序列名
	 */
	public static final String PARAM_SORT_COL = "sort_col";

	/**
	 * 排序状态，值为up、down或空
	 */
	public static final String PARAM_SORT = "sort";

	public static final String PARAM_FILTER_COL = "filter_col";

	public static final String PARAM_FILTER_CUR_COL = "filter_cur_col";

	public static final String PARAM_FILTER = "filter";

	public static final String PARAM_FILTER_NONE = "none";

	/**
	 * 是否上移数据，参考tablepager.js中move函数
	 */
	public static final String PARAM_MOVE_UP = "up";

	public static final String PARAM_MOVE_ROWID = "rowId", PARAM_MOVE_ROWID2 = "rowId2";

	public static AbstractTablePagerSchema getTablePagerData(final ComponentParameter cParameter) {
		AbstractTablePagerSchema tablePagerData = (AbstractTablePagerSchema) cParameter
				.getRequestAttr("table_pager_data");
		if (tablePagerData == null) {
			final ITablePagerHandler pHandle = (ITablePagerHandler) cParameter.getComponentHandler();
			cParameter.setRequestAttr("table_pager_data",
					tablePagerData = pHandle.createTablePagerSchema());
		}
		return tablePagerData;
	}

	public static TablePagerColumn getSelectedColumn(final ComponentParameter nComponentParameter) {
		return getTablePagerData(nComponentParameter).getTablePagerColumns(nComponentParameter).get(
				nComponentParameter.getParameter(PARAM_FILTER_CUR_COL));
	}

	public static void doExport(final HttpServletRequest request, final HttpServletResponse response) {
		final ComponentParameter nComponentParameter = PagerUtils.get(request, response);
		final ITablePagerHandler tHandle = (ITablePagerHandler) nComponentParameter
				.getComponentHandler();
		final String[] arr = StringUtils.split(request.getParameter("v"), ";");
		TablePagerColumnMap columns = null;
		if (arr != null && arr.length > 0) {
			columns = new TablePagerColumnMap();
			final TablePagerColumnMap all = getTablePagerData(nComponentParameter)
					.getTablePagerColumns(nComponentParameter);
			for (final String v : arr) {
				final TablePagerColumn oCol = all.get(v);
				if (oCol != null) {
					columns.add(v, oCol);
				}
			}
		}
		tHandle.export(nComponentParameter,
				Convert.toEnum(EExportFileType.class, request.getParameter("filetype")), columns);
	}

	public static String toOptionHTML(final TablePagerColumn col) {
		final StringBuilder sb = new StringBuilder();
		final Class<?> clazz = col.propertyClass();
		if (String.class.isAssignableFrom(clazz)) {
			sb.append("<option value=\"").append(like).append("\">#(TablePagerUtils.6)</option>");
		}
		sb.append("<option value=\"").append(equal).append("\">#(TablePagerUtils.0)</option>");
		sb.append("<option value=\"").append(not_equal).append("\">#(TablePagerUtils.1)</option>");
		if (Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz)) {
			sb.append("<option value=\"").append(gt).append("\">#(TablePagerUtils.2)</option>");
			sb.append("<option value=\"").append(gt_equal).append("\">#(TablePagerUtils.3)</option>");
			sb.append("<option value=\"").append(lt).append("\">#(TablePagerUtils.4)</option>");
			sb.append("<option value=\"").append(lt_equal).append("\">#(TablePagerUtils.5)</option>");
		}
		return sb.toString();
	}
}
