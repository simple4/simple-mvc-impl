package net.simpleframework.mvc.component.ui.tree;

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
public class TreeBean extends AbstractContainerBean {
	private boolean dynamicLoading;

	private ETreeLineStyle lineStyle;

	private String contextMenu;

	private boolean checkboxes;

	private boolean checkboxesThreeState;

	private boolean cookies = true;

	// event
	private String jsLoadedCallback;

	private String jsCheckCallback;

	private String jsClickCallback, jsDblclickCallback;

	// job

	private String roleDrop;

	public TreeBean(final PageDocument pageDocument, final XmlElement xmlElement) {
		super(pageDocument, xmlElement);
	}

	public TreeBean(final PageDocument pageDocument) {
		this(pageDocument, null);
	}

	public boolean isDynamicLoading() {
		return dynamicLoading;
	}

	public TreeBean setDynamicLoading(final boolean dynamicLoading) {
		this.dynamicLoading = dynamicLoading;
		return this;
	}

	public String getContextMenu() {
		return contextMenu;
	}

	public TreeBean setContextMenu(final String contextMenu) {
		this.contextMenu = contextMenu;
		return this;
	}

	public ETreeLineStyle getLineStyle() {
		return lineStyle == null ? ETreeLineStyle.line : lineStyle;
	}

	public TreeBean setLineStyle(final ETreeLineStyle lineStyle) {
		this.lineStyle = lineStyle;
		return this;
	}

	public boolean isCheckboxes() {
		return checkboxes;
	}

	public TreeBean setCheckboxes(final boolean checkboxes) {
		this.checkboxes = checkboxes;
		return this;
	}

	public boolean isCheckboxesThreeState() {
		return checkboxesThreeState;
	}

	public TreeBean setCheckboxesThreeState(final boolean checkboxesThreeState) {
		this.checkboxesThreeState = checkboxesThreeState;
		return this;
	}

	public boolean isCookies() {
		return cookies;
	}

	public TreeBean setCookies(final boolean cookies) {
		this.cookies = cookies;
		return this;
	}

	public String getJsLoadedCallback() {
		return jsLoadedCallback;
	}

	public TreeBean setJsLoadedCallback(final String jsLoadedCallback) {
		this.jsLoadedCallback = jsLoadedCallback;
		return this;
	}

	public String getJsCheckCallback() {
		return jsCheckCallback;
	}

	public TreeBean setJsCheckCallback(final String jsCheckCallback) {
		this.jsCheckCallback = jsCheckCallback;
		return this;
	}

	public String getJsClickCallback() {
		return jsClickCallback;
	}

	public TreeBean setJsClickCallback(final String jsClickCallback) {
		this.jsClickCallback = jsClickCallback;
		return this;
	}

	public String getJsDblclickCallback() {
		return jsDblclickCallback;
	}

	public TreeBean setJsDblclickCallback(final String jsDblclickCallback) {
		this.jsDblclickCallback = jsDblclickCallback;
		return this;
	}

	public String getRoleDrop() {
		return roleDrop;
	}

	public TreeBean setRoleDrop(final String roleDrop) {
		this.roleDrop = roleDrop;
		return this;
	}

	private TreeNodes treeNodes;

	public TreeNodes getTreeNodes() {
		if (treeNodes == null) {
			treeNodes = TreeNodes.of();
		}
		return treeNodes;
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "jsLoadedCallback", "jsClickCallback", "jsDblclickCallback",
				"jsCheckCallback" };
	}
}
