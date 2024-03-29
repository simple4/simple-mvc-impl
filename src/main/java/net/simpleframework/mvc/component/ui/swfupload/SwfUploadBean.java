package net.simpleframework.mvc.component.ui.swfupload;

import net.simpleframework.common.StringUtils;
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
public class SwfUploadBean extends AbstractContainerBean {

	private String fileSizeLimit; // 单位： B、KB、MB、GB

	private int fileQueueLimit;

	private String fileTypes; // *.jpg;*.jpeg;*.gif;*.png;*.bmp

	private String fileTypesDesc;

	private boolean multiFileSelected;

	private String jsCompleteCallback;

	private String roleUpload;

	public SwfUploadBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public SwfUploadBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public String getFileSizeLimit() {
		return fileSizeLimit;
	}

	public SwfUploadBean setFileSizeLimit(final String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
		return this;
	}

	public int getFileQueueLimit() {
		return fileQueueLimit;
	}

	public SwfUploadBean setFileQueueLimit(final int fileQueueLimit) {
		this.fileQueueLimit = fileQueueLimit;
		return this;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public SwfUploadBean setFileTypes(final String fileTypes) {
		this.fileTypes = fileTypes;
		return this;
	}

	public String getFileTypesDesc() {
		return fileTypesDesc;
	}

	public SwfUploadBean setFileTypesDesc(final String fileTypesDesc) {
		this.fileTypesDesc = fileTypesDesc;
		return this;
	}

	public boolean isMultiFileSelected() {
		return multiFileSelected;
	}

	public SwfUploadBean setMultiFileSelected(final boolean multiFileSelected) {
		this.multiFileSelected = multiFileSelected;
		return this;
	}

	public String getJsCompleteCallback() {
		return jsCompleteCallback;
	}

	public SwfUploadBean setJsCompleteCallback(final String jsCompleteCallback) {
		this.jsCompleteCallback = jsCompleteCallback;
		return this;
	}

	public String getRoleUpload() {
		return StringUtils.text(roleUpload, default_role);
	}

	public SwfUploadBean setRoleUpload(final String roleUpload) {
		this.roleUpload = roleUpload;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsCompleteCallback" };
	}
}
