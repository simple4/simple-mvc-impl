package net.simpleframework.mvc.component.ui.pager;

import java.util.List;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface IPagerHandler extends IComponentHandler {

	static final String PAGER_LIST = "__pager_list";

	/**
	 * 处理从start开始的数据
	 * 
	 * @param compParameter
	 * @param start
	 */
	void process(ComponentParameter cParameter, int start);

	/**
	 * 获取数据的数量
	 * 
	 * @param compParameter
	 * @return
	 */
	int getCount(ComponentParameter cParameter);

	/**
	 * 返回要分页的内容
	 * 
	 * @param cParameter
	 * @param data
	 * @return
	 */
	String toPagerHTML(ComponentParameter cParameter, List<?> data);
}
