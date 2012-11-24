package net.simpleframework.mvc.component.ui.validatecode;

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
public class ValidateCodeBean extends AbstractContainerBean {

	private String textName;

	private int textWidth;

	public ValidateCodeBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setHandleClass(DefaultValidateCodeHandler.class);
	}

	public ValidateCodeBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(final String textName) {
		this.textName = textName;
	}

	public int getTextWidth() {
		return textWidth;
	}

	public void setTextWidth(final int textWidth) {
		this.textWidth = textWidth;
	}
}
