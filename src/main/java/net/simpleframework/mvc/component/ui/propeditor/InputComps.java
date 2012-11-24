package net.simpleframework.mvc.component.ui.propeditor;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class InputComps extends ArrayListEx<InputComps, InputComp> {

	public static InputComps of(final InputComp... items) {
		return new InputComps().append(items);
	}

	private static final long serialVersionUID = -214630607114161778L;
}
