package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net.simpleframework.common.ClassUtils;
import net.simpleframework.common.Convert;
import net.simpleframework.common.NumberUtils;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.html.element.ETextAlign;
import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageRequestResponse;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerColumn extends AbstractElementBean {

	/**
	 * 表格列名称，数据的key值
	 */
	private String columnName, columnSqlName;

	/**
	 * 表格列的显示名称
	 */
	private String columnText;

	/**
	 * 表格列的数据类型
	 */
	private String propertyClass;

	private int width;

	private ETextAlign textAlign = ETextAlign.center;

	private boolean sort = true, filter = true, resize = true;

	/**
	 * 表格列是否可以导出、编辑
	 */
	private boolean export = true, edit = true;

	private boolean visible = true;

	private String tooltip, format;

	/**
	 * 表格列是否wrap超出列大小的数据内容
	 */
	private boolean nowrap = true;

	private String lblStyle;

	public TablePagerColumn(final XmlElement xmlElement) {
		super(xmlElement);
	}

	public TablePagerColumn(final String columnName, final String columnText, final int width) {
		this((XmlElement) null);
		setColumnName(columnName).setColumnText(columnText).setWidth(width);
	}

	public TablePagerColumn(final String columnName, final String columnText) {
		this(columnName, columnText, 0);
	}

	public TablePagerColumn(final String columnName) {
		this(columnName, null);
	}

	public TablePagerColumn() {
		this((String) null);
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnText() {
		return StringUtils.text(columnText, getColumnName());
	}

	public TablePagerColumn setColumnText(final String columnText) {
		this.columnText = columnText;
		return this;
	}

	public TablePagerColumn setColumnName(final String columnName) {
		this.columnName = columnName;
		return this;
	}

	public String getPropertyClass() {
		return propertyClass;
	}

	public TablePagerColumn setPropertyClass(final String propertyClass) {
		this.propertyClass = propertyClass;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public TablePagerColumn setWidth(final int width) {
		this.width = width;
		return this;
	}

	public ETextAlign getTextAlign() {
		return textAlign;
	}

	public TablePagerColumn setTextAlign(final ETextAlign textAlign) {
		this.textAlign = textAlign;
		return this;
	}

	public String getLblStyle() {
		return lblStyle;
	}

	public void setLblStyle(final String lblStyle) {
		this.lblStyle = lblStyle;
	}

	public boolean isResize() {
		return resize;
	}

	public TablePagerColumn setResize(final boolean resize) {
		this.resize = resize;
		return this;
	}

	public boolean isExport() {
		return export;
	}

	public TablePagerColumn setExport(final boolean export) {
		this.export = export;
		return this;
	}

	public boolean isEdit() {
		return edit;
	}

	public TablePagerColumn setEdit(final boolean edit) {
		this.edit = edit;
		return this;
	}

	public boolean isSort() {
		return sort;
	}

	public TablePagerColumn setSort(final boolean sort) {
		this.sort = sort;
		return this;
	}

	public boolean isFilter() {
		return filter;
	}

	public TablePagerColumn setFilter(final boolean filter) {
		this.filter = filter;
		return this;
	}

	public String getColumnSqlName() {
		return StringUtils.text(columnSqlName, getColumnName());
	}

	public TablePagerColumn setColumnSqlName(final String columnSqlName) {
		this.columnSqlName = columnSqlName;
		return this;
	}

	public boolean isVisible() {
		return visible;
	}

	public TablePagerColumn setVisible(final boolean visible) {
		this.visible = visible;
		return this;
	}

	public String getTooltip() {
		return tooltip;
	}

	public TablePagerColumn setTooltip(final String tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public String getFormat() {
		return StringUtils.text(format, defaultFormats.get(propertyClass()));
	}

	public TablePagerColumn setFormat(final String format) {
		this.format = format;
		return this;
	}

	public boolean isNowrap() {
		return nowrap;
	}

	public TablePagerColumn setNowrap(final boolean nowrap) {
		this.nowrap = nowrap;
		return this;
	}

	String toStyle(final PageRequestResponse rRequest, final boolean header) {
		final HashSet<String> set = new HashSet<String>();
		if (!header) {
			set.add("text-align:" + getTextAlign());
		}
		final int w = getWidth();
		if (w > 0) {
			set.add("width:" + TablePagerHTML.fixWidth(rRequest, w) + "px");
		}
		if (isNowrap()) {
			set.add("white-space: nowrap");
		}
		return set.size() > 0 ? StringUtils.join(set, ";") : null;
	}

	public Class<?> propertyClass() {
		final String clazz = getPropertyClass();
		if (StringUtils.hasText(clazz)) {
			try {
				return ClassUtils.forName(clazz);
			} catch (final ClassNotFoundException e) {
			}
		}
		return String.class;
	}

	public Object stringToObject(final String val) {
		final Class<?> propertyClass = propertyClass();
		final String format = getFormat();
		if (StringUtils.hasText(format)) {
			if (Date.class.isAssignableFrom(propertyClass)) {
				return Convert.toDate(val, format);
			}
		}
		return Convert.convert(val, propertyClass);
	}

	public String objectToString(final Object val) {
		final Class<?> propertyClass = propertyClass();
		final String format = getFormat();
		if (StringUtils.hasText(format)) {
			if (val instanceof Date && Date.class.isAssignableFrom(propertyClass)) {
				return Convert.toDateString((Date) val, format);
			} else if (val instanceof Number && Number.class.isAssignableFrom(propertyClass)) {
				return NumberUtils.format((Number) val, format);
			}
		}
		return Convert.toString(val);
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "title" };
	}

	public static final TablePagerColumn ACTION = new TablePagerColumn("action",
			"<a class=\"m2 down_menu_image\"></a>");

	public static final TablePagerColumn ICON = new TablePagerColumn("icon");

	public static final TablePagerColumn BLANK = new TablePagerColumn("blank", "&nbsp;");

	private static Map<Class<?>, String> defaultFormats;
	static {
		defaultFormats = new HashMap<Class<?>, String>();
		defaultFormats.put(Date.class, Convert.defaultDatePattern);

		ICON.setSort(false).setWidth(20).setTextAlign(ETextAlign.right).setColumnText("&nbsp;")
				.setFilter(false).setExport(false).setEdit(false);
		ACTION.setSort(false).setWidth(22).setFilter(false).setExport(false).setEdit(false)
				.setTooltip($m("AbstractTablePagerData.0"));
		BLANK.setFilter(false).setSort(false).setExport(false).setEdit(false);
	}
}
