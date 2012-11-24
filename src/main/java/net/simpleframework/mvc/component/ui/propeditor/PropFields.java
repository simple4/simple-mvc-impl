package net.simpleframework.mvc.component.ui.propeditor;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PropFields extends ArrayListEx<PropFields, PropField> {

	public static PropFields of(final PropField... items) {
		return new PropFields().append(items);
	}

	private static final long serialVersionUID = -7301366056172203145L;
}
