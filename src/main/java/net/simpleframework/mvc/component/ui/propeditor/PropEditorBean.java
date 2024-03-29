package net.simpleframework.mvc.component.ui.propeditor;

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
public class PropEditorBean extends AbstractContainerBean {

	private String title;

	private boolean titleToggle = true;

	private String jsLoadedCallback;

	private PropFields propFields;

	public PropEditorBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public PropEditorBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public PropFields getFormFields() {
		if (propFields == null) {
			propFields = PropFields.of();
		}
		return propFields;
	}

	public String getTitle() {
		return title;
	}

	public PropEditorBean setTitle(final String title) {
		this.title = title;
		return this;
	}

	public boolean isTitleToggle() {
		return titleToggle;
	}

	public PropEditorBean setTitleToggle(final boolean titleToggle) {
		this.titleToggle = titleToggle;
		return this;
	}

	public String getJsLoadedCallback() {
		return jsLoadedCallback;
	}

	public PropEditorBean setJsLoadedCallback(final String jsLoadedCallback) {
		this.jsLoadedCallback = jsLoadedCallback;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsLoadedCallback" };
	}
}
