package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import net.simpleframework.common.CSVWriter;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.ado.ColumnData;
import net.simpleframework.common.ado.EFilterOpe;
import net.simpleframework.common.ado.EFilterRelation;
import net.simpleframework.common.ado.EOrder;
import net.simpleframework.common.ado.FilterItem;
import net.simpleframework.common.ado.query.IDataQuery;
import net.simpleframework.common.ado.query.ListDataObjectQuery;
import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.common.html.element.ElementList;
import net.simpleframework.common.html.element.SpanElement;
import net.simpleframework.common.html.js.EJavascriptEvent;
import net.simpleframework.mvc.MVCContextFactory;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentHandleException;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.base.validation.EValidatorMethod;
import net.simpleframework.mvc.component.base.validation.Validator;
import net.simpleframework.mvc.component.base.validation.Validators;
import net.simpleframework.mvc.component.ui.menu.EMenuEvent;
import net.simpleframework.mvc.component.ui.menu.MenuBean;
import net.simpleframework.mvc.component.ui.menu.MenuItem;
import net.simpleframework.mvc.component.ui.menu.MenuItems;
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
public abstract class AbstractTablePagerHandler extends AbstractPagerHandler implements
		ITablePagerHandler {

	@Override
	public AbstractTablePagerSchema createTablePagerSchema() {
		return new DefaultTablePagerSchema();
	}

	public class DefaultTablePagerSchema extends AbstractTablePagerSchema {

		@Override
		public Map<String, Object> getRowAttributes(final ComponentParameter cParameter,
				final Object dataObject) {
			final Map<String, Object> attributes = super.getRowAttributes(cParameter, dataObject);
			final Map<String, Object> attributes2 = getTableRowAttributes(cParameter, dataObject);
			if (attributes2 != null) {
				attributes.putAll(attributes2);
			}
			return attributes;
		}
	}

	@Override
	public Object getRowDataById(final ComponentParameter cParameter, final Object id) {
		throw ComponentHandleException.of($m("AbstractTablePagerHandle.1"));
	}

	protected Map<String, Object> getTableRowAttributes(final ComponentParameter cParameter,
			final Object dataObject) {
		return null;
	}

	@Override
	public MenuItems getHeaderMenu(final ComponentParameter cParameter, final MenuBean menuBean) {
		return null;
	}

	@Override
	public Map<String, Object> getFormParameters(final ComponentParameter cParameter) {
		final Map<String, Object> parameters = super.getFormParameters(cParameter);

		for (final String k : new String[] { TablePagerUtils.PARAM_SORT_COL,
				TablePagerUtils.PARAM_SORT, "tbl_tbody_height", "tbl_thead_margin_right" }) {
			parameters.put(k, cParameter.getParameter(k));
		}

		final Map<String, ColumnData> dbColumns = getFilterColumns(cParameter);
		if (dbColumns != null) {
			final StringBuilder sb = new StringBuilder();
			int i = 0;
			for (final ColumnData dbColumn : dbColumns.values()) {
				final String ovalue = (String) dbColumn.getAttr("ovalue");
				if (StringUtils.hasText(ovalue)) {
					final String col = dbColumn.getColumnName();
					if (i++ > 0) {
						sb.append(";");
					}
					sb.append(col);
					parameters.put(TablePagerHTML.filterColumnKey(col), ovalue);
				}
			}
			if (sb.length() > 0) {
				parameters.put(TablePagerUtils.PARAM_FILTER_COL, sb.toString());
			}
		}
		return parameters;
	}

	// implements IPagerHandle

	protected ColumnData createColumnData(final TablePagerColumn oCol) {
		return new ColumnData(oCol.getColumnName());
	}

	@Override
	public ColumnData getSortColumn(final ComponentParameter cParameter) {
		final String col = cParameter.getParameter(TablePagerUtils.PARAM_SORT_COL);
		final TablePagerColumn oCol;
		if (StringUtils.hasText(col)
				&& (oCol = TablePagerUtils.getTablePagerData(cParameter)
						.getTablePagerColumns(cParameter).get(col)) != null) {
			final String sort = cParameter.getParameter(TablePagerUtils.PARAM_SORT);
			if ("up".equals(sort) || "down".equals(sort)) {
				final ColumnData dbColumn = createColumnData(oCol);
				dbColumn.setOrder("up".equals(sort) ? EOrder.desc : EOrder.asc);
				return dbColumn;
			}
		}
		return null;
	}

	@Override
	public Map<String, ColumnData> getFilterColumns(final ComponentParameter cParameter) {
		@SuppressWarnings("unchecked")
		Map<String, ColumnData> dbColumns = (Map<String, ColumnData>) cParameter
				.getRequestAttr("filter_columns");
		if (dbColumns != null) {
			return dbColumns;
		}

		final String col = cParameter.getParameter(TablePagerUtils.PARAM_FILTER_COL);
		final String ccol = cParameter.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL);
		if (!StringUtils.hasText(ccol) && !StringUtils.hasText(col)) {
			return null;
		}

		final HashSet<String> sets = new LinkedHashSet<String>(Arrays.asList(StringUtils
				.split(StringUtils.blank(col))));
		String filter = null;
		if (StringUtils.hasText(ccol)) {
			filter = cParameter.getParameter(TablePagerUtils.PARAM_FILTER);
			if (TablePagerUtils.PARAM_FILTER_NONE.equals(filter)) {
				sets.remove(ccol);
			} else {
				filter = StringUtils.text(filter,
						cParameter.getParameter(TablePagerHTML.filterColumnKey(ccol)));
				if (StringUtils.hasText(filter)) {
					sets.add(ccol);
				} else {
					sets.remove(ccol);
				}
			}
		}
		if (sets.size() == 0) {
			return null;
		}

		final TablePagerColumnMap tableColumns = TablePagerUtils.getTablePagerData(cParameter)
				.getTablePagerColumns(cParameter);
		dbColumns = new LinkedHashMap<String, ColumnData>();
		for (final String str : sets) {
			final TablePagerColumn oCol = tableColumns.get(str);
			if (oCol == null) {
				continue;
			}
			final String vStr = str.equals(ccol) ? filter : cParameter.getParameter(TablePagerHTML
					.filterColumnKey(str));
			final String[] vals = StringUtils.split(vStr);
			if (vals == null || vals.length < 2) {
				continue;
			}
			final ColumnData dbColumn = createColumnData(oCol);
			dbColumn.setPropertyClass(oCol.propertyClass());
			dbColumns.put(str, dbColumn);
			dbColumn.setAttr("ovalue", vStr);
			String val = TablePagerHTML.filterDecode(vals[1]);
			FilterItem item = new FilterItem(EFilterRelation.get(vals[0]), oCol.stringToObject(val));
			item.setOvalue(val);
			dbColumn.getFilterItems().add(item);
			if (vals.length == 5) {
				item.setOpe(EFilterOpe.get(vals[2]));
				val = TablePagerHTML.filterDecode(vals[4]);
				item = new FilterItem(EFilterRelation.get(vals[3]), oCol.stringToObject(val));
				item.setOvalue(val);
				dbColumn.getFilterItems().add(item);
			}
		}
		cParameter.setRequestAttr("filter_columns", dbColumns);
		return dbColumns;
	}

	protected String filterItemExpr(final FilterItem item, final Collection<Object> params) {
		final StringBuilder sb = new StringBuilder();
		final EFilterRelation relation = item.getRelation();
		sb.append(" ").append(relation);
		if (relation == EFilterRelation.like) {
			sb.append(" '%").append(item.getValue()).append("%'");
		} else {
			sb.append(" ?");
			params.add(item.getValue());
		}
		final EFilterOpe ope = item.getOpe();
		if (ope != null) {
			sb.append(" ").append(ope).append(" ");
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void doFilterList(final ComponentParameter cParameter,
			final ListDataObjectQuery<?> dataQuery) {
		final Map<String, ColumnData> filterColumns = getFilterColumns(cParameter);
		final String bKey = "backup_filterlist";
		List oData = (List) dataQuery.getAttr(bKey);
		if (filterColumns != null && filterColumns.size() > 0) {
			if (oData == null) {
				dataQuery.setAttr(bKey, oData = new ArrayList(dataQuery.getList()));
			}
			final ArrayList result = new ArrayList(oData);
			for (final ColumnData oCol : filterColumns.values()) {
				final Iterator<FilterItem> it = oCol.getFilterItems().iterator();
				final FilterItem item1 = it.hasNext() ? it.next() : null;
				if (item1 == null) {
					continue;
				}
				final String propertyName = oCol.getColumnName();
				final Class<?> propertyType = oCol.getPropertyClass();
				final FilterItem item2 = it.hasNext() ? it.next() : null;
				ArrayList result2 = new ArrayList(result);
				result.clear();
				for (final Object o : result2) {
					final Object v = BeanUtils.getProperty(o, propertyName);
					boolean delete = item1.isDelete(v, propertyType);
					if (item1.getOpe() == EFilterOpe.or && item2 != null) {
						delete = item2.isDelete(v, propertyType);
					}
					if (!delete) {
						result.add(o);
					}
				}
				if (item1.getOpe() == EFilterOpe.and && item2 != null) {
					result2 = new ArrayList(result);
					result.clear();
					for (final Object o : result2) {
						final Object v = BeanUtils.getProperty(o, propertyName);
						if (!item2.isDelete(v, propertyType)) {
							result.add(o);
						}
					}
				}
			}
			final List data = dataQuery.getList();
			if (data.size() > result.size()) {
				data.clear();
				data.addAll(result);
			}
		} else if (oData != null) {
			final List data = dataQuery.getList();
			data.clear();
			data.addAll(oData);
			dataQuery.removeAttr(bKey);
		}
	}

	@Override
	protected void doCount(final ComponentParameter cParameter, final IDataQuery<?> dataQuery) {
		if (dataQuery instanceof ListDataObjectQuery) {
			doFilterList(cParameter, (ListDataObjectQuery<?>) dataQuery);
		}
		super.doCount(cParameter, dataQuery);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected int doSort(final ColumnData dbColumn, final Object o1, final Object o2) {
		try {
			final String col = dbColumn.getColumnName();
			final Comparable c1 = (Comparable) BeanUtils.getProperty(o1, col);
			final Comparable c2 = (Comparable) BeanUtils.getProperty(o2, col);
			if (c1 == null && c2 == null) {
				return 0;
			} else if (c1 == null) {
				return dbColumn.getOrder() == EOrder.desc ? 1 : -1;
			} else if (c2 == null) {
				return dbColumn.getOrder() == EOrder.desc ? -1 : 1;
			} else {
				return dbColumn.getOrder() == EOrder.desc ? c1.compareTo(c2) : c2.compareTo(c1);
			}
		} catch (final Exception e) {
			log.warn(e);
		}
		return 0;
	}

	@Override
	protected List<?> getData(final ComponentParameter cParameter, final int start) {
		final IDataQuery<?> dataQuery = (IDataQuery<?>) cParameter.getRequestAttr(DATA_QUERY);
		if (dataQuery instanceof ListDataObjectQuery) {
			final ColumnData dbColumn = getSortColumn(cParameter);
			if (dbColumn != null) {
				Collections.sort(((ListDataObjectQuery<?>) dataQuery).getList(),
						new Comparator<Object>() {

							@Override
							public int compare(final Object o1, final Object o2) {
								return doSort(dbColumn, o1, o2);
							}
						});
			}
		}
		return super.getData(cParameter, start);
	}

	/**
	 * 覆盖此函数在render时，用隐藏域填入第一个和最后一个数据的id
	 * 
	 * @return
	 */
	protected boolean addFirstAndLastData() {
		return false;
	}

	@Override
	public void process(final ComponentParameter cParameter, final int start) {
		super.process(cParameter, start);
		IDataQuery<?> dataQuery;
		if (addFirstAndLastData()
				&& (dataQuery = (IDataQuery<?>) cParameter.getRequestAttr(DATA_QUERY)) != null) {
			dataQuery.move(-1);
			cParameter.setRequestAttr(FIRST_ITEM, dataQuery.next());
			dataQuery.move(dataQuery.getCount() - 2);
			cParameter.setRequestAttr(LAST_ITEM, dataQuery.next());
		}
	}

	@Override
	public void export(final ComponentParameter cParameter, final EExportFileType filetype,
			final TablePagerColumnMap columns) {
		final IDataQuery<?> dQuery = createDataObjectQuery(cParameter);
		dQuery.setFetchSize(0);
		final AbstractTablePagerSchema tablePagerData = TablePagerUtils.getTablePagerData(cParameter);
		try {
			if (filetype == EExportFileType.csv) {
				doCSVExport(cParameter, dQuery, tablePagerData, columns);
			} else if (filetype == EExportFileType.excel) {
				doExcelExport(cParameter, dQuery, tablePagerData, columns);
			}
		} catch (final IOException e) {
			throw ComponentHandleException.of(e);
		}
	}

	protected void doCSVExport(final ComponentParameter cParameter, final IDataQuery<?> dQuery,
			final AbstractTablePagerSchema tablePagerData, final TablePagerColumnMap columns)
			throws IOException {
		CSVWriter csvWriter = null;
		try {
			cParameter.setSessionAttr(TablePagerExportProgressBar.MAX_PROGRESS, dQuery.getCount());
			csvWriter = new CSVWriter(new OutputStreamWriter(
					cParameter.getBinaryOutputStream("export.csv"), MVCContextFactory.config()
							.getCharset()));
			final ArrayList<String> al = new ArrayList<String>();
			for (final TablePagerColumn column : columns.values()) {
				al.add(column.getColumnText());
			}
			csvWriter.writeNext(al.toArray(new String[al.size()]));
			Object object;
			int i = 1;
			while ((object = dQuery.next()) != null) {
				al.clear();
				for (final Object o : tablePagerData.getExportRowData(cParameter, columns, object)
						.values()) {
					al.add(StringUtils.blank(o));
				}
				csvWriter.writeNext(al.toArray(new String[al.size()]));
				cParameter.setSessionAttr(TablePagerExportProgressBar.STEP, i++);
			}
		} finally {
			try {
				if (csvWriter != null) {
					csvWriter.close();
				}
			} catch (final IOException e) {
			}
		}
	}

	protected void doExcelExport(final ComponentParameter cParameter, final IDataQuery<?> dQuery,
			final AbstractTablePagerSchema tablePagerData, final TablePagerColumnMap columns)
			throws IOException {
	}

	@Override
	public String getEditorHTML(final ComponentParameter cParameter, final TablePagerColumn column,
			final Object rowData) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<input type='text' style='width: 98%'");
		if (rowData != null) {
			final Object val = BeanUtils.getProperty(rowData, column.getColumnName());
			if (val != null) {
				sb.append(" value='").append(val).append("'");
			}
		}
		sb.append(" />");
		return sb.toString();
	}

	/**
	 * 输入过滤条件的验证信息
	 * 
	 * 子类覆盖
	 * 
	 * @param compParameter
	 * @param oCol
	 * @return
	 */
	protected Validators getFilterColumnValidators(final ComponentParameter cParameter,
			final TablePagerColumn oCol) {
		final Validators al = Validators.of();
		if (Number.class.isAssignableFrom(oCol.propertyClass())) {
			final Validator validator = new Validator(null);
			validator.setMethod(EValidatorMethod.number);
			validator.setSelector("#tp_filter_v1, #tp_filter_v2");
			al.add(validator);
		}
		return al;
	}

	/**
	 * 定义一些表格内部用到的组件
	 * 
	 * @param compParameter
	 * @param componentBeans
	 */
	protected void createComponentBeans(final ComponentParameter cParameter) {
		final AbstractTablePagerHandler tptbl = (AbstractTablePagerHandler) cParameter
				.getComponentHandler();
		final String hashId = cParameter.hashId();

		// 列头的 tip
		final TooltipBean tooltip = cParameter.addComponentBean("tablePagerHeadTooltip",
				TooltipBean.class);
		tooltip.getTips().add(
				new TipBean(tooltip)
						.setStem(ETipPosition.bottomMiddle)
						.setHideOthers(true)
						.setHideAfter(0.5)
						.setOffsetY(5)
						.setHook(
								new Hook().setTarget(ETipPosition.topMiddle).setTip(
										ETipPosition.bottomMiddle))
						.setHideOn(
								new HideOn().setTipElement(ETipElement.target).setEvent(
										EJavascriptEvent.mouseleave)).setWidth(240));

		// 添加菜单组件
		MenuBean menuBean = (MenuBean) cParameter
				.addComponentBean("ml_" + hashId + "_Menu", MenuBean.class)
				.setMenuEvent(EMenuEvent.click).setHandleClass(TablePagerMenu.class);
		List<MenuItem> items = tptbl.getContextMenu(cParameter, menuBean, null);
		if (items == null) {
			cParameter.removeComponentBean(menuBean);
		}
		menuBean = (MenuBean) cParameter.addComponentBean("ml_" + hashId + "_Menu2", MenuBean.class)
				.setMenuEvent(EMenuEvent.click).setMinWidth("140")
				.setHandleClass(TablePagerMenu2.class);
		items = tptbl.getHeaderMenu(cParameter, menuBean);
		if (items == null) {
			cParameter.removeComponentBean(menuBean);
		}
	}

	/**
	 * 表格的导航连接
	 * 
	 * @return
	 */
	protected ElementList navigationTitle(final ComponentParameter cParameter) {
		return null;
	}

	@Override
	public Object getBeanProperty(final ComponentParameter cParameter, final String beanProperty) {
		if ("title".equals(beanProperty)) {
			final ElementList list = navigationTitle(cParameter);
			int size;
			if (list != null && (size = list.size()) > 0) {
				final StringBuilder sb = new StringBuilder();
				sb.append("<div class='nav_arrow'>");
				for (int i = 0; i < size; i++) {
					if (i > 0) {
						sb.append(SpanElement.NAV);
					}
					sb.append(list.get(i));
				}
				sb.append("</div>");
				return sb.toString();
			}
		} else if ("exportAction".equals(beanProperty)) {
			final AbstractComponentBean bean = cParameter.componentBean;
			if (bean instanceof TablePagerBean
					&& !"false".equalsIgnoreCase(((TablePagerBean) bean).getExportAction())) {
				final StringBuilder sb = new StringBuilder();
				sb.append("$Actions['").append(getBeanProperty(cParameter, "name"));
				sb.append("'].exportFile();");
				return sb.toString();
			}
		}
		return super.getBeanProperty(cParameter, beanProperty);
	}
}
