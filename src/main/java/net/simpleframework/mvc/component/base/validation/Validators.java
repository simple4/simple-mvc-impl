package net.simpleframework.mvc.component.base.validation;

import net.simpleframework.common.coll.ArrayListEx;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class Validators extends ArrayListEx<Validators, Validator> {

	public static Validators of(final Validator... items) {
		return new Validators().append(items);
	}

	private static final long serialVersionUID = -357345210542918649L;
}
