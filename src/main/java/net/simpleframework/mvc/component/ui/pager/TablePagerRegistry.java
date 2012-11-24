package net.simpleframework.mvc.component.ui.pager;

import static net.simpleframework.common.I18n.$m;

import java.util.Iterator;

import net.simpleframework.common.script.IScriptEval;
import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.AbstractComponentBean;
import net.simpleframework.mvc.component.ComponentBean;
import net.simpleframework.mvc.component.ComponentName;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ComponentResourceProvider;
import net.simpleframework.mvc.component.ComponentUtils;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.ui.window.WindowBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
@ComponentName(TablePagerRegistry.tablePager)
@ComponentBean(TablePagerBean.class)
@ComponentResourceProvider(TablePagerResourceProvider.class)
public class TablePagerRegistry extends PagerRegistry {
	public static final String tablePager = "tablePager";

	@Override
	public AbstractComponentBean createComponentBean(final PageParameter pParameter,
			final Object data) {
		final TablePagerBean tablePager = (TablePagerBean) super
				.createComponentBean(pParameter, data);

		if (data instanceof XmlElement) {
			final XmlElement columns = ((XmlElement) data).element("columns");
			if (columns != null) {
				final IScriptEval scriptEval = pParameter.getScriptEval();
				final Iterator<?> it = columns.elementIterator("column");
				final TablePagerColumnMap columnsMap = tablePager.getColumns();
				while (it.hasNext()) {
					final XmlElement column = (XmlElement) it.next();
					final TablePagerColumn tpColumn = new TablePagerColumn(column);
					tpColumn.parseElement(scriptEval);
					columnsMap.add(tpColumn.getColumnName(), tpColumn);
				}
			}
		}

		// 添加表格内部用到的组件
		final ComponentParameter cParameter = ComponentParameter.get(pParameter, tablePager);
		final String hPath = ComponentUtils.getResourceHomePath(TablePagerBean.class) + "/jsp/";

		// 过滤窗口
		pParameter.addComponentBean("tablePagerColumnFilterPage", AjaxRequestBean.class)
				.setUrlForward(hPath + "tablepager_filter.jsp");
		pParameter.addComponentBean("tablePagerColumnFilterWindow", WindowBean.class)
				.setContentRef("tablePagerColumnFilterPage").setTitle($m("tablepager.0"))
				.setHeight(240).setWidth(420);

		// 删除过滤
		pParameter.addComponentBean("ajaxTablePagerFilterDelete", AjaxRequestBean.class)
				.setHandleMethod("doFilterDelete").setHandleClass(TablePagerAction.class);

		// 过滤
		if ((Boolean) cParameter.getBeanProperty("showFilterBar")) {
			pParameter.addComponentBean("ajaxTablePagerColumnFilter", AjaxRequestBean.class)
					.setHandleMethod("doFilter2").setHandleClass(TablePagerAction.class);
		}

		// 导出
		pParameter.addComponentBean("ajaxTablePagerExportPage", AjaxRequestBean.class).setUrlForward(
				hPath + "tablepager_export.jsp");
		pParameter.addComponentBean("tablePagerExportWin", WindowBean.class)
				.setContentRef("ajaxTablePagerExportPage").setTitle($m("tablepager_export.0"));

		// 编辑行
		pParameter.addComponentBean("ajaxTablePagerRowEdit", AjaxRequestBean.class)
				.setHandleMethod("doRowEdit").setHandleClass(TablePagerAction.class);

		return tablePager;
	}
}
