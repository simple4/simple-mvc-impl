package net.simpleframework.mvc.component.ui.tree;

import static net.simpleframework.common.I18n.$m;
import net.simpleframework.common.SimpleRuntimeException;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TreeException extends SimpleRuntimeException {
	private static final long serialVersionUID = 220404294448529138L;

	public TreeException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	public static TreeException of_delete() {
		return of($m("TreeException.0"));
	}

	public static TreeException of(final String msg) {
		return _of(TreeException.class, msg, null);
	}
}
