package net.simpleframework.mvc.component.base.ajaxrequest;

import net.simpleframework.mvc.IForward;
import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.IComponentHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public interface IAjaxRequestHandler extends IComponentHandler {

	/**
	 * ajax请求的缺省执行方法。当指定handleMethod属性时，请参考该方法的定义
	 * 
	 * @param compParameter
	 * @return
	 */
	IForward ajaxProcess(ComponentParameter cParameter);
}
