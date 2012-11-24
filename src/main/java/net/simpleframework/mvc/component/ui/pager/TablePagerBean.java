package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.ComponentUtils;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerBean extends PagerBean {
	/**
	 * 是否显示多选框，选择多行
	 */
	private boolean showCheckbox = true;

	/**
	 * 是否显示竖线及边框
	 */
	private boolean showVerticalLine, showBorder;

	/**
	 * 是否显示行号
	 */
	private boolean showLineNo;

	private int rowMargin = 0;

	/**
	 * 列头是否自动滚动，使其一直在可见范围
	 */
	private boolean scrollHead = true;

	private int headHeight;

	/**
	 * 是否显示快捷过滤面板，表格列下面
	 */
	private boolean showFilterBar = true;

	/**
	 * 是否显示详细信息
	 */
	private String detailField;

	private String jsRowClick, jsRowDblclick;

	private TablePagerColumnMap columns;

	public TablePagerBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public TablePagerBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public TablePagerColumnMap getColumns() {
		if (columns == null) {
			columns = new TablePagerColumnMap();
		}
		return columns;
	}

	public TablePagerBean addColumn(final TablePagerColumn column) {
		getColumns().add(column.getColumnName(), column);
		return this;
	}

	@Override
	public String getJspPath() {
		return ComponentUtils.getResourceHomePath(TablePagerBean.class) + "/jsp/tablepager.jsp";
	}

	public boolean isShowCheckbox() {
		return showCheckbox;
	}

	public TablePagerBean setShowCheckbox(final boolean showCheckbox) {
		this.showCheckbox = showCheckbox;
		return this;
	}

	public boolean isShowVerticalLine() {
		return showVerticalLine;
	}

	public TablePagerBean setShowVerticalLine(final boolean showVerticalLine) {
		this.showVerticalLine = showVerticalLine;
		return this;
	}

	public boolean isShowBorder() {
		return showBorder;
	}

	public TablePagerBean setShowBorder(final boolean showBorder) {
		this.showBorder = showBorder;
		return this;
	}

	public int getRowMargin() {
		return rowMargin;
	}

	public TablePagerBean setRowMargin(final int rowMargin) {
		this.rowMargin = rowMargin;
		return this;
	}

	public boolean isShowLineNo() {
		return showLineNo;
	}

	public TablePagerBean setShowLineNo(final boolean showLineNo) {
		this.showLineNo = showLineNo;
		return this;
	}

	public boolean isScrollHead() {
		return scrollHead;
	}

	public TablePagerBean setScrollHead(final boolean scrollHead) {
		this.scrollHead = scrollHead;
		return this;
	}

	public int getHeadHeight() {
		return headHeight;
	}

	public TablePagerBean setHeadHeight(final int headHeight) {
		this.headHeight = headHeight;
		return this;
	}

	public boolean isShowFilterBar() {
		return showFilterBar;
	}

	public TablePagerBean setShowFilterBar(final boolean showFilterBar) {
		this.showFilterBar = showFilterBar;
		return this;
	}

	public String getDetailField() {
		return detailField;
	}

	public TablePagerBean setDetailField(final String detailField) {
		this.detailField = detailField;
		return this;
	}

	public String getJsRowClick() {
		return jsRowClick;
	}

	public TablePagerBean setJsRowClick(final String jsRowClick) {
		this.jsRowClick = jsRowClick;
		return this;
	}

	public String getJsRowDblclick() {
		return jsRowDblclick;
	}

	public TablePagerBean setJsRowDblclick(final String jsRowDblclick) {
		this.jsRowDblclick = jsRowDblclick;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsRowClick", "jsRowDblclick", "jsLoadedCallback" };
	}
}