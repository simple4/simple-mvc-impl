package net.simpleframework.mvc.component.ui.dictionary;

import net.simpleframework.mvc.component.ComponentParameter;
import net.simpleframework.mvc.component.ui.tooltip.TipBean;
import net.simpleframework.mvc.component.ui.window.AbstractWindowHandler;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public abstract class AbstractDictionaryHandler extends AbstractWindowHandler implements
		IDictionaryHandle {
	@Override
	public void doDictionaryLoad(final ComponentParameter cParameter) {
	}

	@Override
	public void doTip(final ComponentParameter cParameter, final TipBean tip) {
	}
}
