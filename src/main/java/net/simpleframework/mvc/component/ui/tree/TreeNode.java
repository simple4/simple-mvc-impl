package net.simpleframework.mvc.component.ui.tree;

import java.util.LinkedList;

import net.simpleframework.common.Convert;
import net.simpleframework.common.StringUtils;
import net.simpleframework.common.bean.IDescriptionBeanAware;
import net.simpleframework.common.bean.IIdBeanAware;
import net.simpleframework.common.xml.ItemUIBean;
import net.simpleframework.common.xml.XmlElement;

/**
 * 这是一个开源的软件，请在LGPLv3下合法使用、修改或重新发布。
 * 
 * @author 陈侃(cknet@126.com, 13910090885)
 *         http://code.google.com/p/simpleframework/
 *         http://www.simpleframework.net
 */
public class TreeNode extends ItemUIBean {

	private final TreeBean treeBean;

	private TreeNodes children;

	private final TreeNode parent;

	private boolean dynamicLoading;

	private String image, imageClose, imageOpen;

	private boolean draggable, acceptdrop;

	private boolean opened;

	private String tooltip;

	private String postfixText;

	private boolean checkbox;

	private int check; // 1 -1 0

	private String contextMenu;

	private boolean select;

	// event
	private String jsCheckCallback;

	private final Object data;

	public TreeNode(final XmlElement xmlElement, final TreeBean treeBean, final TreeNode parent,
			final Object data) {
		super(xmlElement);
		this.parent = parent;
		this.treeBean = treeBean;
		this.data = data;
		setText(Convert.toString(data));
		if (data instanceof IDescriptionBeanAware) {
			setTooltip(((IDescriptionBeanAware) data).getDescription());
		}
		if (data instanceof IIdBeanAware) {
			setId(String.valueOf(((IIdBeanAware) data).getId()));
		}
	}

	public TreeNode(final XmlElement xmlElement, final TreeBean treeBean, final TreeNode parent) {
		this(xmlElement, treeBean, parent, null);
	}

	public TreeNode(final TreeBean treeBean, final TreeNode parent, final Object data) {
		this(null, treeBean, parent, data);
	}

	public TreeNode(final TreeBean treeBean, final Object data) {
		this(null, treeBean, null, data);
	}

	public boolean isDynamicLoading() {
		return dynamicLoading;
	}

	public void setDynamicLoading(final boolean dynamicLoading) {
		this.dynamicLoading = dynamicLoading;
	}

	public String getImage() {
		return image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	public String getImageClose() {
		return StringUtils.text(imageClose, getImage());
	}

	public void setImageClose(final String imageClose) {
		this.imageClose = imageClose;
	}

	public String getImageOpen() {
		return StringUtils.text(imageOpen, getImage());
	}

	public void setImageOpen(final String imageOpen) {
		this.imageOpen = imageOpen;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(final boolean draggable) {
		this.draggable = draggable;
	}

	public boolean isAcceptdrop() {
		return acceptdrop;
	}

	public void setAcceptdrop(final boolean acceptdrop) {
		this.acceptdrop = acceptdrop;
	}

	public boolean isOpened() {
		return opened;
	}

	public void setOpened(final boolean opened) {
		this.opened = opened;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(final boolean select) {
		this.select = select;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}

	public String getPostfixText() {
		return postfixText;
	}

	public void setPostfixText(final String postfixText) {
		this.postfixText = postfixText;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(final boolean checkbox) {
		this.checkbox = checkbox;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(final int check) {
		this.check = check;
	}

	public String getJsCheckCallback() {
		return jsCheckCallback;
	}

	public void setJsCheckCallback(final String jsCheckCallback) {
		this.jsCheckCallback = jsCheckCallback;
	}

	public String getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(final String contextMenu) {
		this.contextMenu = contextMenu;
	}

	public Object getDataObject() {
		return data;
	}

	public TreeNode getParent() {
		return parent;
	}

	public TreeBean getTreeBean() {
		return treeBean;
	}

	public TreeNodes children() {
		if (children == null) {
			children = TreeNodes.of();
		}
		return children;
	}

	public int getLevel() {
		int i = -1;
		TreeNode node = getParent();
		while (node != null) {
			i--;
			node = node.getParent();
		}
		return Math.abs(i);
	}

	public String nodeId() {
		TreeNode parent = getParent();
		if (parent == null) {
			return getId();
		} else {
			final LinkedList<String> ll = new LinkedList<String>();
			ll.addFirst(getId());
			while (parent != null) {
				ll.addFirst(parent.getId());
				parent = parent.getParent();
			}
			return StringUtils.join(ll, "_");
		}
	}

	@Override
	protected String[] elementAttributes() {
		return new String[] { "text", "tooltip", "jsClickCallback", "jsDblclickCallback",
				"jsCheckCallback" };
	}
}
