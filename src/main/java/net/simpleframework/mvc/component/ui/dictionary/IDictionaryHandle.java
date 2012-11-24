package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.tooltip.TipBean;
import net.simpleframework.mvc.component.ui.window.IWindowHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface IDictionaryHandle extends IWindowHandler {

	/**
	 * 在字典装载时触发，参考DictionaryLoad
	 * 
	 * @param cParameter
	 */
	void doDictionaryLoad(ComponentParameter cParameter);

	/**
	 * @param cParameter
	 * @param tip
	 */
	void doTip(ComponentParameter cParameter, TipBean tip);
}