package net.simpleframework.mvc.component.ui.menu;

import net.simpleframework.common.I18n;
import net.simpleframework.common.xml.AbstractElementBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class MenuItem extends AbstractElementBean {
	private final MenuBean menuBean;

	private final MenuItem parent;

	private MenuItems children;

	private String ref;

	private String title;

	private String url;

	private String iconClass;

	private boolean disabled;

	private String jsSelectCallback;

	private boolean checkbox, checked;

	private String jsCheckCallback;

	private String description;

	public MenuItem(final XmlElement xmlElement, final MenuBean menuBean, final MenuItem parent) {
		super(xmlElement);
		this.menuBean = menuBean;
		this.parent = parent;
	}

	public MenuItem(final MenuBean menuBean, final MenuItem parent) {
		this(null, menuBean, parent);
	}

	public MenuItem(final MenuBean menuBean) {
		this(null, menuBean, null);
	}

	public MenuItems children() {
		if (children == null) {
			children = MenuItems.of();
		}
		return children;
	}

	public MenuItem addChild(final MenuItem child) {
		children().add(child);
		return this;
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public MenuItem getParent() {
		return parent;
	}

	public String getRef() {
		return ref;
	}

	public MenuItem setRef(final String ref) {
		this.ref = ref;
		return this;
	}

	public String getTitle() {
		return I18n.replaceI18n(title);
	}

	public MenuItem setTitle(final String title) {
		this.title = title;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public MenuItem setUrl(final String url) {
		this.url = url;
		return this;
	}

	public String getIconClass() {
		return iconClass;
	}

	public MenuItem setIconClass(final String iconClass) {
		this.iconClass = iconClass;
		return this;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public MenuItem setDisabled(final boolean disabled) {
		this.disabled = disabled;
		return this;
	}

	public String getJsSelectCallback() {
		return jsSelectCallback;
	}

	public MenuItem setJsSelectCallback(final String jsSelectCallback) {
		this.jsSelectCallback = jsSelectCallback;
		return this;
	}

	public String getJsCheckCallback() {
		return jsCheckCallback;
	}

	public MenuItem setJsCheckCallback(final String jsCheckCallback) {
		this.jsCheckCallback = jsCheckCallback;
		return this;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public MenuItem setCheckbox(final boolean checkbox) {
		this.checkbox = checkbox;
		return this;
	}

	public boolean isChecked() {
		return checked;
	}

	public MenuItem setChecked(final boolean checked) {
		this.checked = checked;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public MenuItem setDescription(final String description) {
		this.description = description;
		return this;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsSelectCallback", "jsCheckCallback", "description" };
	}

	public static MenuItem of(final MenuBean menuBean, final String title) {
		return of(menuBean, title, null);
	}

	public static MenuItem of(final MenuBean menuBean, final String title, final String iconClass) {
		return of(menuBean, title, iconClass, null);
	}

	public static MenuItem of(final MenuBean menuBean, final String title, final String iconClass,
			final String jsSelectCallback) {
		return new MenuItem(menuBean).setTitle(title).setIconClass(iconClass)
				.setJsSelectCallback(jsSelectCallback);
	}

	public static MenuItem sep(final MenuBean menuBean) {
		return new MenuItem(menuBean).setTitle("-");
	}

	public static final String ICON_ADD = "menu_icon_add";
	public static final String ICON_EDIT = "menu_icon_edit";
	public static final String ICON_DELETE = "menu_icon_delete";
	public static final String ICON_REFRESH = "menu_icon_refresh";
	public static final String ICON_EXPAND = "menu_icon_expand";
	public static final String ICON_COLLAPSE = "menu_icon_collapse";

	public static final String ICON_UP = "menu_icon_up";
	public static final String ICON_UP2 = "menu_icon_up2";
	public static final String ICON_DOWN = "menu_icon_down";
	public static final String ICON_DOWN2 = "menu_icon_down2";
}
