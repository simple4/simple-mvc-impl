package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.base.validation.AbstractValidationHandler;
import net.simpleframework.mvc.component.base.validation.ValidationBean;
import net.simpleframework.mvc.component.base.validation.Validators;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerFilterValidation extends AbstractValidationHandler {

	@Override
	public Validators getValidators(final ComponentParameter cParameter) {
		final ComponentParameter nComponentParameter = PagerUtils.get(cParameter);
		final Validators validators = ((ValidationBean) cParameter.componentBean).getValidators();
		final ITablePagerHandler tHandle = (ITablePagerHandler) nComponentParameter
				.getComponentHandler();
		if (tHandle instanceof AbstractTablePagerHandler) {
			final TablePagerColumn tpColumn = TablePagerUtils.getSelectedColumn(nComponentParameter);
			final Validators coll = ((AbstractTablePagerHandler) tHandle).getFilterColumnValidators(
					nComponentParameter, tpColumn);
			if (coll != null) {
				coll.addAll(validators);
			}
			return coll;
		}
		return validators;
	}
}
