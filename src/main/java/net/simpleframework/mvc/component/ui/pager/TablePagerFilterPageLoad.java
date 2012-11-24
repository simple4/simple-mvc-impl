package net.simpleframework.mvc.component.ui.pager;

import java.util.Iterator;
import java.util.Map;

import net.simpleframework.common.StringUtils;
import net.simpleframework.common.ado.ColumnData;
import net.simpleframework.common.ado.EFilterOpe;
import net.simpleframework.common.ado.FilterItem;
import net.simpleframework.mvc.DefaultPageHandler;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.base.ajaxrequest.AjaxRequestBean;
import net.simpleframework.mvc.component.base.validation.EValidatorMethod;
import net.simpleframework.mvc.component.base.validation.EWarnType;
import net.simpleframework.mvc.component.base.validation.ValidationBean;
import net.simpleframework.mvc.component.base.validation.Validator;
import net.simpleframework.mvc.component.ui.calendar.CalendarBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerFilterPageLoad extends DefaultPageHandler {

	@Override
	public void pageLoad(final PageParameter pParameter, final Map<String, Object> dataBinding,
			final PageSelector selector) {
		final String col = pParameter.getParameter(TablePagerUtils.PARAM_FILTER_CUR_COL);
		if (!StringUtils.hasText(col)) {
			return;
		}
		final ComponentParameter nComponentParameter = PagerUtils.get(pParameter);
		boolean disabled = true;
		ColumnData dbColumn;
		final Map<String, ColumnData> dbColumns = ((ITablePagerHandler) nComponentParameter
				.getComponentHandler()).getFilterColumns(nComponentParameter);
		if (dbColumns != null && (dbColumn = dbColumns.get(col)) != null) {
			final Iterator<FilterItem> it = dbColumn.getFilterItems().iterator();
			if (it.hasNext()) {
				FilterItem item = it.next();
				dataBinding.put("tp_filter_r1", item.getRelation().toString());
				dataBinding.put("tp_filter_v1", item.getOvalue());
				final EFilterOpe ope = item.getOpe();
				if (ope == EFilterOpe.and) {
					dataBinding.put("tp_filter_op1", true);
					disabled = false;
				} else if (ope == EFilterOpe.or) {
					dataBinding.put("tp_filter_op2", true);
					disabled = false;
				}
				if (it.hasNext()) {
					item = it.next();
					dataBinding.put("tp_filter_r2", item.getRelation().toString());
					dataBinding.put("tp_filter_v2", item.getOvalue());
				}
			}
		}
		if (disabled) {
			selector.disabledSelector = "#tp_filter_r2, #tp_filter_v2";
		}
	}

	@Override
	public void beforeComponentRender(final PageParameter pParameter) {
		super.beforeComponentRender(pParameter);

		pParameter.addComponentBean("ajaxTablePagerFilterSave", AjaxRequestBean.class)
				.setHandleMethod("doFilter").setHandleClass(TablePagerAction.class)
				.setSelector(".tablepager_filter");

		// 加入验证
		pParameter
				.addComponentBean(ValidationBean.class, TablePagerFilterValidation.class)
				.setTriggerSelector("#idTablePagerFilterSave")
				.setWarnType(EWarnType.insertAfter)
				.addValidators(new Validator(EValidatorMethod.required, "#tp_filter_v1, #tp_filter_v2"));

		// 加入日期
		pParameter.addComponentBean("calendarTablePagerFilter", CalendarBean.class);
	}
}
