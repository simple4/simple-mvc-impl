package net.simpleframework.mvc.component.ui.pager;

import java.util.Map;

import net.simpleframework.common.ado.ColumnData;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.menu.MenuBean;
import net.simpleframework.mvc.component.ui.menu.MenuItems;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface ITablePagerHandler extends IPagerHandler {
	static final String FIRST_ITEM = "__first_item";

	static final String LAST_ITEM = "__last_item";

	/**
	 * 创建表格的元信息
	 * 
	 * @return
	 */
	AbstractTablePagerSchema createTablePagerSchema();

	/**
	 * 根据id查找行数据
	 * 
	 * @param compParameter
	 * @param id
	 * @return
	 */
	Object getRowDataById(ComponentParameter cParameter, Object id);

	/**
	 * 获取当前的排序列
	 * 
	 * @param compParameter
	 * @return
	 */
	ColumnData getSortColumn(ComponentParameter cParameter);

	/**
	 * 获取当前的过滤列
	 * 
	 * 和getSortColumn一起提供排序和过滤的列信息
	 * 
	 * @param compParameter
	 * @return
	 */
	Map<String, ColumnData> getFilterColumns(ComponentParameter cParameter);

	/**
	 * 数据导出
	 * 
	 * @param compParameter
	 * @param filetype
	 * @param columns
	 */
	void export(ComponentParameter cParameter, EExportFileType filetype, TablePagerColumnMap columns);

	/**
	 * 在编辑时，获取编辑器的HTML代码
	 * 
	 * @param compParameter
	 * @param column
	 * @param rowData
	 *           行的真实数据
	 * @return
	 */
	String getEditorHTML(ComponentParameter cParameter, TablePagerColumn column, Object rowData);

	// menu
	MenuItems getHeaderMenu(ComponentParameter cParameter, MenuBean menuBean);
}
