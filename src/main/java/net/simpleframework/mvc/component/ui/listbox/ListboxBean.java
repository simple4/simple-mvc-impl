package net.simpleframework.mvc.component.ui.listbox;

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
public class ListboxBean extends AbstractContainerBean {

	private ListItems listItems;

	private boolean checkbox;

	private boolean tooltip;

	private String jsClickCallback, jsDblclickCallback;

	public ListboxBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setWidth("200");
		setHeight("180");
	}

	public ListboxBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public ListboxBean setCheckbox(final boolean checkbox) {
		this.checkbox = checkbox;
		return this;
	}

	public boolean isTooltip() {
		return tooltip;
	}

	public ListboxBean setTooltip(final boolean tooltip) {
		this.tooltip = tooltip;
		return this;
	}

	public ListItems getListItems() {
		if (listItems == null) {
			listItems = ListItems.of();
		}
		return listItems;
	}

	public ListboxBean addListItem(final ListItem listItem) {
		getListItems().add(listItem);
		return this;
	}

	public String getJsClickCallback() {
		return jsClickCallback;
	}

	public ListboxBean setJsClickCallback(final String jsClickCallback) {
		this.jsClickCallback = jsClickCallback;
		return this;
	}

	public String getJsDblclickCallback() {
		return jsDblclickCallback;
	}

	public ListboxBean setJsDblclickCallback(final String jsDblclickCallback) {
		this.jsDblclickCallback = jsDblclickCallback;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsClickCallback", "jsDblclickCallback" };
	}
}
