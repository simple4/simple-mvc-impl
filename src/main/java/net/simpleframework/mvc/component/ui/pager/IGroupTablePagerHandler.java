package net.simpleframework.mvc.component.ui.pager;

import net.simpleframework.mvc.component.ComponentParameter;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface IGroupTablePagerHandler extends ITablePagerHandler {

	/**
	 * 获取列分组描述信息
	 * 
	 * @param compParameter
	 * @param groupValue
	 * @return
	 */
	GroupWrapper getGroupWrapper(ComponentParameter cParameter, String groupValue);
}
