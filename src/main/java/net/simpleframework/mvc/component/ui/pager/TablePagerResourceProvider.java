package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.common.coll.ArrayUtils;
import net.simpleframework.mvc.PageParameter;
import net.simpleframework.mvc.component.IComponentRegistry;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TablePagerResourceProvider extends PagerResourceProvider {

	public TablePagerResourceProvider(final IComponentRegistry componentRegistry) {
		super(componentRegistry);
	}

	@Override
	public String[] getCssPath(final PageParameter pParameter) {
		return ArrayUtils.add(super.getCssPath(pParameter),
				getCssResourceHomePath(pParameter, TablePagerResourceProvider.class)
						+ "/tablepager.css");
	}

	@Override
	public String[] getJavascriptPath(final PageParameter pParameter) {
		return ArrayUtils.add(super.getJavascriptPath(pParameter),
				getResourceHomePath(TablePagerResourceProvider.class) + "/js/tablepager.js");
	}
}
