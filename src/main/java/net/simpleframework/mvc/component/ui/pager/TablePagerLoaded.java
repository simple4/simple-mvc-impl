package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerLoaded extends PagerLoaded {

	@Override
	public void beforeComponentRender(final PageParameter pParameter) {
		super.beforeComponentRender(pParameter);
		final ComponentParameter nComponentParameter = PagerUtils.get(pParameter);
		final ITablePagerHandler tptbl = (ITablePagerHandler) nComponentParameter
				.getComponentHandler();
		if (tptbl instanceof AbstractTablePagerHandler) {
			((AbstractTablePagerHandler) tptbl).createComponentBeans(nComponentParameter);
		}
	}
}
