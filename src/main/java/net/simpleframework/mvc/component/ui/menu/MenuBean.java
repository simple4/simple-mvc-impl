package net.simpleframework.mvc.component.ui.menu;

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
public class MenuBean extends AbstractContainerBean {

	private MenuItems menuItems;

	private EMenuEvent menuEvent;

	private String jsBeforeShowCallback;

	private String minWidth;

	public MenuBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
		setMinWidth("120");
	}

	public MenuBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public MenuItems getMenuItems() {
		if (menuItems == null) {
			menuItems = MenuItems.of();
		}
		return menuItems;
	}

	public MenuBean addItem(final MenuItem item) {
		getMenuItems().add(item);
		return this;
	}

	public EMenuEvent getMenuEvent() {
		return menuEvent;
	}

	public MenuBean setMenuEvent(final EMenuEvent menuEvent) {
		this.menuEvent = menuEvent;
		return this;
	}

	public String getMinWidth() {
		return minWidth;
	}

	public MenuBean setMinWidth(final String minWidth) {
		this.minWidth = minWidth;
		return this;
	}

	public String getJsBeforeShowCallback() {
		return jsBeforeShowCallback;
	}

	public MenuBean setJsBeforeShowCallback(final String jsBeforeShowCallback) {
		this.jsBeforeShowCallback = jsBeforeShowCallback;
		return this;
	}

	@Override
	public String getSelector() {
		return selector;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsBeforeShowCallback" };
	}
}
