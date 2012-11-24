package net.simpleframework.mvc.component.ui.pwdstrength;

import net.simpleframework.common.xml.XmlElement;
import net.simpleframework.mvc.PageDocument;
import net.simpleframework.mvc.component.AbstractContainerBean;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class PwdStrengthBean extends AbstractContainerBean {

	private String pwdInput;

	public PwdStrengthBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public PwdStrengthBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getPwdInput() {
		return pwdInput;
	}

	public PwdStrengthBean setPwdInput(final String pwdInput) {
		this.pwdInput = pwdInput;
		return this;
	}
}
