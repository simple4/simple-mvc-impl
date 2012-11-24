package net.simpleframework.mvc.component.base.validation;

import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class Validator extends AbstractElementBean {

	private String selector;

	private EWarnType warnType;

	private EValidatorMethod method;

	private String message;

	private String args;

	public Validator(final XmlElement xmlElement) {
		super(xmlElement);
	}

	public Validator() {
		super(null);
	}

	public Validator(final EValidatorMethod method, final String selector, final String args) {
		super(null);
		setMethod(method);
		setSelector(selector);
		setArgs(args);
	}

	public Validator(final EValidatorMethod method, final String selector) {
		this(method, selector, null);
	}

	public EValidatorMethod getMethod() {
		return method;
	}

	public Validator setMethod(final EValidatorMethod method) {
		this.method = method;
		return this;
	}

	public String getSelector() {
		return selector;
	}

	public Validator setSelector(final String selector) {
		this.selector = selector;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Validator setMessage(final String message) {
		this.message = message;
		return this;
	}

	public String getArgs() {
		return args;
	}

	public Validator setArgs(final String args) {
		this.args = args;
		return this;
	}

	public EWarnType getWarnType() {
		return warnType;
	}

	public Validator setWarnType(final EWarnType warnType) {
		this.warnType = warnType;
		return this;
	}
}
