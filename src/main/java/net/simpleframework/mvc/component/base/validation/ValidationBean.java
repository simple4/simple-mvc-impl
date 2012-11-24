package net.simpleframework.mvc.component.base.validation;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractComponentBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class ValidationBean extends AbstractComponentBean {

	private EWarnType warnType;

	private String triggerSelector;

	private Validators validators;

	public ValidationBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public ValidationBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public Validators getValidators() {
		if (validators == null) {
			validators = Validators.of();
		}
		return validators;
	}

	public ValidationBean addValidators(final Validator... validators) {
		getValidators().append(validators);
		return this;
	}

	public String getTriggerSelector() {
		return triggerSelector;
	}

	public ValidationBean setTriggerSelector(final String triggerSelector) {
		this.triggerSelector = triggerSelector;
		return this;
	}

	public EWarnType getWarnType() {
		return warnType == null ? EWarnType.alert : warnType;
	}

	public ValidationBean setWarnType(final EWarnType warnType) {
		this.warnType = warnType;
		return this;
	}
}
