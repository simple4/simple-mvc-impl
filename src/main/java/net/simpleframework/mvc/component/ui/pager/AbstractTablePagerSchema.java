package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.mvc.component.ui.pager.TablePagerColumn.ACTION;
import static net.simpleframework.mvc.component.ui.pager.TablePagerColumn.BLANK;
import static net.simpleframework.mvc.component.ui.pager.TablePagerColumn.ICON;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import net.simpleframework.common.ClassUtils;
import net.simpleframework.common.I18n;
import net.simpleframework.common.bean.BeanUtils;
import net.simpleframework.common.bean.IIdBeanAware;
import net.simpleframework.common.coll.KVMap;
import net.simpleframework.common.xml.XmlDocument;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.common.xml.XmlElement.Attri;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractTablePagerSchema {
	public final static String ACTIONc = "<a class=\"m down_menu_image\"></a>";

	private static final TablePagerColumnMap defaultColumns = new TablePagerColumnMap();
	static {
		defaultColumns.add(ICON.getColumnName(), ICON);
		defaultColumns.add(ACTION.getColumnName(), ACTION);
		defaultColumns.add(BLANK.getColumnName(), BLANK);
	}

	/**
	 * 获取列头信息
	 * 
	 * @param compParameter
	 * @return
	 */
	public TablePagerColumnMap getTablePagerColumns(final ComponentParameter cParameter) {
		return getTablePagerColumnsInternal(cParameter);
	}

	private TablePagerColumnMap getTablePagerColumnsInternal(final ComponentParameter cParameter) {
		final TablePagerBean tablePager = (TablePagerBean) cParameter.componentBean;
		if (tablePagerColumns.size() == 0) {
			tablePagerColumns.putAll(tablePager.getColumns());
		}
		if (tablePagerColumns.size() == 0) {
			tablePagerColumns.putAll(getTablePagerColumnsFromXML(cParameter));
		}
		return tablePagerColumns;
	}

	private final TablePagerColumnMap tablePagerColumns = new TablePagerColumnMap();

	private TablePagerColumnMap getTablePagerColumnsFromXML(final ComponentParameter cParameter) {
		synchronized (ACTIONc) {
			final InputStream inputStream = ClassUtils.getResourceRecursively(getClass(),
					"columns.xml");
			if (inputStream != null) {
				final XmlDocument doc = new XmlDocument(inputStream);
				final Iterator<?> it = doc.getRoot().elementIterator();
				while (it.hasNext()) {
					final XmlElement xmlElement = (XmlElement) it.next();
					final String columnName = xmlElement.attributeValue("columnName");
					TablePagerColumn tablePagerColumn = defaultColumns.get(columnName);
					if (tablePagerColumn != null) {
						tablePagerColumns.add(columnName, tablePagerColumn);
					} else {
						tablePagerColumn = new TablePagerColumn();
						tablePagerColumns.add(columnName, tablePagerColumn);
						final Iterator<?> attributes = xmlElement.attributeIterator();
						while (attributes.hasNext()) {
							final Attri attri = (Attri) attributes.next();
							BeanUtils.setProperty(tablePagerColumn, attri.getName(),
									I18n.replaceI18n(attri.getValue()));
						}
					}
				}
			}
			return tablePagerColumns;
		}
	}

	/**
	 * 获取导出的列信息
	 * 
	 * @param compParameter
	 * @return
	 */
	public TablePagerColumnMap getTablePagerExportColumns(final ComponentParameter cParameter) {
		final TablePagerColumnMap columns = new TablePagerColumnMap();
		for (final Map.Entry<String, TablePagerColumn> entry : getTablePagerColumns(cParameter)
				.entrySet()) {
			final TablePagerColumn oCol = entry.getValue();
			if (!oCol.isExport()) {
				continue;
			}
			columns.add(entry.getKey(), oCol);
		}
		return columns;
	}

	/**
	 * 将真实数据转为显示数据
	 * 
	 * @param compParameter
	 * @param dataObject
	 * @return
	 */
	public Map<String, Object> getRowData(final ComponentParameter cParameter,
			final Object dataObject) {
		final TablePagerColumnMap pagerColumns = getTablePagerColumns(cParameter);
		final KVMap kv = new KVMap();
		for (final Map.Entry<String, TablePagerColumn> entry : pagerColumns.entrySet()) {
			final String key = entry.getKey();
			Object val = null;
			try {
				val = BeanUtils.getProperty(dataObject, key);
			} catch (final Exception e) {
			}
			if (val != null) {
				kv.add(key, val);
			}
		}
		return kv;
	}

	public Map<String, Object> getExportRowData(final ComponentParameter cParameter,
			final Object dataObject) {
		return getExportRowData(cParameter, null, dataObject);
	}

	/**
	 * 将真实数据转为导出数据
	 * 
	 * @param compParameter
	 * @param columns
	 * @param dataObject
	 * @return
	 */
	public Map<String, Object> getExportRowData(final ComponentParameter cParameter,
			TablePagerColumnMap columns, final Object dataObject) {
		if (columns == null) {
			columns = getTablePagerExportColumns(cParameter);
		}
		final KVMap rowData = new KVMap();
		Object value;
		for (final Map.Entry<String, TablePagerColumn> entry : columns.entrySet()) {
			final String key = entry.getKey();
			try {
				value = BeanUtils.getProperty(dataObject, key);
			} catch (final Exception e) {
				value = "<ERROR>";
			}
			rowData.add(key, value);
		}
		return rowData;
	}

	public static final String ROW_ID = "rowId";

	/**
	 * 获取数据的id
	 * 
	 * @param dataObject
	 * @return
	 */
	public Object getId(final Object dataObject) {
		if (dataObject instanceof IIdBeanAware) {
			return ((IIdBeanAware) dataObject).getId();
		} else if (dataObject instanceof Map<?, ?>) {
			return ((Map<?, ?>) dataObject).get("ID");
		} else {
			try {
				return BeanUtils.getProperty(dataObject, "id");
			} catch (final Exception e) {
			}
		}
		return null;
	}

	/**
	 * 为表格的行添加额外的HTML属性
	 * 
	 * @param compParameter
	 * @param dataObject
	 * @return
	 */
	public Map<String, Object> getRowAttributes(final ComponentParameter cParameter,
			final Object dataObject) {
		final KVMap attributes = new KVMap();
		final Object id = getId(dataObject);
		if (id != null) {
			attributes.add(ROW_ID, id);
		}
		return attributes;
	}

	protected String wrapNum(final long num) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<span class=\"nnum\">").append(num).append("</span>");
		return sb.toString();
	}
}
