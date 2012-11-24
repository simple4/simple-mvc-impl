package net.simpleframework.mvc.component.base.submit;

import net.simpleframework.common.StringUtils;
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
public class SubmitBean extends AbstractComponentBean {

	private String formName;

	private boolean binary;

	private String fileSizeLimit = "1MB"; // 单位： B、KB、MB、GB

	private String handleMethod;

	private String confirmMessage;

	private String role;

	public SubmitBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setRunImmediately(false);
		setIncludeRequestData("pa");
	}

	public SubmitBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getFormName() {
		return formName;
	}

	public SubmitBean setFormName(final String formName) {
		this.formName = formName;
		return this;
	}

	public String getHandleMethod() {
		return handleMethod;
	}

	public SubmitBean setHandleMethod(final String handleMethod) {
		this.handleMethod = handleMethod;
		return this;
	}

	public boolean isBinary() {
		return binary;
	}

	public SubmitBean setBinary(final boolean binary) {
		this.binary = binary;
		return this;
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public void setFileSizeLimit(final String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}

	public String getConfirmMessage() {
		return confirmMessage;
	}

	public SubmitBean setConfirmMessage(final String confirmMessage) {
		this.confirmMessage = confirmMessage;
		return this;
	}

	public String getRole() {
		return StringUtils.text(role, default_role);
	}

	public SubmitBean setRole(final String role) {
		this.role = role;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "confirmMessage" };
	}
}
