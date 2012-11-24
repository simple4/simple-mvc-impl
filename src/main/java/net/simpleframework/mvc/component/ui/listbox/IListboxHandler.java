package net.simpleframework.mvc.component.ui.listbox;

import java.util.Map;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface IListboxHandler extends IComponentHandler {

	/**
	 * 获取列表数据
	 * 
	 * @param cParameter
	 * @return
	 */
	ListItems getListItems(ComponentParameter cParameter);

	/**
	 * 获取扩展属性
	 * 
	 * @param cParameter
	 * @param listItem
	 * @return
	 */
	Map<String, Object> getListItemAttributes(ComponentParameter cParameter, ListItem listItem);
}
